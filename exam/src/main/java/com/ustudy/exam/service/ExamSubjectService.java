package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.ExamSubject;

import net.sf.json.JSONObject;

public interface ExamSubjectService {
	
	List<ExamSubject> getAllExamSubject();
	
	List<ExamSubject> getAllExamSubjectByExam(String examId);
	
	List<ExamSubject> getAllExamSubjectByExamAndGrade(String examId, String gradeId);
	
	List<ExamSubject> getExamSubjectByExamAndGradeAndSubject(String examId, String gradeId, String subjectId);
	
	List<ExamSubject> getExamSubjectById(String id);
	
	boolean saveBlankAnswerPaper(String id, String fileName);
	
	boolean saveBlankQuestionsPaper(String id, String fileName);

	boolean saveStudentAnswers(JSONObject data);
}
