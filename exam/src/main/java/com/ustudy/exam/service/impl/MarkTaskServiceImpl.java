package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.MarkTaskMapper;
import com.ustudy.exam.model.MetaScoreTask;
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
	private MarkTaskMapper scoreTaskM;
	
	@Override
	public List<MarkTaskBrife> getMarkTaskBrife(String teacid) {
		
		List<MetaScoreTask> mstL = scoreTaskM.getMetaScoreTask(teacid);
		List<MarkTaskBrife> stL = new ArrayList<MarkTaskBrife>();
		
		for (MetaScoreTask mst: mstL) {
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
	private MarkTaskBrife assembleTaskBrife(MetaScoreTask mst) {
		
		MarkTaskBrife mt = scoreTaskM.getMarkTaskBrife(mst.getQuesid());
		mt.setTeacherId(mst.getTeacid());
		mt.setTeacherName(ExamUtil.retrieveSessAttr("uname"));
		mt.setId(mst.getId());
		mt.setScoreType(mst.getScoreType());
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
		MarkTaskBrife mt = scoreTaskM.getMetaTaskInfo(qIds.get(0).getId());
		mt.setScoreType(scoreTaskM.getMarkType(teacid, qIds.get(0).getId()));
		List<QuesMarkSum> sumL = new ArrayList<QuesMarkSum>();
		for (QuesId id: qIds) {
			// if there is multiple QuesId, they should be combined to retrieve student paper
			// ids here must be belonged to the same subject in the same grade of exam
			// assemble summary information here.
			QuesMarkSum qs = scoreTaskM.getQuesSum(id.getId());
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
		
		List<String> paperIds = scoreTaskM.getPapersByQuesId(queS.get(0).getQuesid());
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
				BlockAnswer ba = scoreTaskM.getStuAnswer(mark.getQuesid(), pId);
				ba.setMetaInfo(mark.getQuestionName(), mark.getQuestionType(), mark.getMarkMode(), mark.getFullscore());
				if (mark.getQuesno() == null || mark.getQuesno().isEmpty() || mark.getQuesno().compareTo("0") == 0) {
					// need to retrieve detailed information of sub questions for this question block
					List<SingleAnswer> saL = scoreTaskM.getQuesDiv(mark.getQuesid());
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
				String ansId = scoreTaskM.getStuanswerId(pid, ba.getQuesid());
				for (SingleAnswer sa:saL) {
					num = scoreTaskM.insertStuAnswerDiv(sa, ansId);
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
			num = scoreTaskM.updateStuAnswer(ba);
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
		List<String> quesIds = scoreTaskM.getQuesIdsByExamGradeSub(egs);
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
		MarkTask mt = scoreTaskM.getAllMarkTasksByQuesId(questionId);
		mt.setMetaInfo(questionId, egs);
		if (mt.getMarkMode().compareTo("单评") == 0) {
			mt.setTeachersIds(scoreTaskM.getTeachersByQid(questionId));
			mt.setFinalMarkTeachersIds(new ArrayList<String>());
		}
		else if (mt.getMarkMode().compareTo("双评") == 0) {
			// get teachers for first screen
			mt.setTeachersIds(scoreTaskM.getTeachersByQidRole(questionId, "初评"));
			// get teachers for final screen
			mt.setFinalMarkTeachersIds(scoreTaskM.getTeachersByQidRole(questionId, "终评"));
		}
		else {
			logger.warn("getMarkTasksBySub(), wrong type -> " + mt.getMarkMode());
		}
		return mt;
	}
}
