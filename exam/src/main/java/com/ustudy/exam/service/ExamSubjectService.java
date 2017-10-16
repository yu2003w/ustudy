package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.ExamSubject;

public interface ExamSubjectService {
	
	public List<ExamSubject> getAllExamSubject();
	
	public List<ExamSubject> getAllExamSubjectByExam(String examId);
	
	public List<ExamSubject> getAllExamSubjectByExamAndGrade(String examId, String gradeId);
	
	public List<ExamSubject> getExamSubjectByExamAndGradeAndSubject(String examId, String gradeId, String subjectId);
	
	public List<ExamSubject> getExamSubjectById(String id);
	
}
