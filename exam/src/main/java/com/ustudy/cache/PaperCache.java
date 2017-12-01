package com.ustudy.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.MarkTaskMapper;
import com.ustudy.exam.model.cache.MarkStaticsCache;
import com.ustudy.exam.model.cache.MarkTaskCache;
import com.ustudy.exam.model.cache.PaperImgCache;
import com.ustudy.exam.utility.ExamUtil;

@Service
public class PaperCache {
	
	private final static Logger logger = LogManager.getLogger(PaperCache.class);
	
	// caching papers here, quesid is the key
	@Autowired
	private RedisTemplate<String, List<MarkTaskCache>> paperC;
	// caching statics information of teacher marking task, key is teacher id
	@Autowired
	private RedisTemplate<String, Map<String, MarkStaticsCache>> teaPaperC;
	
	@Autowired
	private MarkTaskMapper mtM;
	
	private final int MAX_THRES = 20;
	private final String QUES_CACHE_PREFIX = "ques-";
	private final String TEA_CACHE_PREFIX = "tea-";
	
	/**
	 * cache papers in memory
	 * Maybe not all parameters are needed, only cache basic info here
	 * @return
	 */
	public boolean cachePapers() {
		return false;
	}
	
	private synchronized boolean cachePapers(String quesid) {
		logger.debug("retrievePapers(), start retrieving papers for quesid -> " + quesid);
		List<MarkTaskCache> mtcL = mtM.getPapersByQuesId(quesid);
		if (mtcL == null || mtcL.isEmpty()) {
			logger.info("cachePapers(), no papers available for question " + quesid);
			return false;
		}
		
		paperC.opsForValue().set(QUES_CACHE_PREFIX + quesid, mtcL);
		logger.debug("cachePapers(), papers cached for question{" + quesid + "} -> " + mtcL.toString());
		return true;
	}
	
	public synchronized List<PaperImgCache> retrievePapers(String quesid, String assmode) {
		if (assmode == null || assmode.isEmpty()) {
			logger.error("retrievePapers(), assign mode is not set for question " + quesid);
			return null;
		}
		List<MarkTaskCache> mtcL = paperC.opsForValue().get(QUES_CACHE_PREFIX + quesid);
		if (mtcL == null || mtcL.isEmpty()) {
			if (!cachePapers(quesid)) {
				logger.error("retrievePapers(), failed to cache papers for question " + quesid);
				return null;
			}
			logger.debug("retrievePapers(), cached again for question " + quesid);
			mtcL = paperC.opsForValue().get(QUES_CACHE_PREFIX + quesid);
		}
		
		String teacid = ExamUtil.getCurrentUserId();
		logger.debug("retrievePapers(), papers retrieved ->" + mtcL.toString());
		
		List<String> firstTeaL = mtM.getTeachersByQidRole(quesid, "初评");
		List<String> finalTeaL = mtM.getTeachersByQidRole(quesid, "终评");
		int factor = 1, amount = mtcL.size(), thres = 0, count = 0, marked = 0;
		factor = firstTeaL.size();
		if (factor < 1) {
			logger.error("retrievePapers(), mark task is not assigned for question -> " + quesid);
			return null;
		}
		if ((firstTeaL.contains(teacid) || finalTeaL.contains(teacid)) && assmode.compareTo("平均") == 0) {
			thres = amount/factor;
			if (thres > MAX_THRES) {
				thres = MAX_THRES;
			}
			logger.debug("retrievePapers(), threshold is " + thres);
		}
		else { 
			logger.warn("retrievePapers(), teacher " + teacid + 
					" is not assigned to mark papers for question " + quesid);
			return null;
		}
		
		// get allocated papers here
		List<PaperImgCache> pList = new ArrayList<PaperImgCache>();
		Map<String, MarkStaticsCache> quesSta = teaPaperC.opsForValue().get(teacid);
		if ( quesSta == null) {
			quesSta = new HashMap<String, MarkStaticsCache>();
			logger.debug("retrievePapers(), created question statics hashmap for teacher " + teacid);
		}

		for (MarkTaskCache paper : mtcL) {
			if (count < thres &&
				(paper.getStatus() == 0 || 
				(paper.getStatus() == 1 && paper.getTeacid().compareTo(teacid) == 0))) {
				paper.setStatus(1);
				paper.setTeacid(teacid);
				pList.add(new PaperImgCache(paper.getPaperid(), paper.getImg()));
				count++;
			}
			else if (paper.getStatus() == 2 && paper.getTeacid().compareTo(teacid) == 0) {
				marked++;
			}
		}
		
		//update statics information
		MarkStaticsCache ms = new MarkStaticsCache(marked, thres);
		quesSta.put(quesid, ms);
		teaPaperC.opsForValue().set(TEA_CACHE_PREFIX + teacid, quesSta);
		
		//write back changes to redis
		paperC.opsForValue().set(QUES_CACHE_PREFIX + quesid, mtcL);
		logger.debug("retrievePapers(), assigned tasks for teacher " + teacid + " -> " + pList.toString());
		
		return pList;
		
	}
	
	public boolean updateMarkStaticsCache(String quesid) {
		String teacid = ExamUtil.getCurrentUserId();
		Map<String, MarkStaticsCache> quesSta = teaPaperC.opsForValue().get(TEA_CACHE_PREFIX + teacid);
		if (quesSta == null || quesSta.isEmpty()) {
			logger.error("updateMarkStaticsCache(), statics cache is not initialized for teacher " + teacid);
			return false;
		}
		MarkStaticsCache msc = quesSta.get(quesid);
		if (msc == null) {
			logger.error("updateMarkStaticsCache(), statics cache is not initialized for question " + quesid 
					+ " which assigned to teacher " + teacid);
			return false;
		}
		msc.incrCompleted();
		quesSta.put(quesid, msc);
		teaPaperC.opsForValue().set(TEA_CACHE_PREFIX + teacid, quesSta);
		return true;
	}
}
