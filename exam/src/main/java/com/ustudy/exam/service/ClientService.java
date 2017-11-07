package com.ustudy.exam.service;

import java.util.List;
import java.util.Map;

import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamGrade;
import com.ustudy.exam.model.ExamSubject;

import net.sf.json.JSONObject;

public interface ClientService {
	
	Map<String, Object> login(String token);

	boolean saveTemplates(String csId, JSONObject data);
	
	Map<String, String> getTemplateById(String examId, String gradeId, String subjectId);
	
	Map<String, String> getTemplateById(String csId);
	
	List<ExamSubject> getExamSubjects(String examId, String gradeId);
	
	List<ExamGrade> getExamGrades(String examId, String examStatus);
	
	Map<String, List<Exam>> getExams(String examStatus);
	
}
