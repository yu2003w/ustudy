package com.ustudy.cache;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.MarkTaskMapper;
import com.ustudy.exam.model.cache.MarkTaskCache;
import com.ustudy.exam.utility.ExamUtil;

@Service
public class PaperCache {
	
	private final static Logger logger = LogManager.getLogger(PaperCache.class);
	
	// caching papers here, quesid is the key
	@Autowired
	private RedisTemplate<String, List<MarkTaskCache>> paperC;
	
	@Autowired
	private MarkTaskMapper mtM;
	
	/**
	 * cache papers in memory
	 * Maybe not all parameters are needed, only cache basic info here
	 * @return
	 */
	public boolean cachePapers() {
		return false;
	}
	
	private boolean cachePapers(String quesid) {
		logger.debug("retrievePapers(), start retrieving papers for quesid -> " + quesid);
		List<String> paperL = mtM.getPapersByQuesId(quesid);
		if (paperL.isEmpty()) {
			logger.info("cachePapers(), no papers available for question " + quesid);
			return false;
		}
		
		List<MarkTaskCache> mtcL = new ArrayList<MarkTaskCache>();
		for (String id: paperL) {
			MarkTaskCache mtc = new MarkTaskCache(id, 0);
			mtcL.add(mtc);
		}
		paperC.opsForValue().set("ques" + quesid, mtcL);
		logger.debug("cachePapers(), papers cached for question{" + quesid + "} -> " + mtcL.toString());
		return true;
	}
	
	public List<String> retrievePapers(String quesid, String assmode) {
		if (!cachePapers(quesid)) {
			logger.error("retrievePapers(), failed to cache papers for question " + quesid);
			return null;
		}
		String teacid = ExamUtil.getCurrentUserId();
		List<MarkTaskCache> mtcL = paperC.opsForValue().get("ques" + quesid);
		logger.debug("retrievePapers(), papers retrieved ->" + mtcL.toString());
		
		List<String> firstTeaL = mtM.getTeachersByQidRole(quesid, "初评");
		List<String> finalTeaL = mtM.getTeachersByQidRole(quesid, "终评");
		int factor = 1, amount = mtcL.size(), thres = 0, count = 0;
		factor = firstTeaL.size();
		if (factor < 1) {
			logger.error("retrievePapers(), mark task is not assigned for question -> " + quesid);
			return null;
		}
		if ((firstTeaL.contains(teacid) || finalTeaL.contains(teacid)) && assmode.compareTo("平均") == 0) {
			thres = amount/factor;
		}
		else {
			logger.warn("retrievePapers(), teacher " + teacid + 
					" is not assigned to mark papers for question " + quesid);
			return null;
		}
		
		// get allocated papers here
		List<String> pList = new ArrayList<String>();
		for (MarkTaskCache paper : mtcL) {
			if (paper.getStatus() == 0 && count < thres) {
				paper.setStatus(1);
				paper.setTeacid(teacid);
				pList.add(paper.getPaperid());
				count++;
			}
		}
		//write back changes to redis
		//paperC.opsForValue().set("ques" + quesid, mtcL);
		logger.debug("retrievePapers(), assigned tasks for teacher " + teacid + " -> " + pList.toString());
		
		return pList;
		
	}
	
}
