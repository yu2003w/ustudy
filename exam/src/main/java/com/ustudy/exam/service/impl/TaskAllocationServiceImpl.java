package com.ustudy.exam.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ExamSubjectDao;
import com.ustudy.exam.dao.MultipleScoreSetDao;
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.QuesAnswerDivDao;
import com.ustudy.exam.dao.RefAnswerDao;
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.model.QuesAnswer;
import com.ustudy.exam.service.TaskAllocationService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class TaskAllocationServiceImpl implements TaskAllocationService {

	private static final Logger logger = LogManager.getLogger(TaskAllocationServiceImpl.class);

	@Resource
	private QuesAnswerDao quesAnswerDaoImpl;

	@Resource
	private QuesAnswerDivDao quesAnswerDivDaoImpl;

	@Resource
	private RefAnswerDao refAnswerDaoImpl;

	@Resource
	private ExamSubjectDao examSubjectDaoImpl;
	
	@Resource
	private MultipleScoreSetDao multipleScoreSetDaoImpl;

	public JSONArray getQuestions(Long egsId) throws Exception {
		logger.info("getQuestions -> egsId=" + egsId);
		JSONArray array = new JSONArray();
		List<QuesAnswer> quesAnswers = quesAnswerDaoImpl.getQuesAnswers(egsId);
		for (QuesAnswer quesAnswer : quesAnswers) {
			JSONObject object = new JSONObject();
			object.put("id", quesAnswer.getId());
			if(quesAnswer.getStartno() == quesAnswer.getEndno()){
				object.put("questionName", "" + quesAnswer.getStartno());
			}else {
				object.put("questionName", quesAnswer.getStartno() + "-" + quesAnswer.getEndno());
			}
			object.put("type", quesAnswer.getType());
			
			array.add(object);
		}
		return array;
	}
	
	public JSONArray getQuestions(Long examId, Long gradeId, Long subjectId) throws Exception {
		logger.info("getQuestions -> examId=" + examId + ",gradeId=" + gradeId + ",subjectId=" + subjectId);
		JSONArray array = new JSONArray();
		List<ExamSubject> examSubjects = examSubjectDaoImpl.getExamSubjectByExamIdAndGradeIdAndSubjectId(examId, gradeId, subjectId);
		if(null != examSubjects && examSubjects.size()>0){
			Long egsId = examSubjects.get(0).getId();
			array = getQuestions(egsId);
		}
		
		return array;
	}

	public JSONObject getSchools(Long schoolId) throws Exception {
		logger.info("getSchools -> schoolId=" + schoolId);
		return null;
	}

	public JSONObject getGrades(Long gradeId) throws Exception {
		logger.info("getGrades -> gradeId=" + gradeId);
		return null;
	}

}
