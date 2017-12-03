package com.ustudy.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.MarkTaskMapper;
import com.ustudy.exam.model.PaperRequest;
import com.ustudy.exam.model.cache.MarkStaticsCache;
import com.ustudy.exam.model.cache.MarkTaskCache;
import com.ustudy.exam.model.cache.PaperImgCache;
import com.ustudy.exam.utility.ExamUtil;

@Service
public class PaperCache {
	
	private final static Logger logger = LogManager.getLogger(PaperCache.class);
	
	// caching papers here, quesid is the key, paper id is key the paper
	@Autowired
	private RedisTemplate<String, Map<String, MarkTaskCache>> paperC;
	// caching statics information of teacher marking task, key is teacher id
	@Autowired
	private RedisTemplate<String, Map<String, MarkStaticsCache>> teaPaperC;
	
	@Autowired
	private MarkTaskMapper mtM;
	
	private final int MAX_THRES = 20;
	private final String QUES_CACHE_PREFIX = "ques-";
	private final String TEA_CACHE_PREFIX = "tea-";
	
	private synchronized boolean cachePapers(String quesid) {
		logger.debug("retrievePapers(), start retrieving papers for quesid -> " + quesid);
		List<MarkTaskCache> mtcL = mtM.getPapersByQuesId(quesid);
		if (mtcL == null || mtcL.isEmpty()) {
			logger.info("cachePapers(), no papers available for question " + quesid);
			return false;
		}
		
		Map<String, MarkTaskCache> mtcHM = new HashMap<String, MarkTaskCache>();
		// get viewed papers list
		List<String> viewedP = mtM.getViewedPapersByQuesId(quesid);
		if (viewedP != null) {
			logger.debug("cachePapers(), " + viewedP.size() + " papers already viewed." + viewedP.toString());
		}
		for (MarkTaskCache mtc: mtcL) {
			if (viewedP != null && viewedP.contains(mtc.getPaperid())) {
				mtc.setStatus(2);
			}
			mtcHM.put(mtc.getPaperid(), mtc);
		}
		paperC.opsForValue().set(QUES_CACHE_PREFIX + quesid, mtcHM);
		logger.debug("cachePapers(), papers cached for question{" + quesid + "} -> " + mtcHM.toString());
		return true;
	}
	
	public synchronized Map<String, List<PaperImgCache>> retrievePapers(List<PaperRequest> prs) {
		// key is question id
		Map<String, List<PaperImgCache>> paperM = new HashMap<String, List<PaperImgCache>>();
		for (PaperRequest pr: prs) {
			List<PaperImgCache> pImgCache = this.getPapersForSingleQues(pr.getQid(), pr.getAssmode());
			if (pImgCache == null || pImgCache.isEmpty()) {
				logger.warn("retrievePapers(), no papers retrieved for " + pr.toString());
			}
			paperM.put(pr.getQid(), pImgCache);
		}
		return paperM;
	}
	
	private List<PaperImgCache> getPapersForSingleQues(String quesid, String assmode) {
		if (assmode == null || assmode.isEmpty()) {
			logger.error("retrievePapers(), assign mode is not set for question " + quesid);
			return null;
		}
		
		Map<String, PaperImgCache> paperM = null;
		String teacid = ExamUtil.getCurrentUserId();
		Map<String, MarkStaticsCache> teaSta = teaPaperC.opsForValue().get(TEA_CACHE_PREFIX + teacid);
		
		if (teaSta != null && !teaSta.isEmpty()) {
			MarkStaticsCache msc = teaSta.get(quesid);
			if (msc != null) {
				paperM = msc.getCurAssign();
				if (paperM != null && !paperM.isEmpty()) {
					logger.info("retrievePapers(), maybe user refreshed pages, return already assigned tasks");
					return new ArrayList<PaperImgCache>(paperM.values());
				}	
			}
		}
				
		Map<String, MarkTaskCache> mtcL = paperC.opsForValue().get(QUES_CACHE_PREFIX + quesid);
		if (mtcL == null || mtcL.isEmpty()) {
			if (!cachePapers(quesid)) {
				logger.error("retrievePapers(), failed to cache papers for question " + quesid);
				return null;
			}
			logger.debug("retrievePapers(), cached again for question " + quesid);
			mtcL = paperC.opsForValue().get(QUES_CACHE_PREFIX + quesid);
		}
		logger.debug("retrievePapers(), " + mtcL.size() + " papers retrieved.");
		
		List<String> firstTeaL = mtM.getTeachersByQidRole(quesid, "初评");
		List<String> finalTeaL = mtM.getTeachersByQidRole(quesid, "终评");
		int factor = 1;
		factor = firstTeaL.size();
		if (factor < 1) {
			logger.error("retrievePapers(), mark task is not assigned for question -> " + quesid);
			return null;
		}
		int amount = mtcL.size()/factor, thres = 0, marked = 0;
		if ((firstTeaL.contains(teacid) || finalTeaL.contains(teacid)) && assmode.compareTo("平均") == 0) {
			if (amount > MAX_THRES) {
				thres = MAX_THRES;
			}
			else
				thres = amount;
			logger.debug("retrievePapers(), threshold is " + thres);
		}
		else { 
			logger.warn("retrievePapers(), teacher " + teacid + 
					" is not assigned to mark papers for question " + quesid);
			return null;
		}
		
		// get allocated papers here
		paperM = new HashMap<String, PaperImgCache>();
		if (teaSta == null) {
			teaSta = new HashMap<String, MarkStaticsCache>();
			logger.debug("retrievePapers(), created question statics hashmap for teacher " + teacid);
		}

		Set<Entry<String, MarkTaskCache>> entries = mtcL.entrySet();
		int count = 0;
		for (Entry<String, MarkTaskCache> en: entries) {
			if (count < thres && en.getValue().getStatus() == 0) {
				en.getValue().setStatus(1);
				en.getValue().setTeacid(teacid);
				paperM.put(en.getKey(), new PaperImgCache(en.getKey(), en.getValue().getImg()));
				count++;
			}
		}
		
		//update statics information
		MarkStaticsCache ms = new MarkStaticsCache(marked, amount);
		ms.setCurAssign(paperM);
		teaSta.put(quesid, ms);
		teaPaperC.opsForValue().set(TEA_CACHE_PREFIX + teacid, teaSta);
		
		//write back changes to redis
		paperC.opsForValue().set(QUES_CACHE_PREFIX + quesid, mtcL);
		logger.debug("retrievePapers(), assigned tasks for teacher " + teacid + " -> " + paperM.toString());
		
		return new ArrayList<PaperImgCache>(paperM.values());
		
	}
	
	public boolean updateMarkStaticsCache(String quesid, String pid) {
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
		msc.incrCompleted(1);
		msc.getCurAssign().remove(pid);
		quesSta.put(quesid, msc);
		updatePaperCache(quesid, pid);
		teaPaperC.opsForValue().set(TEA_CACHE_PREFIX + teacid, quesSta);
		return true;
	}
	
	private boolean updatePaperCache(String quesid, String pid) {
		Map<String, MarkTaskCache> mtcM = paperC.opsForValue().get(QUES_CACHE_PREFIX + quesid);
		if (mtcM == null || mtcM.isEmpty()) {
			logger.error("updatePaperCache(), failed to retrieve cache information for question " + quesid);
			return false;
		}
		MarkTaskCache mtc = mtcM.get(pid);
		if (mtc == null) {
			logger.error("updatePaperCache(), no cached element found for question " + quesid + 
					" and paper id " + pid);
			return false;
		}
		mtc.setStatus(2);
		paperC.opsForValue().set(QUES_CACHE_PREFIX + quesid, mtcM);
		return true;
	}
}
