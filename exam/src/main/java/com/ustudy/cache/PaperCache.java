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
import com.ustudy.exam.model.cache.PaperScoreCache;
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
	private final String QUES_CACHE_PREFIX_FINAL = "ques_f_";
	private final String TEA_CACHE_PREFIX = "tea-";
	
	private synchronized boolean cachePapers(PaperRequest pr, String teacid, String role) {
		logger.debug("retrievePapers(), start retrieving papers for quesid -> " + pr.getQid());
		List<MarkTaskCache> mtcL = mtM.getPapersByQuesId(pr.getQid());
		if (mtcL == null || mtcL.isEmpty()) {
			logger.info("cachePapers(), no papers available for question " + pr.getQid());
			return false;
		}
		
		// for double mark, to double papers ids in the cache, when dispatch the task, should avoid assign 
		// same paper to the same teacher twice
		// Thanks Li Qi for the idea.
		if (role.compareTo("初评") == 0 && pr.getMarkmode().compareTo("双评") == 0) {
			for (MarkTaskCache mtc: mtcL) {
				mtcL.add(new MarkTaskCache(mtc));
			}
		}
				
		Map<String, MarkTaskCache> mtcHM = new HashMap<String, MarkTaskCache>();
		// get viewed papers list
		List<String> viewedP = mtM.getViewedPapersByQuesId(pr.getQid(), teacid);
		if (viewedP != null) {
			logger.debug("cachePapers(), " + viewedP.size() + " papers already viewed." + viewedP.toString());
		}
		// List<PaperScoreCache> pcC = mtM.getPaperScoreByQuesId(pr.getQid(), teacid);
		
		for (MarkTaskCache mtc: mtcL) {
			if (viewedP != null && viewedP.contains(mtc.getPaperid())) {
				mtc.setStatus(2);
				mtc.setTeacid(teacid);
			}
			mtcHM.put(mtc.getPaperid(), mtc);
		}
		if (role.compareTo("初评") == 0) {
			paperC.opsForValue().set(QUES_CACHE_PREFIX + pr.getQid(), mtcHM);
		}
		else
			paperC.opsForValue().set(QUES_CACHE_PREFIX_FINAL + pr.getQid(), mtcHM);
		
		logger.debug("cachePapers(), papers cached for question{" + pr.getQid() + "} -> " + mtcHM.toString());
		return true;
	}
	
	public synchronized Map<String, List<PaperImgCache>> retrievePapers(List<PaperRequest> prs) {
		// key is question id
		Map<String, List<PaperImgCache>> paperM = new HashMap<String, List<PaperImgCache>>();
		for (PaperRequest pr: prs) {
			List<PaperImgCache> pImgCache = this.getPapersForSingleQues(pr);
			if (pImgCache == null || pImgCache.isEmpty()) {
				logger.warn("retrievePapers(), no papers retrieved for " + pr.toString());
			}
			paperM.put(pr.getQid(), pImgCache);
		}
		return paperM;
	}
	
	private List<PaperImgCache> getPapersForSingleQues(PaperRequest pr) {
		if (pr.getAssmode() == null || pr.getAssmode().isEmpty()) {
			logger.error("getPapersForSingleQues(), assign mode is not set for question " + pr.getQid());
			return null;
		}
		
		if (pr.getMarkmode() == null || pr.getMarkmode().isEmpty()) {
			logger.error("getPapersForSingleQues(), mark mode is not set for question " + pr.getQid());
			return null;
		}
		
		Map<String, PaperImgCache> paperM = null;
		String teacid = ExamUtil.getCurrentUserId();
		Map<String, MarkStaticsCache> teaSta = teaPaperC.opsForValue().get(TEA_CACHE_PREFIX + teacid);
		
		if (teaSta != null && !teaSta.isEmpty()) {
			MarkStaticsCache msc = teaSta.get(pr.getQid());
			if (msc != null) {
				paperM = msc.getCurAssign();
				if (paperM != null && !paperM.isEmpty()) {
					logger.info("retrievePapers(), maybe user refreshed pages, return already assigned tasks");
					return new ArrayList<PaperImgCache>(paperM.values());
				}	
			}
		}
		
		// database design to make sure role is valid
		String role = mtM.getMarkRole(pr.getQid(), teacid);
		
		Map<String, MarkTaskCache> mtcL = paperC.opsForValue().get(QUES_CACHE_PREFIX + pr.getQid());
		if (mtcL == null || mtcL.isEmpty()) {
			if (!cachePapers(pr, teacid, role)) {
				logger.error("retrievePapers(), failed to cache papers for question " + pr.getQid());
				return null;
			}
			logger.debug("retrievePapers(), cached again for question " + pr.getQid());
			mtcL = paperC.opsForValue().get(QUES_CACHE_PREFIX + pr.getQid());
		}
		logger.debug("retrievePapers(), " + mtcL.size() + " papers retrieved.");
		
		List<String> firstTeaL = mtM.getTeachersByQidRole(pr.getQid(), "初评");
		List<String> finalTeaL = mtM.getTeachersByQidRole(pr.getQid(), "终评");
		int factor = 1;
		factor = firstTeaL.size();
		if (factor < 1) {
			logger.error("retrievePapers(), mark task is not assigned for question -> " + pr.getQid());
			return null;
		}
		int amount = mtcL.size()/factor, thres = 0, marked = 0;
		if ((firstTeaL.contains(teacid) || finalTeaL.contains(teacid)) && pr.getAssmode().compareTo("平均") == 0) {
			if (amount > MAX_THRES) {
				thres = MAX_THRES;
			}
			else
				thres = amount;
			logger.debug("retrievePapers(), threshold is " + thres);
		}
		else { 
			logger.warn("retrievePapers(), teacher " + teacid + 
					" is not assigned to mark papers for question " + pr.getQid());
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
		teaSta.put(pr.getQid(), ms);
		teaPaperC.opsForValue().set(TEA_CACHE_PREFIX + teacid, teaSta);
		
		//write back changes to redis
		paperC.opsForValue().set(QUES_CACHE_PREFIX + pr.getQid(), mtcL);
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
