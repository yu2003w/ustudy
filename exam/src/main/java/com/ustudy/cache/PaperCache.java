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
import com.ustudy.exam.model.PaperRequest;
import com.ustudy.exam.model.cache.MarkStaticsCache;
import com.ustudy.exam.model.cache.MarkTaskCache;
import com.ustudy.exam.model.cache.PaperImgCache;
import com.ustudy.exam.model.cache.PaperScoreCache;
import com.ustudy.exam.model.statics.TeaStatics;
import com.ustudy.exam.utility.ExamUtil;

@Service
public class PaperCache {
	
	private final static Logger logger = LogManager.getLogger(PaperCache.class);
	
	// caching papers for question id, quesid is the key, 
	// List<MarkTaskCache> contains all papers related with the question
	@Autowired
	private RedisTemplate<String, List<MarkTaskCache>> paperC;
	
	// <String, MarkStaticsCache>, key is teacher id combined with quesion id
	// similar as t-13810001341-q-17
	// value MartStaticsCache contains all papers assigned to the teacher including already marked
	@Autowired
	private RedisTemplate<String, MarkStaticsCache> teaPaperC;
	
	@Autowired
	private MarkTaskMapper mtM;
	
	private final int MAX_THRES = 20;
	private final String QUES_PAPER_PREFIX = "q-";
	private final String QUES_PAPER_PREFIX_FINAL = "qf_";
	private final String TEA_PAPER_PREFIX = "t-";
	private final String TEA_QUES_PREFIX = "-q-";
	
	private synchronized boolean cachePapers(PaperRequest pr, String role) {
		logger.debug("cachePapers(), start caching papers for -> " + pr.toString());
		List<MarkTaskCache> mtcL = mtM.getPapersByQuesId(pr.getQid());
		if (mtcL == null || mtcL.isEmpty()) {
			logger.info("cachePapers(), no papers available for question " + pr.getQid());
			return false;
		}
		
		// for double mark, to double papers ids in the cache, when dispatch the task, should avoid assign 
		// same paper to the same teacher twice
		// Thanks Li Qi for the idea.
		if (pr.getMarkmode().compareTo("双评") == 0) {
			if (role.compareTo("初评") == 0) {
				mtcL.addAll(mtcL);
			}
			else if (role.compareTo("终评") == 0) {
				// TODO: add logic for final mark
			}
			
		}

		// get viewed papers list
		List<PaperScoreCache> viewedP = mtM.getViewedPapersByQuesId(pr.getQid());
		Map<String, PaperScoreCache> vIds = new HashMap<String, PaperScoreCache>();
		if (viewedP != null) {
			logger.debug("cachePapers(), " + viewedP.size() + " papers already viewed." + viewedP.toString());
			for (PaperScoreCache ps: viewedP) {
				vIds.put(ps.getPaperid(), ps);
			}
		}
		
		// ids only for viewed papers
		for (MarkTaskCache mtc: mtcL) {
			PaperScoreCache ps = vIds.get(mtc.getPaperid());
			if (ps != null) {
				mtc.setStatus(2);
				mtc.setTeacid(ps.getTeacid());
				mtc.setScore(ps.getScore());
			}
		}
		if (role.compareTo("终评") == 0) {
			paperC.opsForValue().set(QUES_PAPER_PREFIX_FINAL + pr.getQid(), mtcL);
		}
		else
			paperC.opsForValue().set(QUES_PAPER_PREFIX + pr.getQid(), mtcL);
		
		logger.debug("cachePapers(), papers cached for question{" + pr.getQid() + "} -> " + mtcL.toString());
		
		return true;
	}
	
	/**
	 * Retrieve metrics information about average score, marked number of questions assigned to teacher
	 * @param teacid
	 * @return
	 */
	private boolean initMetricsByTeaId(String teacid) {
		List<TeaStatics> tsL = mtM.getMarkStaticsByTeaId(teacid);
		for (TeaStatics ts: tsL) {
			MarkStaticsCache ms = new MarkStaticsCache(ts.getMarked(), ts.calAverageS());
			teaPaperC.opsForValue().set(TEA_PAPER_PREFIX + teacid + TEA_QUES_PREFIX + ts.getQuesid(), ms);
			logger.info("initMetricsByTeacId(), statics cache for " + teacid + " is " + ms.toString());
		}
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
		
		String teacid = ExamUtil.getCurrentUserId();
		String cacheKey = TEA_PAPER_PREFIX + teacid + TEA_QUES_PREFIX + pr.getQid();
		MarkStaticsCache msc = teaPaperC.opsForValue().get(cacheKey);
		List<PaperImgCache> paperM = null;
		if (msc != null && msc.getCurAssign() != null) {
			int wanted = msc.getTotal() - msc.getCompleted();
			paperM = this.getPapersFromTeaCache(msc.getCurAssign(), 0, wanted);
			if (paperM != null && !paperM.isEmpty()) {
				logger.info("getPapersForSingleQues(), maybe user refreshed pages, return already assigned "
						+ "tasks, size ->" + paperM.size() + "," + paperM.toString());
				return paperM;
			}	
		}
		
		// database design to make sure role is valid
		String role = mtM.getMarkRole(pr.getQid(), teacid);
		
		List<MarkTaskCache> mtcL = paperC.opsForValue().get(QUES_PAPER_PREFIX + pr.getQid());
		if (mtcL == null || mtcL.isEmpty()) {
			if (!cachePapers(pr, role)) {
				logger.error("getPapersForSingleQues(), failed to cache papers for question " + pr.getQid());
				return null;
			}
			logger.debug("getPapersForSingleQues(), paper cached finished for question " + pr.getQid());

			// get all papers for certain question, need to dispatch to teachers
			mtcL = paperC.opsForValue().get(QUES_PAPER_PREFIX + pr.getQid());
		}
		logger.debug("getPapersForSingleQues(), " + mtcL.size() + " papers retrieved.");
		
		// prepare information for teacher's cache
		paperM = new ArrayList<PaperImgCache>();
		if (msc == null) {
			// initialize statics cache for teacher
			initMetricsByTeaId(teacid);
			msc = teaPaperC.opsForValue().get(cacheKey);
			if (msc == null) 
				msc = new MarkStaticsCache();
			logger.debug("getPapersForSingleQues(), initialize mark statics cache for teacher " + teacid);
		}
				
		if (msc.getCurAssign() == null)
			msc.setCurAssign(new ArrayList<MarkTaskCache>());
		
		List<String> teaL = mtM.getTeachersByQidRole(pr.getQid(), role); 
		int factor = teaL.size();
		if (factor < 1) {
			logger.error("getPapersForSingleQues(), mark task is not assigned for question -> " + pr.getQid());
			return null;
		}
		int amount = mtcL.size()/factor, batch = 0;
		if (teaL.contains(teacid) && pr.getAssmode().compareTo("平均") == 0) {
			int wanted = amount - msc.getCompleted();
			if (wanted > MAX_THRES) {
				batch = MAX_THRES;
			}
			else
				batch = wanted;
			logger.debug("getPapersForSingleQues(), papers assigned to " + teacid + " is " + amount + 
					" , threshold is " + batch);
		}
		else { 
			logger.warn("getPapersForSingleQues(), teacher " + teacid + 
					" is not assigned to mark papers for question " + pr.getQid());
			return null;
		}
		
		// allocated tasks
		int count = 0;
		for (MarkTaskCache mt: mtcL) {
			if (count < batch && mt.getStatus() == 0) {
				mt.setStatus(1);
				mt.setTeacid(teacid);
				paperM.add(new PaperImgCache(mt.getPaperid(), mt.getImg()));
				msc.getCurAssign().add(mt);
				count++;
			}
			else if (mt.getTeacid() !=null && mt.getTeacid().compareTo(teacid) == 0) {
				// added already marked items into list
				msc.getCurAssign().add(mt);
			}
		}
		
		msc.setTotal(amount);
		teaPaperC.opsForValue().set(cacheKey, msc);
		
		//write back changes to redis
		paperC.opsForValue().set(QUES_PAPER_PREFIX + pr.getQid(), mtcL);
		logger.debug("getPapersForSingleQues(), assigned tasks for teacher " + teacid + " -> " + paperM.toString());
		
		return paperM;
		
	}
	
	public boolean updateMarkStaticsCache(String quesid, String pid, String score) {
		String teacid = ExamUtil.getCurrentUserId();
		MarkStaticsCache msc = teaPaperC.opsForValue().get(TEA_PAPER_PREFIX + teacid + 
				TEA_QUES_PREFIX + quesid);
		if (msc == null) {
			logger.error("updateMarkStaticsCache(), statics cache is not initialized for question " + quesid 
					+ " assigned to teacher " + teacid);
			return false;
		}
		
		msc.incrCompleted(1, score);
		msc.getCurAssign().get(msc.getCompleted()).setStatus(2);
		updatePaperCache(quesid, pid);
		teaPaperC.opsForValue().set(TEA_PAPER_PREFIX + teacid + TEA_QUES_PREFIX + quesid, msc);
		return true;
	}
	
	/*
	 * get papers from teacher's cache for the question
	 * if no papers wanted retrieved, need to get from the questions' cache
	 */
	private List<PaperImgCache> getPapersFromTeaCache(List<MarkTaskCache> task, int seq, int wanted) {
		List<PaperImgCache> piC = new ArrayList<PaperImgCache>();
		int i = 0, count = 0;
		for (MarkTaskCache mt: task) {
			if (seq == 0) {
				// get unmarked papers
				if (mt.getStatus() != 2 && count < wanted) {
					piC.add(new PaperImgCache(mt.getPaperid(), mt.getImg()));
					count++;
				}
			}
			else {
				// retrieve from specified sequence
				if (i++ > seq && count < MAX_THRES) {
					piC.add(new PaperImgCache(mt.getPaperid(), mt.getImg()));
					count++;
				}
			}	
		}
		return piC;
	}
	
	private boolean updatePaperCache(String quesid, String pid) {
		List<MarkTaskCache> mtcM = paperC.opsForValue().get(QUES_PAPER_PREFIX + quesid);
		if (mtcM == null || mtcM.isEmpty()) {
			logger.error("updatePaperCache(), failed to retrieve cache information for question " + quesid);
			return false;
		}
		for (MarkTaskCache mt: mtcM) {
			if (mt.getPaperid().compareTo(pid) == 0)
				mt.setStatus(2);
		}
		
		paperC.opsForValue().set(QUES_PAPER_PREFIX + quesid, mtcM);
		return true;
	}
	
	public int getTotal(String quesid, String tid) {
		return teaPaperC.opsForValue().get(TEA_PAPER_PREFIX + tid +	TEA_QUES_PREFIX + quesid).getTotal();
	}
	
	public int getMarked(String quesid, String tid) {
		return teaPaperC.opsForValue().get(TEA_PAPER_PREFIX + tid +	TEA_QUES_PREFIX + quesid).getCompleted();
	}
	
	public String getProgress(String quesid, String tid) {
		return String.valueOf(this.getMarked(quesid, tid)) + "/" + String.valueOf(this.getTotal(quesid, tid));
	}
	
	public String getAveScore(String quesid, String tid) {
		return teaPaperC.opsForValue().get(TEA_PAPER_PREFIX + tid +	TEA_QUES_PREFIX + quesid).getAvescore();
	}
}
