package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.cache.PaperCache;
import com.ustudy.exam.mapper.MarkTaskMapper;
import com.ustudy.exam.model.MetaMarkTask;
import com.ustudy.exam.model.QuesComb;
import com.ustudy.exam.model.QuesId;
import com.ustudy.exam.model.QuesMarkSum;
import com.ustudy.exam.model.QuesRegion;
import com.ustudy.exam.model.QuestionPaper;
import com.ustudy.exam.model.SingleAnswer;
import com.ustudy.exam.model.cache.PaperImgCache;
import com.ustudy.exam.model.BlockAnswer;
import com.ustudy.exam.model.ExamGradeSub;
import com.ustudy.exam.model.MarkTask;
import com.ustudy.exam.model.MarkTaskBrife;
import com.ustudy.exam.service.MarkTaskService;
import com.ustudy.exam.utility.ExamUtil;
import com.ustudy.exam.utility.OSSUtil;

@Service
public class MarkTaskServiceImpl implements MarkTaskService {

	private static final Logger logger = LogManager.getLogger(MarkTaskServiceImpl.class);
	
	@Autowired
	private MarkTaskMapper markTaskM;
	
	@Autowired
	private PaperCache paperC;
	
	@Override
	public List<MarkTaskBrife> getMarkTaskBrife(String teacid) {
		
		List<MetaMarkTask> mstL = markTaskM.getMetaMarkTask(teacid);
		List<MarkTaskBrife> stL = new ArrayList<MarkTaskBrife>();
		
		for (MetaMarkTask mst: mstL) {
			stL.add(assembleTaskBrife(mst));
		}
		
		return stL;
	}
	
	/*
	 * Steps for constructing mark task brife information
	 * 1, retrieve basic mark task information
	 * 2, retrieve question summary info
	 * 
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
		}
		else
			quesN = mt.getQuesno();
		QuesMarkSum sum = new QuesMarkSum(quesN, mt.getQuesType(), 0, 0, mst.getQuesid());
		List<QuesMarkSum> sumL = new ArrayList<QuesMarkSum>();
		sumL.add(sum);
		mt.setSummary(sumL);
		logger.debug("assembleTaskBrife(), " + mt.toString());
		return mt;
	}

	@Override
	public MarkTaskBrife getTaskPapers(String teacid, QuesComb comb) {
		/*
		 * 1, retrieve basic information for question blocks in QuesComb request
		 * 2, get assigned questions from question dispatcher
		 * 
		 */
		List<QuesId> qIds = comb.getQuesids();
		//get basic information from first question id
		MarkTaskBrife mt = markTaskM.getMetaTaskInfo(qIds.get(0).getId());
		mt.setMarkType(markTaskM.getMarkType(teacid, qIds.get(0).getId()));
		List<QuesMarkSum> sumL = new ArrayList<QuesMarkSum>();
		for (QuesId id: qIds) {
			// if there is multiple QuesId, they should be combined to retrieve student paper
			// ids here must be belonged to the same subject in the same grade of exam
			// assemble summary information here.
			QuesMarkSum qs = markTaskM.getQuesSum(id.getId());
			String quesN = null;
			if (qs.getQuesno() == null || qs.getQuesno().isEmpty() || qs.getQuesno().compareTo("0") == 0) {
				quesN = qs.getStartno() + "-" + qs.getEndno();
			}
			else
				quesN = qs.getQuesno();
			qs.setQuestionName(quesN);
			sumL.add(qs);
		}
		mt.setSummary(sumL);
		// retrieve corresponding students' papers
		mt.setPapers(requestPapers(sumL, comb.getStartSeq(), comb.getEndSeq()));
		logger.debug("getTaskPapers()," + mt.toString());
		return mt;
	}

	private List<QuestionPaper> requestPapers(List<QuesMarkSum> queS, int startSeq, int endSeq) {
		List<QuestionPaper> items = new ArrayList<QuestionPaper>();
		
		// List<String> paperIds = markTaskM.getPapersByQuesId();
		if (queS.isEmpty()) {
			logger.error("requestPapers(), question parameter is invalid -> " + queS.toString());
			return null;
		}
		
		// a little tricky here, get papers based the first question block meta information
		List<PaperImgCache> paperIds = paperC.retrievePapers(queS.get(0).getQuesid(), queS.get(0).getAssignMode());
		logger.debug("requestPapers()，number of retrieved paper is " + paperIds.size());
		int i = 0;
		for (PaperImgCache pImg: paperIds) {
			//fetch question info from each paper and group them together
			QuestionPaper stuP = new QuestionPaper();
			// need to set paper sequences here
			if (startSeq == -1)
				stuP.setPaperSeq(++i);
			else
				stuP.setPaperSeq(startSeq + (++i));
			List<BlockAnswer> blA = new ArrayList<BlockAnswer>();
			for (QuesMarkSum mark: queS) {
				BlockAnswer ba = markTaskM.getStuAnswer(mark.getQuesid(), pImg.getPaperid());
				if (ba == null) {
					// first time to view this paper, need set basic information
					ba = new BlockAnswer();
					ba.setBasicInfo(pImg.getPaperid(), mark.getQuesid(), pImg.getImg());
				}
				ba.setMetaInfo(mark.getQuestionName(), mark.getQuestionType(), mark.getMarkMode(), 
						mark.getFullscore());
				List<SingleAnswer> saL = new ArrayList<SingleAnswer>();
				if (mark.getQuesno() == null || mark.getQuesno().isEmpty() || mark.getQuesno().compareTo("0") == 0) {
					// need to retrieve detailed information of sub questions for this question block
					saL = markTaskM.getQuesDiv(mark.getQuesid());
				}
				ba.setSteps(saL);
				
				// set region informations for this question id
				// need to set answer image,
				List<QuesRegion> qreL = markTaskM.getPaperRegion(mark.getQuesid());
				String paperImg = ba.getPaperImg();
				String [] imgs = null;
				if (paperImg == null || paperImg.isEmpty()) {
					logger.warn("requestPapers(), paper image is empty from quesid " + mark.getQuesid() + pImg.toString());
				}
				else {
					imgs = paperImg.split(",");
					for (QuesRegion re: qreL) {
						if (re.getPageno() + 1 > imgs.length) {
							logger.error("requestPapers(), pageno not matched with real images ->" + imgs);
						}
						else
						    re.setAnsImg(imgs[re.getPageno()]);
					}
				}
				
				ba.setRegions(qreL);
				blA.add(ba);
			}
			stuP.setBlocks(blA);
			logger.debug("requestPapers()," + stuP);
			items.add(stuP);
		}
		
		return items;
	}
	
	@Override
	@Transactional
	public boolean updateMarkResult(QuestionPaper up) {
		// here only one student paper need to be handled
		int pid = up.getPaperSeq();
		List<BlockAnswer> blocks = up.getBlocks();
		for (BlockAnswer ba:blocks) {
			int realScore = 0, num = 0;
			if (ba.getSteps().isEmpty() == false) {
				List<SingleAnswer> saL = ba.getSteps();
				String ansId = markTaskM.getStuanswerId(pid, ba.getQuesid());
				for (SingleAnswer sa:saL) {
					num = markTaskM.insertStuAnswerDiv(sa, ansId);
					if (num != 1) {
						logger.error("updateMarkResult(),failed to insert record -> " + sa.toString());
					}
					else
						realScore += Float.valueOf(sa.getScore());
				}
			}
			
			String answer = ba.getAnswerImgData();
			String mark = ba.getMarkImgData();
			
			if (answer == null || answer.isEmpty() || mark == null || mark.isEmpty()) {
				return false;
			}
			
			try{
				String b64AnswerImg = answer.split(",")[1];
				byte[] answerBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(b64AnswerImg);
				OSSUtil.putObject(ba.getAnswerImg(), new ByteArrayInputStream(answerBytes));
				String b64MarkImg = mark.split(",")[1];
				byte[] markBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(b64MarkImg);
				OSSUtil.putObject(ba.getMarkImg(), new ByteArrayInputStream(markBytes));
			} catch (Exception e) {
				logger.error("updateMarkResult(), failed to upload image to oss -> " + e.getMessage());
				return false;
			}
			
			// score, answerType, markImg, and isMarked should be set to true
			ba.setAnswerImg1(ba.getAnswerImg());
			ba.setMarkImg1(ba.getMarkImg());
			ba.setTeacid1(ExamUtil.getCurrentUserId());
			if (realScore != 0)
			    ba.setScore(realScore);
			num = markTaskM.updateStuAnswer(ba);
			if (num != 1) {
				logger.warn("updateMarkResult(), " + num + " records updated. It seemed something went wrong.");
			}
			else
				logger.debug("updateMarkResult(), updated -> " + ba.toString());
		}
		
		// need to update statics here, make sure this is called only after database operations are completed
		for (BlockAnswer ba:blocks) {
			paperC.updateMarkStaticsCache(ba.getQuesid());
		}
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
		for (String id: quesIds) {
			MarkTask mt = getMarkTaskByEGSQuestion(egs, id);
			if (mt != null) {
				taskL.add(mt);
			}
		}
		return taskL;
	}

	@Override
	public MarkTask getMarkTaskByEGSQuestion(ExamGradeSub egs, String questionId) {
		MarkTask mt = markTaskM.getAllMarkTasksByQuesId(questionId);
		if (mt==null) {
			return mt;
		}
		mt.setMetaInfo(questionId, egs);
		if (mt.getMarkMode().compareTo("单评") == 0) {
			mt.setTeachersIds(markTaskM.getTeachersByQid(questionId));
			mt.setFinalMarkTeachersIds(new ArrayList<String>());
		}
		else if (mt.getMarkMode().compareTo("双评") == 0) {
			// get teachers for first screen
			mt.setTeachersIds(markTaskM.getTeachersByQidRole(questionId, "初评"));
			// get teachers for final screen
			mt.setFinalMarkTeachersIds(markTaskM.getTeachersByQidRole(questionId, "终评"));
		}
		else {
			logger.warn("getMarkTasksBySub(), wrong type -> " + mt.getMarkMode());
		}
		return mt;
	}

	@Override
	@Transactional
	public boolean createMarkTask(MarkTask mt) {
		// for certain questions, assign teachers for mark, such as first round mark, final mark
		
		// populate first round mark teachers
		List<String> teaL = mt.getTeachersIds();
		int num = 0;
		if (teaL != null && !teaL.isEmpty()) {
			for (String id: teaL) {
				MetaMarkTask mmt = new MetaMarkTask(id, mt.getQuestionId(), 0, "标准", "初评");
				num = markTaskM.populateMetaMarkTask(mmt);
				if (num != 1) {
					logger.error("createMarkTask(), failed to populate record " + mmt.toString());
					return false;
				}
			}
		}
		
		teaL = mt.getFinalMarkTeachersIds();
		if (teaL != null && !teaL.isEmpty()) {
			for (String id: teaL) {
				MetaMarkTask mmt = new MetaMarkTask(id, mt.getQuestionId(), 0, "标准", "终评");
				num = markTaskM.populateMetaMarkTask(mmt);
				if (num != 1) {
					logger.error("createMarkTask(), failed to populate record " + mmt.toString());
					return false;
				}
				logger.debug("createMarkTask(), populate record succeded -> " + mmt.toString());
			}
		}
		
		// update time limit for specified question id
		if (mt.getTimeLimit() != 0) {
			num = markTaskM.setTimeLimit(mt.getTimeLimit(), mt.getQuestionId());
			if (num != 1) {
				logger.error("createMarkTask(), failed to update time limit for " + mt.getQuestionId());
				return false;
			}
			logger.debug("createMarkTask(), update time limit succeded");
		}
		
		// need to set owner here
		
		return true;
	}

	@Override
	@Transactional
	public boolean updateMarkTask(MarkTask mt) {
		if (deleteMarkTaskByQues(mt)) {
			if (!createMarkTask(mt)) {
				logger.error("updateMarkTask(), failed to create mark task ->" + mt.toString());
				return false;
			}
			logger.debug("updateMarkTask(), updateMarkTask successfully.");
		}
		else {
			logger.error("updateMarkTask(), failed to delete mark task ->" + mt.toString());
			return false;
		}
		
		return true;
	}

	@Transactional
	private boolean deleteMarkTaskByQues(MarkTask mt) {
		int num = 0;
		if (mt.getTeachersIds() != null && !mt.getTeachersIds().isEmpty()) {
			num = markTaskM.deleteMetaMarkTaskByQues(mt.getQuestionId(), "初评");
			if (num <= 0) {
				logger.error("deleteMarkTaskByQues(), mark task delete failed -> " + mt.getTeachersIds());
				return false;
			}
			logger.debug("deleteMarkTaskByQues(), mark task deleted -> " + mt.getTeachersIds());
		}
		
		if (mt.getFinalMarkTeachersIds() != null && !mt.getFinalMarkTeachersIds().isEmpty()) {
			num = markTaskM.deleteMetaMarkTaskByQues(mt.getQuestionId(), "终评");
			if (num <= 0) {
				logger.error("deleteMarkTaskByQues(), mark task delete failed -> " + mt.getFinalMarkTeachersIds());
				return false;
			}
			logger.debug("deleteMarkTaskByQues(), mark task deleted -> " + mt.getFinalMarkTeachersIds());
		}
		
		return true;
	}
	
	@Override
	@Transactional
	public boolean deleteMarkTask(MarkTask mt) {
		List<String> teaL = mt.getTeachersIds();
		int num = 0;
		for (String id: teaL) {
			num = markTaskM.deleteMetaMarkTaskByTeacher(id, mt.getQuestionId(), "初评");
			if (num != 1) {
				logger.error("deleteMarkTask(), delete failed with num ->" + num);
				return false;
			}
			logger.debug("deleteMarkTask(), deleted mark task for " + id);
		}
		teaL = mt.getFinalMarkTeachersIds();
		for (String id: teaL) {
			num = markTaskM.deleteMetaMarkTaskByTeacher(id, mt.getQuestionId(), "终评");
			if (num != 1) {
				logger.error("deleteMarkTask(), delete failed with num ->" + num);
				return false;
			}
			logger.debug("deleteMarkTask(), deleted mark task for " + id);
		}
		
		return true;
	}
}
