package com.ustudy.exam.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ExamSubjectDao;
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.service.ExamSubjectService;

import net.sf.json.JSONObject;

@Service
public class ExamSubjectServiceImpl implements ExamSubjectService {
	
	private static final Logger logger = LogManager.getLogger(ExamSubjectServiceImpl.class);
	
	@Resource
	private ExamSubjectDao daoImpl;
	
	public List<ExamSubject> getAllExamSubject(){
		return daoImpl.getAllExamSubject();
	}
	
	public List<ExamSubject> getAllExamSubjectByExam(String examId){
		logger.debug("getAllExamSubjectByExam -> examId:" + examId);
		return daoImpl.getAllExamSubjectByExamId(examId);
	}
	
	public List<ExamSubject> getAllExamSubjectByExamAndGrade(String examId, String gradeId) {
		logger.debug("getAllExamSubjectByExamAndGrade -> examId:" + examId + ",gradeId:" + gradeId);
		return daoImpl.getAllExamSubjectByExamIdAndGradeId(examId, gradeId);
	}
	
	public List<ExamSubject> getExamSubjectByExamAndGradeAndSubject(String examId, String gradeId, String subjectId) {
		logger.debug("getExamSubjectByExamAndGradeAndSubject -> examId:" + examId + ",gradeId:" + gradeId + ",subjectId:" + subjectId);
		return daoImpl.getExamSubjectByExamIdAndGradeIdAndSubjectId(examId, gradeId, subjectId);
	}
	
	public List<ExamSubject> getExamSubjectById(String id) {
		logger.debug("getExamSubjectById -> id:" + id);
		return daoImpl.getExamSubjectById(id);
	}

	public boolean saveBlankAnswerPaper(String id, String fileName) {
		logger.debug("saveBlankAnswerPaper -> id:" + id + ",fileName:" + fileName);
		try {
			daoImpl.saveBlankAnswerPaper(id, fileName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveBlankQuestionsPaper(String id, String fileName) {
		logger.debug("saveBlankQuestionsPaper -> id:" + id + ",fileName:" + fileName);
		try {
			daoImpl.saveBlankQuestionsPaper(id, fileName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveStudentAnswers(JSONObject data) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
