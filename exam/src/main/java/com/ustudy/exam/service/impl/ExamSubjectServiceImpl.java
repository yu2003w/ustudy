package com.ustudy.exam.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ExamSubjectDao;
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.model.QuesAnswer;
import com.ustudy.exam.service.ExamSubjectService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ExamSubjectServiceImpl implements ExamSubjectService {
	
	private static final Logger logger = LogManager.getLogger(ExamSubjectServiceImpl.class);
	
	@Resource
	private ExamSubjectDao daoImpl;
	
	@Resource
	private QuesAnswerDao quesAnswerDaoImpl;
	
	public List<ExamSubject> getExamSubjects(Long subjectId, Long gradeId, String start, String end, String examName){
		logger.debug("getExamSubjects");
		
		List<ExamSubject> examSubjects = daoImpl.getAllExamSubject(subjectId, gradeId, start, end, examName);
		
		if(null != examSubjects && examSubjects.size()>0){
			Map<Long, JSONArray> quesAnswers = getQuesAnswers(examSubjects);
			for (ExamSubject examSubject : examSubjects) {
				examSubject.setQuestions(quesAnswers.get(examSubject.getId()));
			}
		}		
		
		return examSubjects;
	}
	
	private Map<Long, JSONArray> getQuesAnswers(List<ExamSubject> examSubjects){

		Map<Long, JSONArray> resault = new HashMap<>();
		
		StringBuffer ids = new StringBuffer();
		if(null != examSubjects && examSubjects.size()>0){
			for (ExamSubject examSubject : examSubjects) {
				if(ids.length() > 0){
					ids.append("," + examSubject.getId());
				}else {
					ids.append(examSubject.getId());
				}
			}
		}
		
		List<QuesAnswer> quesAnswers = quesAnswerDaoImpl.getQuesAnswersByIds(ids.toString());
		if(null != quesAnswers && quesAnswers.size() > 0){
			for (QuesAnswer quesAnswer : quesAnswers) {
				JSONArray array = resault.get(quesAnswer.getExamGradeSubId());
				if(null == array){
					array = new JSONArray();
				}
				JSONObject object = new JSONObject();
				object.put("id", quesAnswer.getId());
				int startno = quesAnswer.getStartno();
				int endno = quesAnswer.getEndno();
				if(startno <= 0 || endno <=0){
					int quesno = quesAnswer.getQuesno();
					object.put("quesno", String.valueOf(quesno));
				}else {
					object.put("quesno", startno + "-" + endno);
				}
				
				array.add(object);
				resault.put(quesAnswer.getExamGradeSubId(), array);
			}
		}
		
		return resault;
	}
	
	public List<ExamSubject> getExamSubjects(Long examId){
		logger.debug("getExamSubjects -> examId:" + examId);
		return daoImpl.getAllExamSubjectByExamId(examId);
	}
	
	public List<ExamSubject> getExamSubjects(Long examId, Long gradeId) {
		logger.debug("getExamSubjects -> examId:" + examId + ",gradeId:" + gradeId);
		return daoImpl.getAllExamSubjectByExamIdAndGradeId(examId, gradeId);
	}
	
	public List<ExamSubject> getExamSubjects(Long examId, Long gradeId, Long subjectId) {
		logger.debug("getExamSubjects -> examId:" + examId + ",gradeId:" + gradeId + ",subjectId:" + subjectId);
		return daoImpl.getExamSubjectByExamIdAndGradeIdAndSubjectId(examId, gradeId, subjectId);
	}
	
	public ExamSubject getExamSubject(Long id) {
		logger.debug("getExamSubject -> id:" + id);
		return daoImpl.getExamSubjectById(id);
	}

	public boolean saveBlankAnswerPaper(Long id, String fileName) {
		logger.debug("saveBlankAnswerPaper -> id:" + id + ",fileName:" + fileName);
		try {
			daoImpl.saveBlankAnswerPaper(id, fileName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveBlankQuestionsPaper(Long id, String fileName) {
		logger.debug("saveBlankQuestionsPaper -> id:" + id + ",fileName:" + fileName);
		try {
			daoImpl.saveBlankQuestionsPaper(id, fileName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isAanswerSeted(Long id){
		try {
			daoImpl.isAanswerSeted(id);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isTaskDispatch(Long id){
		try {
			daoImpl.isTaskDispatch(id);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
}
