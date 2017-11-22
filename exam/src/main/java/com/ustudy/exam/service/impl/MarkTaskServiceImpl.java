package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.exam.mapper.MarkTaskMapper;
import com.ustudy.exam.model.MetaMarkTask;
import com.ustudy.exam.model.QuesComb;
import com.ustudy.exam.model.QuesId;
import com.ustudy.exam.model.QuesMarkSum;
import com.ustudy.exam.model.QuestionPaper;
import com.ustudy.exam.model.SingleAnswer;
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
		mt.setScoreType(mst.getMarkType());
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
		mt.setScoreType(markTaskM.getMarkType(teacid, qIds.get(0).getId()));
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
		
		List<String> paperIds = markTaskM.getPapersByQuesId(queS.get(0).getQuesid());
		logger.debug("requestPapers()，number of retrieved paper is " + paperIds.size());
		int i = 0;
		for (String pId: paperIds) {
			//fetch question info from each paper and group them together
			QuestionPaper stuP = new QuestionPaper();
			// need to set paper sequences here
			if (startSeq == -1)
				stuP.setPaperSeq(++i);
			else
				stuP.setPaperSeq(startSeq + (++i));
			List<BlockAnswer> blA = new ArrayList<BlockAnswer>();
			for (QuesMarkSum mark: queS) {
				BlockAnswer ba = markTaskM.getStuAnswer(mark.getQuesid(), pId);
				ba.setMetaInfo(mark.getQuestionName(), mark.getQuestionType(), mark.getMarkMode(), mark.getFullscore());
				if (mark.getQuesno() == null || mark.getQuesno().isEmpty() || mark.getQuesno().compareTo("0") == 0) {
					// need to retrieve detailed information of sub questions for this question block
					List<SingleAnswer> saL = markTaskM.getQuesDiv(mark.getQuesid());
					ba.setSteps(saL);
				}
				blA.add(ba);
			}
			stuP.setBlocks(blA);
			logger.debug("requestPapers()," + stuP);
			items.add(stuP);
		}
		
		return items;
	}
	
	@Override
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
						realScore += sa.getScore();
				}
			}
			
			String answer = ba.getAnswerImgData();
			String mark = ba.getMarkImgData();
			
			if (answer == "" || mark == "") {
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
			taskL.add(mt);
		}
		return taskL;
	}

	@Override
	public MarkTask getMarkTaskByEGSQuestion(ExamGradeSub egs, String questionId) {
		MarkTask mt = markTaskM.getAllMarkTasksByQuesId(questionId);
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
		if (deleteMarkTask(mt)) {
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

	@Override
	@Transactional
	public boolean deleteMarkTask(MarkTask mt) {
		List<String> teaL = mt.getTeachersIds();
		int num = 0;
		for (String id: teaL) {
			num = markTaskM.deleteMetaMarkTask(id, mt.getQuestionId(), "初评");
			if (num != 1) {
				logger.error("deleteMarkTask(), delete failed with num ->" + num);
				return false;
			}
			logger.debug("deleteMarkTask(), deleted mark task for " + id);
		}
		teaL = mt.getFinalMarkTeachersIds();
		for (String id: teaL) {
			num = markTaskM.deleteMetaMarkTask(id, mt.getQuestionId(), "终评");
			if (num != 1) {
				logger.error("deleteMarkTask(), delete failed with num ->" + num);
				return false;
			}
			logger.debug("deleteMarkTask(), deleted mark task for " + id);
		}
		
		return true;
	}
}
