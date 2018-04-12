package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.DatatypeConverter;

import java.io.ByteArrayInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.exam.dao.ExamSubjectDao;
import com.ustudy.exam.mapper.ConfigMapper;
import com.ustudy.exam.mapper.MarkTaskMapper;
import com.ustudy.exam.model.MetaMarkTask;
import com.ustudy.exam.model.PaperRequest;
import com.ustudy.exam.model.QuesComb;
import com.ustudy.exam.model.QuesId;
import com.ustudy.exam.model.QuesMarkSum;
import com.ustudy.exam.model.ImgRegion;
import com.ustudy.exam.model.MarkAnsImg;
import com.ustudy.exam.model.QuestionPaper;
import com.ustudy.exam.model.SingleAnswer;
import com.ustudy.exam.model.cache.FirstMarkImgRecord;
import com.ustudy.exam.model.cache.FirstMarkRecord;
import com.ustudy.exam.model.cache.MarkUpdateResult;
import com.ustudy.exam.model.cache.PaperImgCache;
import com.ustudy.exam.model.BlockAnswer;
import com.ustudy.exam.model.ExamGradeSub;
import com.ustudy.exam.model.MarkTask;
import com.ustudy.exam.model.MarkTaskBrife;
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.service.MarkTaskService;
import com.ustudy.exam.service.impl.cache.PaperCache;
import com.ustudy.exam.service.impl.cache.TeacherCache;
import com.ustudy.exam.utility.ExamUtil;
import com.ustudy.exam.utility.OSSMetaInfo;
import com.ustudy.exam.utility.OSSUtil;

@Service
public class MarkTaskServiceImpl implements MarkTaskService {

	private static final Logger logger = LogManager.getLogger(MarkTaskServiceImpl.class);

	@Resource
	private ExamSubjectDao examSubjectDao;

	@Autowired
	private MarkTaskMapper markTaskM;

	@Autowired
	private PaperCache paperC;

	@Autowired
	private TeacherCache teaC;

	@Autowired
	private ConfigMapper cgM;

	@Override
	public List<MarkTaskBrife> getMarkTaskBrife(String teacid) {

		List<MetaMarkTask> mstL = markTaskM.getMetaMarkTask(teacid);
		List<MarkTaskBrife> stL = new ArrayList<MarkTaskBrife>();

		for (MetaMarkTask mmt : mstL) {
			MarkTaskBrife mtb = assembleTaskBrife(mmt);
			int total = paperC.getTotal(mmt.getQuesid(), mmt.getTeacid());
			int marked = paperC.getMarked(mmt.getQuesid(), mmt.getTeacid());

			mtb.getSummary().get(0).setAvgScore(paperC.getAveScore(mmt.getQuesid(), mmt.getTeacid()));
			mtb.getSummary().get(0).setMarkedNum(String.valueOf(marked));
			mtb.getSummary().get(0).setProgress(String.valueOf(marked) + "/" + String.valueOf(total));

			mtb.setProgress(String.valueOf(marked) + "/" + String.valueOf(total));
			stL.add(mtb);
		}

		return stL;
	}

	/*
	 * Steps for constructing mark task brife information 
	 * 1, retrieve basic mark task information 
	 * 2, retrieve question summary info
	 */
	private MarkTaskBrife assembleTaskBrife(MetaMarkTask mst) {

		MarkTaskBrife mt = markTaskM.getMarkTaskBrife(mst.getQuesid());
		mt.setTeacherId(mst.getTeacid());
		mt.setTeacherName(ExamUtil.retrieveSessAttr("uname"));
		mt.setId(mst.getId());
		mt.setMarkType(mst.getMarktype());
		logger.debug("assembleTaskBrife()," + mt.toString());
		// assemble question summary information
		String quesN = null;
		if (mt.getQuesno() == null || mt.getQuesno().isEmpty() || mt.getQuesno().compareTo("0") == 0) {
			quesN = mt.getStartno() + "-" + mt.getEndno();
		} else
			quesN = mt.getQuesno();
		QuesMarkSum sum = markTaskM.getQuesSum(mst.getQuesid());
		sum.setQuestionName(quesN);
		sum.setQuestionType(mt.getQuesType());

		if (sum.getFullscore() >= 20) {
			sum.setComposable(false);
			logger.debug("assembleTaskBrife(), " + sum.getQuesid() + " is not composable.");
		}

		// new QuesMarkSum(quesN, mt.getQuesType(), null, null, mst.getQuesid());
		List<QuesMarkSum> sumL = new ArrayList<QuesMarkSum>();
		sumL.add(sum);
		// initialize paper cache here to get number of allocated numbers
		List<PaperRequest> prS = new ArrayList<PaperRequest>();
		for (QuesMarkSum que : sumL) {
			prS.add(new PaperRequest(que.getQuesid(), que.getAssignMode(), que.getMarkMode(), -1, -1));
		}
		paperC.retrievePapers(prS);
		mt.setSummary(sumL);
		// here, set paper to empty array to make frontend more easy
		mt.setPapers(new ArrayList<QuestionPaper>());

		logger.debug("assembleTaskBrife(), " + mt.toString());
		return mt;
	}

	@Override
	public MarkTaskBrife getTaskPapers(String teacid, QuesComb comb) {
		/*
		 * 1, retrieve basic information for question blocks in QuesComb request 
		 * 2, get assigned questions from question dispatcher
		 */
		List<QuesId> qIds = comb.getQuesids();
		// get basic information from first question id
		MarkTaskBrife mt = markTaskM.getMetaTaskInfo(qIds.get(0).getId());
		mt.setTeacherId(teacid);
		mt.setTeacherName(ExamUtil.retrieveSessAttr("uname"));
		mt.setMarkType(markTaskM.getMarkType(teacid, qIds.get(0).getId()));
		List<QuesMarkSum> sumL = new ArrayList<QuesMarkSum>();
		for (QuesId id : qIds) {
			// if there is multiple QuesId, they should be combined to retrieve student papers
			// ids here must be belonged to the same subject in the same grade of exam
			// assemble summary information here.
			QuesMarkSum qs = markTaskM.getQuesSum(id.getId());
			String quesN = null;
			if (qs.getQuesno() == null || qs.getQuesno().isEmpty() || qs.getQuesno().compareTo("0") == 0) {
				quesN = qs.getStartno() + "-" + qs.getEndno();
			} else
				quesN = qs.getQuesno();
			qs.setQuestionName(quesN);

			// if full score greater than 20, this question is not composable
			if (qs.getFullscore() >= 20) {
				qs.setComposable(false);
				logger.debug("getTaskPapers(), " + qs.getQuesid() + " is not composable");
			}
			sumL.add(qs);
		}

		// retrieve corresponding students' papers
		mt.setPapers(requestPapers(sumL, comb.getStartSeq(), comb.getEndSeq(), teacid,
				(mt.getMarkType().compareTo("终评") == 0)));
		int total = 0, marked = 0;
		for (QuesMarkSum qm : sumL) {
			int to = paperC.getTotal(qm.getQuesid(), teacid);
			int ma = paperC.getMarked(qm.getQuesid(), teacid);
			qm.setProgress(String.valueOf(ma) + "/" + String.valueOf(to));
			qm.setAvgScore(paperC.getAveScore(qm.getQuesid(), teacid));
			total += to;
			marked += ma;
		}
		mt.setProgress(String.valueOf(marked) + "/" + String.valueOf(total));
		mt.setSummary(sumL);
		logger.debug("getTaskPapers()," + mt.toString());
		return mt;
	}

	private List<QuestionPaper> requestPapers(List<QuesMarkSum> queS, int startSeq, int endSeq, String teacid,
			boolean isfinal) {
		List<QuestionPaper> items = new ArrayList<QuestionPaper>();

		if (queS.isEmpty()) {
			logger.error("requestPapers(), question parameter is invalid -> " + queS.toString());
			return null;
		}

		List<PaperRequest> prS = new ArrayList<PaperRequest>();
		for (QuesMarkSum que : queS) {
			prS.add(new PaperRequest(que.getQuesid(), que.getAssignMode(), que.getMarkMode(), startSeq, endSeq));
		}

		// need to initialize cache for each question ids
		Map<String, List<PaperImgCache>> papers = paperC.retrievePapers(prS);
		int maxSize = 0;
		for (List<PaperImgCache> ppC : papers.values()) {
			if (maxSize < ppC.size())
				maxSize = ppC.size();
		}
		logger.info("requestPapers()，max number of retrieved papers is " + maxSize);

		int i = 0;
		if (startSeq == -1) {
			int completed = paperC.getMarked(queS.get(0).getQuesid(), teacid);
			for (QuesMarkSum que : queS) {
				if (completed > paperC.getMarked(que.getQuesid(), teacid)) {
					completed = paperC.getMarked(que.getQuesid(), teacid);
				}
			}
			// for fetch fresh papers, only need to retrieve number of already marked papers
			i = completed + 1;
		} else {
			i = startSeq;
		}

		for (int j = 0; j < maxSize; j++) {
			// fetch question info from each paper and group them together
			QuestionPaper stuP = new QuestionPaper();
			// need to set paper sequences here
			stuP.setPaperSeq(i++);

			List<BlockAnswer> blA = new ArrayList<BlockAnswer>();
			for (QuesMarkSum mark : queS) {
				List<PaperImgCache> pImg = papers.get(mark.getQuesid());
				if (j >= pImg.size()) {
					continue;
				}
				BlockAnswer ba = markTaskM.getAnswer(mark.getQuesid(), pImg.get(j).getPaperid(), teacid);
				if (ba == null) {
					// first time to view this paper, need set basic information
					ba = new BlockAnswer();
					ba.setBasicInfo(pImg.get(j).getPaperid(), mark.getQuesid(), pImg.get(j).getImg());
				}
				logger.trace("requestPapers(), answer information: quesid->" + ba.getQuesid() + ", paperid->"
						+ ba.getPaperId() + ", ansimgs->" + ba.getPaperImg());

				ba.setMetaInfo(mark.getQuestionName(), mark.getQuestionType(), mark.getMarkMode(), mark.getFullscore());
				List<SingleAnswer> saL = new ArrayList<SingleAnswer>();
				if (mark.getQuesno() == null || mark.getQuesno().isEmpty() || mark.getQuesno().compareTo("0") == 0) {
					// need to retrieve detailed information of sub questions for this question
					// block
					if (ba.isMarked()) {
						saL = markTaskM.getMarkedQuesDiv(mark.getQuesid(), ba.getPaperId());
					} else
						saL = markTaskM.getQuesDiv(mark.getQuesid());

				}

				ba.setSteps(saL);

				// if final mark, need to process following two elements
				if (isfinal) {
					ba.setIsfinal(true);
					FirstMarkRecord[] recs = new FirstMarkRecord[2];
					String tid = pImg.get(j + 1).getTeacid();
					recs[0] = new FirstMarkRecord(tid, teaC.getTeaName(tid),
							String.valueOf(pImg.get(j + 1).getScore()));
					tid = pImg.get(j + 2).getTeacid();
					recs[1] = new FirstMarkRecord(tid, teaC.getTeaName(tid),
							String.valueOf(pImg.get(j + 2).getScore()));
					ba.setMarkRec(recs);
					ba.setScoreGap(String.valueOf(Math.abs(pImg.get(j + 1).getScore() - pImg.get(j + 2).getScore())));
					logger.debug("requestPapers(), first mark records ->" + recs[0].toString() + 
							"," + recs[1].toString());
				}

				// set region informations for this question id
				// need to set answer image,
				List<ImgRegion> qreL = markTaskM.getPaperRegion(mark.getQuesid());
				String paperImg = ba.getPaperImg();
				String[] imgs = null;
				if (paperImg == null || paperImg.isEmpty()) {
					logger.error("requestPapers(), paper image is vacant for quesid " + mark.getQuesid()
							+ pImg.get(j).toString());
				} else {
					List<FirstMarkImgRecord> fMImgs = null;
					if (isfinal) {
						fMImgs = markTaskM.getFirstMarkImgs(mark.getQuesid(), pImg.get(j).getPaperid());
						logger.debug("requestPapers(), first marked imgs for quesid->" + mark.getQuesid()
								+ ", paperid->" + pImg.get(j).getPaperid() + ",imgs->" + fMImgs.toString());
					}
					imgs = paperImg.split(",");

					List<MarkAnsImg> markImgs = null;
					if (ba.isMarked()) {
						markImgs = markTaskM.getMarkAnsImgs(mark.getQuesid(), pImg.get(j).getPaperid(), teacid);
						logger.debug("requestPapers(), marked imgs for quesid->" + mark.getQuesid() + ", paperid->"
								+ pImg.get(j).getPaperid() + ", imgs->" + markImgs.toString());
					}

					for (int k = 0; k < qreL.size(); k++) {
						// page no is real pageno, it should not be greater than imgs.length
						ImgRegion re = qreL.get(k);
						logger.trace("requestPapers(), region->" + re.toString());

						/*
						 * Noted: student papers is composed of several pages, each page has several regions 
						 * One question maybe resides in several pages and regions
						 * Pageno is started from 0
						 */
						if (re.getPageno() + 1 > imgs.length) {
							logger.error("requestPapers(), pageno not matched with real images ->" + imgs);
							throw new RuntimeException("requestPapers(), pageno " + re.getPageno()
									+ " not matched with " + paperImg.toString());
						}

						re.setAnsImg(imgs[re.getPageno()]);

						// maybe this is remark operation
						if (ba.isMarked() && markImgs != null && !markImgs.isEmpty()) {
							if (k < markImgs.size()) {
								logger.debug("requestPapers(), region->" + re.getPageno() + ", marked imgs->"
										+ markImgs.get(k).toString());
								MarkAnsImg mm = markImgs.get(k);
								if (mm != null && mm.getRegionId() == re.getId()) {
									re.setMarkImg(mm.getMarkImg());
									re.setAnsMarkImg(mm.getAnsMarkImg());
								}
							} else {
								logger.warn("requestPapers(), no marked imgs for region->" + re.toString());
								re.setMarkImg(null);
								re.setAnsMarkImg(null);
							}

						}
						// for final marks, need to add marked papers here
						if (isfinal) {
							FirstMarkImgRecord[] fmRec = new FirstMarkImgRecord[2];

							/*
							 * first mark records order by pageno and contains two teachers' first mark records 
							 * loop "fMImgs" to get the corresponding records
							 */
							for (FirstMarkImgRecord fm : fMImgs) {
								if (fm.getQareaId() == re.getId()) {
									if (fm.getTeacid().equals(pImg.get(j + 1).getTeacid())) {
										fmRec[0] = fm;
										if (fmRec[1] != null)
											break;
									} else if (fm.getTeacid().equals(pImg.get(j + 2).getTeacid())) {
										fmRec[1] = fm;
										if (fmRec[0] != null)
											break;
									} else {
										logger.error("requestPapers(), unexpected first mark record->" + fm);
										throw new RuntimeException("Unexpected first mark record->" + fm.toString());
									}
								}
							}

							re.setFirstMarkImgs(fmRec);
							logger.debug("requestPapers(), firstMarkImg[0]->" + fmRec[0].toString()
									+ ", firstMarkImg[1]->" + fmRec[1].toString());
						}
					}

					// for final marks, 3 paper image cache in one group
					if (isfinal) {
						j += 2;
					}
				}

				ba.setRegions(qreL);
				blA.add(ba);
			}
			stuP.setBlocks(blA);
			// logger.debug("requestPapers()," + stuP);
			items.add(stuP);
		}

		return items;
	}

	@Override
	@Transactional
	public List<MarkUpdateResult> updateMarkResult(QuestionPaper up, Long egsId) {

		ExamSubject es = examSubjectDao.getMarkSwitchById(egsId);
		if (es.getMarkSwitch() == false) {
			logger.error("updateMarkResult(), the marking is already paused.");
			throw new RuntimeException("updateMarkResult(), the marking is paused");
		}
		// here only one student paper need to be handled
		// int pid = up.getPaperSeq();
		List<BlockAnswer> blocks = up.getBlocks();
		// need to find out which score should be updated
		String teacid = ExamUtil.getCurrentUserId();
		if (teacid == null || teacid.isEmpty()) {
			logger.error("updateMarkResult(), failed to get login user, maybe service restarted.");
			throw new RuntimeException("updateMarkResult(), failed to get login user");
		}

		for (BlockAnswer ba : blocks) {

			if (!ba.getSteps().isEmpty()) {
				float realScore = 0;
				List<SingleAnswer> saL = ba.getSteps();
				for (SingleAnswer sa : saL) {
					realScore += Float.valueOf(sa.getScore());
				}
				if (realScore != 0)
					ba.setScore(String.valueOf(realScore));
				/*
				 * else ba.setScore("0");
				 */
			}

			int num = markTaskM.insertAnswer(ba, teacid);
			if (num < 0 || num > 2 || ba.getId() < 0) {
				logger.error("updateMarkResult(), set answer record for mark result failed. number->" + num
						+ ",pri key->" + ba.getId());
				throw new RuntimeException("updateMarkResult(), set answer record failed.");
			} else
				logger.debug("updateMarkResult(), answer updated and primary key->" + ba.getId() + " returned " + num);

			if (!ba.getSteps().isEmpty()) {
				List<SingleAnswer> saL = ba.getSteps();
				for (SingleAnswer sa : saL) {
					num = markTaskM.insertAnswerStep(sa, ba.getId());
					if (num < 0 || num > 2) {
						logger.error("updateMarkResult(),failed to insert record -> " + sa.toString());
						throw new RuntimeException("updateMarkResult(), insertAnswerStep() returned " + num);
					}
				}
			}

			if (!saveAnsImgByRegion(ba.getRegions(), ba.getId(), teacid)) {
				logger.error("updateMarkResult(), save answer images failed." + ba.getRegions().toString());
				throw new RuntimeException("updateMarkResult(), save answer images failed");
			}

		}

		// need to update statics here, make sure this is called only after database
		// operations are completed
		List<MarkUpdateResult> murL = new ArrayList<MarkUpdateResult>();
		for (BlockAnswer ba : blocks) {
			paperC.updateMarkStaticsCache(ba.getQuesid(), ba.getPaperId(), ba.getScore(), ba.isIsfinal());
			murL.add(new MarkUpdateResult(ba.getQuestionName(), paperC.getProgress(ba.getQuesid(), teacid),
					paperC.getAveScore(ba.getQuesid(), teacid)));
		}
		return murL;
	}

	private boolean saveAnsImgByRegion(List<ImgRegion> irs, int id, String teacid) {

		if (irs == null || irs.isEmpty()) {
			logger.error("saveAnsImgByRegion(), regions are absent.");
			return false;
		}

		for (ImgRegion ir : irs) {
			String mark = ir.getMarkImgData();

			if (mark != null && !mark.isEmpty()) {
				try {
					String x = String.valueOf(ir.getPosx());
					String y = String.valueOf(ir.getPosy());
					String w = String.valueOf(ir.getWidth());
					String h = String.valueOf(ir.getHeight());

					// upload mark image
					String b64MarkImg = mark.split(",")[1];
					byte[] markBytes = DatatypeConverter.parseBase64Binary(b64MarkImg);

					if (OSSUtil.getClient() == null) {
						// need to initialize OSSMetaInfo
						logger.info("saveAnsImgByRegion(), initialize OSSClient before use");
						synchronized (OSSMetaInfo.class) {
							if (OSSUtil.getClient() == null) {
								OSSMetaInfo omi = cgM.getOSSInfo("oss");
								logger.debug("saveAnsImgByRegion(), OSS Client init with->" + omi.toString());
								OSSUtil.initOSS(omi);
							}
						}
					}

					OSSUtil.putObject(ir.getMarkImg(), new ByteArrayInputStream(markBytes));

					// upload answer&mark image
					OSSUtil.putObject(ir.getAnsImg(), ir.getMarkImg(), ir.getAnsMarkImg(), x, y, w, h);
				} catch (Exception e) {
					logger.error("saveAnsImgByRegion(), failed to upload image to oss -> " + e.getMessage());
					return false;
				}
				int ret = markTaskM.insertAnsImg(ir, id);
				if (ret > 2 || ret < 0) {
					logger.error("saveAnsImgByRegion(), failed to save answer images, returned " + ret);
					return false;
				}
			} else {
				logger.error("saveAnsImgByPage(), ansmark image or mark image missed.");
				return false;
			}
		}

		logger.debug("updateMarkResult(), save answer image succeed. " + irs.toString());
		return true;
	}

	@Override
	public List<MarkTask> getMarkTasksBySub(ExamGradeSub egs) {
		if (egs.getExamId().isEmpty() || egs.getGradeId().isEmpty() || egs.getSubjectId().isEmpty()) {
			logger.warn("getMarkTasksBySub(), parameter is not valid");
			return null;
		}
		List<String> quesIds = markTaskM.getQuesIdsByExamGradeSub(egs);
		if (quesIds == null || quesIds.isEmpty()) {
			logger.warn("getMarkTasksBySub(), no question ids retrieved for " + egs.toString());
			return null;
		}
		List<MarkTask> taskL = new ArrayList<MarkTask>();
		for (String id : quesIds) {
			MarkTask mt = getMarkTaskByEGSQuestion(egs, id);
			if (mt != null) {
				taskL.add(mt);
			}
		}
		if (taskL.isEmpty()) {
			return null;
		}
		return taskL;
	}

	@Override
	public MarkTask getMarkTaskByEGSQuestion(ExamGradeSub egs, String questionId) {
		MarkTask mt = markTaskM.getAllMarkTasksByQuesId(questionId);
		if (mt == null) {
			return mt;
		}
		mt.setMetaInfo(questionId, egs);
		String markmode = mt.getMarkMode();
		if (markmode == null) {
			logger.error("getMarkTaskByEGSQuestion(), mark mode is not set for " + questionId);
			return null;
		}
		if (markmode.compareTo("单评") == 0) {
			mt.setTeachersIds(markTaskM.getTeachersByQid(questionId));
			mt.setFinalMarkTeachersIds(new ArrayList<String>());
		} else if (markmode.compareTo("双评") == 0) {
			// get teachers for first screen
			mt.setTeachersIds(markTaskM.getTeachersByQidType(questionId, "初评"));
			// get teachers for final screen
			mt.setFinalMarkTeachersIds(markTaskM.getTeachersByQidType(questionId, "终评"));
		} else {
			logger.error("getMarkTaskByEGSQuestion(), wrong type -> " + mt.getMarkMode());
			return null;
		}
		return mt;
	}

	@Override
	@Transactional
	public boolean createMarkTask(MarkTask mt) {
		// for certain questions, assign teachers for mark, such as first round mark,
		// final mark

		// populate first round mark teachers
		List<String> teaL = mt.getTeachersIds();
		int num = 0;
		String mtype = "标准";
		if (mt.getFinalMarkTeachersIds() != null && !mt.getFinalMarkTeachersIds().isEmpty()) {
			mtype = "初评";
		}

		if (teaL != null && !teaL.isEmpty()) {
			for (String id : teaL) {
				MetaMarkTask mmt = new MetaMarkTask(id, mt.getQuestionId(), 0, mtype);
				num = markTaskM.populateMetaMarkTask(mmt);
				if (num != 1) {
					logger.error("createMarkTask(), failed to populate record " + mmt.toString());
					return false;
				}
			}
		}

		teaL = mt.getFinalMarkTeachersIds();
		if (teaL != null && !teaL.isEmpty()) {
			for (String id : teaL) {
				MetaMarkTask mmt = new MetaMarkTask(id, mt.getQuestionId(), 0, "终评");
				num = markTaskM.populateMetaMarkTask(mmt);
				if (num != 1) {
					logger.error("createMarkTask(), failed to populate record " + mmt.toString());
					return false;
				}
				logger.debug("createMarkTask(), populate record succeded -> " + mmt.toString());
			}
			mt.setMarkMode("双评");
		} else
			mt.setMarkMode("单评");

		// update time limit, assign mode, mark mode, teac_owner for specified question id
		num = markTaskM.updateQuestionMeta(mt);
		if (num != 1) {
			logger.error("createMarkTask(), failed to update corresponding question information. " + mt.toString());
			return false;
		}
		logger.info("createMarkTask(), marktask created successfully.");

		return true;
	}

	@Override
	@Transactional
	public boolean updateMarkTask(MarkTask mt) {
		if (deleteMarkTask(mt)) {
			if (!createMarkTask(mt)) {
				logger.error("updateMarkTask(), failed to create mark task ->" + mt.toString());
				return false;
			}
			logger.debug("updateMarkTask(), updateMarkTask successfully.");
		} else {
			logger.error("updateMarkTask(), failed to delete mark task ->" + mt.toString());
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ustudy.exam.service.MarkTaskService#deleteMarkTask(com.ustudy.exam.model.
	 * MarkTask)
	 */
	@Override
	@Transactional
	public boolean deleteMarkTask(MarkTask mt) {
		logger.debug("deleteMarkTask(), clear mark tasks assigned for question " + mt.getQuestionId());
		int ret = markTaskM.deleteMetaMarkTaskByQues(mt.getQuestionId());
		if (ret <= 0) {
			logger.error("deleteMarkTask(), mark task delete failed -> " + mt.toString());
			return false;
		}

		logger.debug("deleteMarkTask(), " + ret + " mark task records cleared");
		return true;
	}

	@Override
	public OSSMetaInfo loadOSSInfo(String key) {

		if (key == null || key.isEmpty()) {
			logger.error("loadOSSInfo(), load oss info failed, key is empty");
			throw new RuntimeException("load oss info failed, key is empty");
		}

		OSSMetaInfo omi = cgM.getOSSInfo(key);
		logger.debug("loadOSSInfo(), oss info->" + omi.toString());
		return omi;
	}

}
