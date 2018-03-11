package com.ustudy.exam.service.impl.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
	// MartStaticsCache contains all papers assigned to the teacher including already marked
	@Autowired
	private RedisTemplate<String, MarkStaticsCache> teaPaperC;

	@Autowired
	private MarkTaskMapper mtM;

	private final int MAX_THRES = 20;
	private final String QUES_PAPER_PREFIX = "q-";
	private final String QUES_PAPER_PREFIX_FINAL = "qf-";
	private final String TEA_PAPER_PREFIX = "t-";
	private final String TEA_QUES_PREFIX = "-q-";

	/**
	 * Cached papers for specified question, place related paper ids into memory for
	 * first round mark final round mark should be calculated based on first round
	 * 
	 * @param ---
	 *            information contains paper id, assign mode and mark mode
	 * @return
	 */
	private synchronized boolean cachePapers(PaperRequest pr) {
		logger.debug("cachePapers(), start caching papers for -> " + pr.toString());
		List<MarkTaskCache> mtcL = mtM.getPapersByQuesId(pr.getQid());
		if (mtcL == null || mtcL.isEmpty()) {
			logger.info("cachePapers(), no papers available for question " + pr.getQid());
			return false;
		}

		// for double mark, to double papers ids in the cache, when dispatch the task, 
		// should avoid assign same paper to the same teacher twice
		// Thanks Li Qi for the idea.
		List<String> firstTeaL = null;
		boolean dMark = false;
		if (pr.getMarkmode().compareTo("双评") == 0) {
			// noted: here, mctL.addAll(mtcL) should not be used, it's not right usage for list copy
			List<MarkTaskCache> dt = new ArrayList<MarkTaskCache>();
			// place marktask as paper A, A', B, B', C, C', D, D' and so on
			for (MarkTaskCache mt : mtcL) {
				dt.add(new MarkTaskCache(mt));
				dt.add(new MarkTaskCache(mt));
			}
			mtcL = dt;
			// retrieve teachers for first round mark
			firstTeaL = mtM.getTeachersByQidType(pr.getQid(), "初评");
			if (firstTeaL == null || firstTeaL.isEmpty()) {
				logger.error("cachePapers(), no teachers retrieved for " + pr.getQid());
				return false;
			}
			else
				dMark = true;
		}
		logger.info("cachePapers(), total paper size -> " + mtcL.size() + ", qid->" + pr.getQid() + 
				(dMark == false ? "": firstTeaL.toString()));

		// get already viewed papers
		List<PaperScoreCache> viewedP = mtM.getViewedPapersByQuesId(pr.getQid());
		Map<String, PaperScoreCache[]> vIds = new HashMap<String, PaperScoreCache[]>();
		if (viewedP != null && !viewedP.isEmpty()) {
			logger.trace("cachePapers(), " + viewedP.size() + " papers already viewed.");
			for (PaperScoreCache ps : viewedP) {
				logger.trace("cachePapers(), viewed -> " + ps.toString());
			}
			for (PaperScoreCache ps : viewedP) {
				logger.trace("cachePapers(), " + ps.toString());
				if (vIds.get(ps.getPaperid()) != null) {
					vIds.get(ps.getPaperid())[1] = ps;
					logger.trace("cachePapers(), viewed paper " + ps.getPaperid() + "->" + vIds.get(ps.getPaperid())[0]
							+ "," + vIds.get(ps.getPaperid())[1]);
				} else {
					PaperScoreCache[] two = new PaperScoreCache[2];
					two[0] = ps;
					two[1] = null;
					vIds.put(ps.getPaperid(), two);
					logger.trace("cachePapers(), viewed paper " + ps.getPaperid() + "->" + two[0] + ", " + two[1]);
				}
			}
		}

		// fill ids for viewed papers， also fill sequences for all papers
		// for double marks, calculate to associate teacher and marked papers
		int i = 0;
		int teaN = dMark == true ? firstTeaL.size():0;
		for (MarkTaskCache mtc : mtcL) {
			PaperScoreCache[] ps = vIds.get(mtc.getPaperid());
			if (ps != null) {
				String teacid = null;
				if (dMark) {
					teacid = firstTeaL.get(((i + 1) % teaN) - 1);
				}
				// assign teacher to proper paper
				if (ps[0] != null && ps[0].getTeacid().compareTo(teacid) == 0) {
					logger.trace("cachePapers(), ps[0]" + ps[0].toString());
					mtc.setStatus(2);
					mtc.setTeacid(ps[0].getTeacid());
					mtc.setScore(ps[0].getScore());
					ps[0] = null;
				} else if (ps[1] != null && ps[1].getTeacid().compareTo(teacid) == 0) {
					logger.trace("cachePapers(), ps[1]" + ps[1].toString());
					mtc.setStatus(2);
					mtc.setTeacid(ps[1].getTeacid());
					mtc.setScore(ps[1].getScore());
					// need to clean once set
					vIds.remove(mtc.getPaperid());
				}
			}
			mtc.setSeq(i++);
		}

		paperC.opsForValue().set(QUES_PAPER_PREFIX + pr.getQid(), mtcL);

		logger.info("cachePapers(), papers cached for quesid->" + pr.getQid());
		// code for basic debugging
		/*
		 * for (MarkTaskCache mt:mtcL) { logger.debug(mt.toString()); }
		 */

		return true;
	}

	private boolean popFinalMarkIds(String quesid) {
		String cacheK = this.QUES_PAPER_PREFIX + quesid;
		List<MarkTaskCache> mtcL = paperC.opsForValue().get(cacheK);
		if (mtcL == null || mtcL.isEmpty()) {
			logger.error("popFinalMarkIds(), no cached papers for question " + quesid);
			return false;
		}
		int num = mtcL.size() / 2;

		List<MarkTaskCache> mfL = new ArrayList<MarkTaskCache>();
		List<PaperScoreCache> viewedP = mtM.getFinalViewedPapersByQuesId(quesid);
		Map<String, PaperScoreCache> viewedPM = new HashMap<String, PaperScoreCache>();
		if (viewedP != null && !viewedP.isEmpty()) {
			for (PaperScoreCache ps : viewedP) {
				viewedPM.put(ps.getPaperid(), ps);
			}
		}

		for (int i = 0; i < num; i += 2) {
			MarkTaskCache mt = mtcL.get(i);
			PaperScoreCache ps = viewedPM.get(mt.getPaperid());
			if (ps != null) {
				// already marked
				MarkTaskCache fm = new MarkTaskCache(mt.getPaperid(), mt.getImg(), ps.getTeacid(), 2, ps.getScore(),
						mt.getSeq());
				mfL.add(fm);
			} else {
				// make sure that related papers are marked
				if ((mtcL.get(i).getScore() != -1 && mtcL.get(i + 1).getScore() != -1)
						&& (Math.abs(mtcL.get(i).getScore() - mtcL.get(i + 1).getScore()) >= 5)) {
					// logger.debug("popFinalMarkIds(), mtcL[" + i +"] -> " + mtcL.get(i).toString()
					// +
					// ", mtcL[" + (i+num) + "]->" + mtcL.get(i+num).toString());
					MarkTaskCache fm = new MarkTaskCache(mtcL.get(i).getPaperid(), mtcL.get(i).getImg());
					fm.setSeq(i);
					mfL.add(fm);
				}
			}

		}

		paperC.opsForValue().set(QUES_PAPER_PREFIX_FINAL + quesid, mfL);
		logger.info("popFinalMarkIds(), finished for " + quesid + " with " + mfL.size() + "papers");
		return true;
	}

	/**
	 * Retrieve metrics information about average score, marked number of questions
	 * assigned to teacher
	 * 
	 * @param teacid
	 * @return
	 */
	private boolean initMetricsByTeaId(String teacid) {
		List<TeaStatics> tsL = mtM.getMarkStaticsByTeaId(teacid);
		for (TeaStatics ts : tsL) {
			String cacheKey = TEA_PAPER_PREFIX + teacid + TEA_QUES_PREFIX + ts.getQuesid();
			if (teaPaperC.opsForValue().get(cacheKey) == null) {
				// task cache for this teacher is not set yet
				MarkStaticsCache ms = new MarkStaticsCache(ts.getMarked(), ts.calAverageS());
				teaPaperC.opsForValue().set(TEA_PAPER_PREFIX + teacid + TEA_QUES_PREFIX + ts.getQuesid(), ms);
				logger.info("initMetricsByTeacId(), statics cache for " + teacid + ", quesid->" + ts.getQuesid()
						+ " is " + ms.toString());
			} else {
				logger.info("initMetricsByTeacId(), statics cache already initialized for " + teacid + 
						", quesid->" + ts.getQuesid());
			}
		}
		return true;
	}

	public synchronized Map<String, List<PaperImgCache>> retrievePapers(List<PaperRequest> prs) {
		// key is question id
		Map<String, List<PaperImgCache>> paperM = new HashMap<String, List<PaperImgCache>>();
		for (PaperRequest pr : prs) {
			List<PaperImgCache> pImgCache = this.getPapersForSingleQues(pr);
			if (pImgCache == null || pImgCache.isEmpty()) {
				logger.warn("retrievePapers(), no papers retrieved for " + pr.toString());
				pImgCache = new ArrayList<PaperImgCache>();
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

		// database design to make sure role is valid
		String role = mtM.getMarkType(teacid, pr.getQid());
		boolean isFinalMark = (role.compareTo("终评") == 0);

		List<PaperImgCache> paperM = null;
		if (msc != null && msc.getCurAssign() != null) {
			int wanted = msc.getTotal() - msc.getCompleted();

			if (pr.getStartSeq() > 0) {
				if (pr.getStartSeq() > msc.getTotal()) {
					logger.warn("getPapersForSingleQues(), request start seq->" + pr.getStartSeq()
							+ ", total assigned->" + msc.getTotal());
					return null;
				}
				// get already marked papers, it's remark scenario
				wanted = pr.getEndSeq() - pr.getStartSeq() + 1;
				wanted = wanted > 0 ? wanted : MAX_THRES;
			}
			if (wanted > 0) {
				paperM = this.getPapersFromTeaCache(msc, pr.getStartSeq() > 0 ? pr.getStartSeq() : 0, wanted,
						isFinalMark, pr.getQid());
				if (paperM != null && !paperM.isEmpty()) {
					logger.info("getPapersForSingleQues(), maybe " + teacid + 
							" refreshed pages, returned batch size->" + paperM.size());
					logger.debug("getPapersForSingleQues(), detailed batch->" + paperM.toString());
					return paperM;
				}
			} else if (!isFinalMark && pr.getAssmode().compareTo("平均") == 0) {
				logger.info("getPapersForSingleQues(), all assigned papers are marked by " + teacid);
				return null;
			}
		}

		String paperCacheKey = null;
		List<MarkTaskCache> mtcL = null;

		if (isFinalMark) {
			paperCacheKey = QUES_PAPER_PREFIX_FINAL + pr.getQid();
			// for final mark, need to fetch items from cache each time
			mtcL = paperC.opsForValue().get(paperCacheKey);
			if (mtcL != null) {
				paperC.delete(paperCacheKey);
				mtcL = null;
				logger.info("getPapersForSingleQues(), clear final mark cache for " + paperCacheKey);
			}
			if (msc != null) {
				paperC.delete(cacheKey);
				msc = null;
				logger.info("getPapersForSingleQues(), clean final mark cache for " + cacheKey);
			}
		} else {
			paperCacheKey = QUES_PAPER_PREFIX + pr.getQid();
		}
		mtcL = paperC.opsForValue().get(paperCacheKey);

		if (mtcL == null || mtcL.isEmpty()) {
			if (paperC.opsForValue().get(QUES_PAPER_PREFIX + pr.getQid()) == null) {
				if (!cachePapers(pr)) {
					logger.error("getPapersForSingleQues(), failed to cache papers for question " + pr.getQid());
					return null;
				}
				logger.info("getPapersForSingleQues(), paper cached finished for question " + pr.getQid());
			}

			// for final mark, paper ids should be calculated from first round mark
			if (isFinalMark && !popFinalMarkIds(pr.getQid())) {
				logger.warn("getPapersForSingleQues(), papers for final mark is not ready");
				return null;
			}

			// get all papers for certain question, need to dispatch to teachers
			mtcL = paperC.opsForValue().get(paperCacheKey);
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

		if (msc.getCurAssign() == null) {
			msc.setCurAssign(new ConcurrentHashMap<String, MarkTaskCache>());
			logger.debug("getPapersForSingleQues(), initialize mark statics cache of mark tasks.");
		}

		if (msc.getpList() == null) {
			msc.setpList(new ArrayList<String>());
		}

		int amount = msc.getTotal();
		// only useful for double mark scenario
		TeaMarkInfo tmi = new TeaMarkInfo();
		if (amount <= 0 || pr.getAssmode().compareTo("平均") != 0) {
			// not assigned yet
			amount = calAssignedAmount(pr, mtcL.size(), role, teacid, tmi);
			if (amount == -1) {
				logger.error("getPapersForSingleQues(), failed to calculate assinged amount for " + teacid);
				return null;
			}
			msc.setTotal(amount);
		}

		int batch = 0, wanted = amount - msc.getCompleted();
		if (wanted > MAX_THRES) {
			batch = MAX_THRES;
		} else
			batch = wanted;
		
		logger.info("getPapersForSingleQues(), papers assigned to " + teacid + " is " + amount + 
				" , current batch is " + batch);

		// allocated tasks
		int count = 0, mid = 0;
		List<MarkTaskCache> firstMarkL = null;
		if (isFinalMark) {
			firstMarkL = paperC.opsForValue().get(QUES_PAPER_PREFIX + pr.getQid());
			if (firstMarkL != null && !firstMarkL.isEmpty()) {
				mid = firstMarkL.size() / 2;
			}
		}

		if (role.equals("初评")) {
			// for first mark, papers for each teacher could be calculated and paper sequence for each teacher
			// is fixed once the mark task is assigned
			logger.info("getPapersForSingleQues(), total task for first mark ->" + mtcL.size() + 
					", amount->" + amount);
			for (int i = tmi.getSeq() - 1; i < mtcL.size() && count < amount; i += tmi.getNum()) {
				MarkTaskCache mt = mtcL.get(i);
				logger.debug("getPapersForSingleQues(), index->" + i + ", " + mt.toString());
				if (!msc.getCurAssign().containsKey(mt.getPaperid())) {
					if (mt.getStatus() == 0) {
						mt.setStatus(1);
						mt.setTeacid(teacid);
						paperM.add(new PaperImgCache(mt.getPaperid(), mt.getImg()));
						msc.getpList().add(mt.getPaperid());
						msc.getCurAssign().put(mt.getPaperid(), mt);
					}
					// else if (mt.getStatus() == 2 && mt.getTeacid().compareTo(teacid) == 0) {
					else if (mt.getTeacid().compareTo(teacid) == 0) {
						// already marked papers
						msc.getCurAssign().put(mt.getPaperid(), mt);
						msc.getpList().add(mt.getPaperid());
					}
					count++;
				}
			}
		} else {
			for (MarkTaskCache mt : mtcL) {
				if (!msc.getCurAssign().containsKey(mt.getPaperid())) {
					if (count < batch && mt.getStatus() == 0) {
						mt.setStatus(1);
						mt.setTeacid(teacid);
						paperM.add(new PaperImgCache(mt.getPaperid(), mt.getImg()));
						msc.getpList().add(mt.getPaperid());

						if (isFinalMark) {
							// for final marks, number of returned records should be times of triple
							// 1, final mark element 2, first mark element 3, first mark element
							MarkTaskCache fm = firstMarkL.get(mt.getSeq());
							paperM.add(new PaperImgCache(fm.getPaperid(), fm.getScore(), fm.getTeacid()));
							fm = firstMarkL.get(mt.getSeq() + mid);
							paperM.add(new PaperImgCache(fm.getPaperid(), fm.getScore(), fm.getTeacid()));
						}

						msc.getCurAssign().put(mt.getPaperid(), mt);
						count++;
					} else if (mt.getStatus() == 2 && mt.getTeacid().compareTo(teacid) == 0) {
						// already marked papers
						msc.getCurAssign().put(mt.getPaperid(), mt);
						msc.getpList().add(mt.getPaperid());
					}
				}
			}
		}

		teaPaperC.opsForValue().set(cacheKey, msc);
		logger.info("getPapersForSingleQues(), paper seq for " + teacid + ", quesid->" + pr.getQid() + "->"
				+ msc.getpList().toString() + ", assigned size->" + msc.getCurAssign().size());

		// need to write back to redis cache again
		paperC.opsForValue().set(paperCacheKey, mtcL);

		// Now paper cache, teacher paper cache initialized or request new papers
		// for review already marked papers, need to get from teacher cache again rather
		// than fresh papers
		if (pr.getStartSeq() > 0) {
			wanted = pr.getEndSeq() - pr.getStartSeq() + 1;
			paperM = this.getPapersFromTeaCache(msc, pr.getStartSeq(), wanted > 0 ? wanted : MAX_THRES, isFinalMark,
					pr.getQid());
			logger.info("getPapersForSingleQues(), fetch marked papers for teacher " + teacid);
		}

		logger.debug("getPapersForSingleQues(), assigned new papers for teacher " + teacid + " -> " + paperM.toString());
		return paperM;

	}

	/**
	 * @param pr
	 *            --- PaperRequest basic information
	 * @param total
	 *            --- total papers needs to be dispatched
	 * @param role
	 *            --- role for teacher
	 * @return --- amount needs to be assigned to the teacher
	 */
	private int calAssignedAmount(PaperRequest pr, int total, String role, String teacid, TeaMarkInfo tmi) {
		List<String> teaL = mtM.getTeachersByQidType(pr.getQid(), role);
		int factor = teaL.size();
		if (factor < 1) {
			logger.error("calAssignedAmount(), mark task is not set for question -> " + pr.getQid());
			return -1;
		}
		tmi.setNum(factor);
		int amount = 0;
		if (pr.getAssmode().compareTo("平均") == 0) {
			for (int i = 0; i < factor; i++) {
				if (teaL.get(i).compareTo(teacid) == 0) {
					int left = total % factor;
					amount = total / factor + (i < left ? 1 : 0);
					tmi.setSeq(i + 1);
				}
			}
			if (amount == 0) {
				logger.warn("calAssignedAmount(), something goes wrong, assigned papers for " + teacid + " is " + amount
						+ " and sequence is " + tmi.getSeq());
			}
			logger.info("calAssignedAmount(), assigned papers for " + teacid + " is " + amount + " and sequence is "
					+ tmi.getSeq());
		} else {
			logger.error("calAssignedAmount(), " + pr.getAssmode() + " is not supported");
			return -1;
		}
		return amount;
	}

	public boolean updateMarkStaticsCache(String quesid, String pid, String score, boolean isfinal) {
		String teacid = ExamUtil.getCurrentUserId();
		MarkStaticsCache msc = teaPaperC.opsForValue().get(TEA_PAPER_PREFIX + teacid + TEA_QUES_PREFIX + quesid);
		if (msc == null) {
			logger.error("updateMarkStaticsCache(), statics cache is not initialized for question " + quesid
					+ " assigned to teacher " + teacid);
			return false;
		}

		MarkTaskCache mt = msc.getCurAssign().get(pid);
		if (mt != null) {
			mt.setScore(Float.valueOf(score));
		} else {
			logger.error("updateMarkStaticsCache(), failed to retrieve MarkTaskCache for " + pid + ", cache went wrong");
			throw new RuntimeException("updateMarkStaticsCache(), cache went wrong");
		}

		// maybe paper is marked again
		if (mt.getStatus() == 1) {
			mt.setStatus(2);
			msc.incrCompleted(1, score);
			logger.debug("updateMarkStaticsCache(), paper marked -> id:" + mt.getPaperid() + ", seq:" + mt.getSeq());
		}

		// also need to update score in cache for final marks
		updatePaperCache(quesid, pid, score, mt.getSeq(), isfinal);

		logger.debug("updateMarkStaticsCache(), assigned size->" + msc.getCurAssign().size());
		teaPaperC.opsForValue().set(TEA_PAPER_PREFIX + teacid + TEA_QUES_PREFIX + quesid, msc);
		return true;
	}

	/*
	 * get papers from teacher's cache for the question if no papers wanted
	 * retrieved, need to get from the questions' cache for final mark, need to
	 * populate more records
	 */
	private List<PaperImgCache> getPapersFromTeaCache(MarkStaticsCache msc, int seq, int wanted, boolean isfinal,
			String quesid) {
		Map<String, MarkTaskCache> task = msc.getCurAssign();
		List<String> pList = msc.getpList();
		List<PaperImgCache> piC = new ArrayList<PaperImgCache>();
		logger.debug("getPapersFromTeaCache(), currentAssign size ->" + task.size() + "\nkeys->" + task.keySet().toString()
						+ ", startSeq->" + seq + ", wanted->" + wanted + "\nPaperList->" + pList.toString());
		int i = 0, mid = -1;
		if (wanted > MAX_THRES)
			wanted = MAX_THRES;

		List<MarkTaskCache> firstMarkL = null;
		if (isfinal) {
			firstMarkL = paperC.opsForValue().get(QUES_PAPER_PREFIX + quesid);
			if (firstMarkL != null && !firstMarkL.isEmpty()) {
				mid = firstMarkL.size() / 2;
			} else {
				logger.error("getPapersFromTeaCache(), failed to retrieve tasks from cache for " + quesid);
				return null;
			}
		}

		// get paper ids from plist starting from seq specified
		if (seq > 0) {
			for (i = 0; i < (pList.size() - seq + 1) && i < wanted; i++) {
				MarkTaskCache mt = task.get(pList.get(i + seq - 1));
				piC.add(new PaperImgCache(mt.getPaperid(), mt.getImg()));
				if (isfinal) {
					// for final marks, number of returned records should be times of triple
					// 1, final mark element 2, first mark element 3, first mark element
					MarkTaskCache fm = firstMarkL.get(mt.getSeq());
					piC.add(new PaperImgCache(fm.getPaperid(), fm.getScore(), fm.getTeacid()));
					fm = firstMarkL.get(mt.getSeq() + mid);
					piC.add(new PaperImgCache(fm.getPaperid(), fm.getScore(), fm.getTeacid()));
				}
			}

			logger.info("getPapersFromTeaCache(), retrieved " + (i + 1) + 
					" papers from the beginning of " + seq + " for quesid->" + quesid);
		} else {
			// maybe user refreshed browser, get unmarked papers from cache
			int count = 0;
			for (i = 0; i < pList.size() && count < wanted; i++) {
				MarkTaskCache mt = task.get(pList.get(i));
				if (mt.getStatus() != 2) {
					piC.add(new PaperImgCache(mt.getPaperid(), mt.getImg()));
					if (isfinal) {
						// for final marks, number of returned records should be times of triple
						// 1, final mark element 2, first mark element 3, first mark element
						MarkTaskCache fm = firstMarkL.get(mt.getSeq());
						piC.add(new PaperImgCache(fm.getPaperid(), fm.getScore(), fm.getTeacid()));
						fm = firstMarkL.get(mt.getSeq() + mid);
						piC.add(new PaperImgCache(fm.getPaperid(), fm.getScore(), fm.getTeacid()));
					}
					count++;
				}
			}
			logger.debug("getPapersFromTeaCache(), " + count + " unmarked papers retrieved from cache.");
		}

		return piC;
	}

	private boolean updatePaperCache(String quesid, String pid, String score, int seq, boolean isfinal) {
		String cacheK = QUES_PAPER_PREFIX + quesid;
		if (isfinal) {
			cacheK = QUES_PAPER_PREFIX_FINAL + quesid;
		}
		List<MarkTaskCache> mtcM = paperC.opsForValue().get(cacheK);
		if (mtcM == null || mtcM.isEmpty()) {
			logger.error("updatePaperCache(), failed to retrieve cache information for question " + quesid);
			return false;
		}

		String teacid = ExamUtil.getCurrentUserId();
		MarkTaskCache mt = null;
		if (isfinal) {
			for (MarkTaskCache mc : mtcM) {
				if (mc.getPaperid().compareTo(pid) == 0 && mc.getTeacid().compareTo(teacid) == 0) {
					mt = mc;
					break;
				}
			}
			if (mt == null) {
				logger.error("updatePaperCache(), not find final item->" + pid + " in cache for " + cacheK);
				throw new RuntimeException(
						"updatePaperCache(), not find final item->" + pid + " in cache for " + cacheK);
			}
		} else {
			mt = mtcM.get(seq);
			if (mt.getTeacid() == null) {
				logger.error("updatePaperCache(), tried to update unassinged item, paper " + "info->id:"
						+ mt.getPaperid() + ",seq:" + mt.getSeq());
				throw new RuntimeException("tried to update unassinged item");

			}
			if (mt.getPaperid().compareTo(pid) != 0 || mt.getTeacid().compareTo(teacid) != 0) {
				logger.error("updatePaperCache(), invalid seq->" + seq + " for " + cacheK);
				throw new RuntimeException("updatePaperCache(), invalid seq->" + seq + " for " + cacheK);
			}
		}

		mt.setStatus(2);
		mt.setScore(Float.valueOf(score));

		paperC.opsForValue().set(cacheK, mtcM);
		return true;
	}

	public int getTotal(String quesid, String tid) {
		return teaPaperC.opsForValue().get(TEA_PAPER_PREFIX + tid + TEA_QUES_PREFIX + quesid).getTotal();
	}

	public int getMarked(String quesid, String tid) {
		return teaPaperC.opsForValue().get(TEA_PAPER_PREFIX + tid + TEA_QUES_PREFIX + quesid).getCompleted();
	}

	public String getProgress(String quesid, String tid) {
		return String.valueOf(this.getMarked(quesid, tid)) + "/" + String.valueOf(this.getTotal(quesid, tid));
	}

	public String getAveScore(String quesid, String tid) {
		return teaPaperC.opsForValue().get(TEA_PAPER_PREFIX + tid + TEA_QUES_PREFIX + quesid).getAvescore();
	}

	// this method should be called before score released
	private void clearQuesCache(String quesid) {
		if (quesid == null || quesid.isEmpty()) {
			logger.error("clearCache(), invalid quesid");
			throw new RuntimeException("clearCache(), invalid quesid");
		}
		String cacheK = this.QUES_PAPER_PREFIX + quesid;
		if (paperC.opsForValue().get(cacheK) != null)
			paperC.delete(cacheK);
		cacheK = this.QUES_PAPER_PREFIX_FINAL + quesid;
		if (paperC.opsForValue().get(cacheK) != null)
			paperC.delete(cacheK);
		logger.info("clearCache(), paper cache cleared for question " + quesid);

		List<String> teas = mtM.getTeachersByQid(quesid);
		for (String tid : teas) {
			cacheK = this.TEA_PAPER_PREFIX + tid + this.TEA_QUES_PREFIX + quesid;
			if (teaPaperC.opsForValue().get(cacheK) == null) {
				teaPaperC.delete(cacheK);
				logger.debug("clearCache(), cache cleared for " + cacheK);
			}
		}

	}

	/*
	 * clear subject cache after score released
	 */
	public void clearSubCache(String egsid) {
		List<String> quesL = mtM.getQuesIdsByExamGradeSubId(egsid);
		for (String ques : quesL) {
			clearQuesCache(ques);
		}
		logger.info("clearSubCache(), subject cache cleared, quesid->" + quesL.toString());
	}
}
