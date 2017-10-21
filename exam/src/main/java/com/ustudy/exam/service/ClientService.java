package com.ustudy.exam.service;

import java.util.List;
import java.util.Map;

import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamGrade;
import com.ustudy.exam.model.ExamSubject;

import net.sf.json.JSONObject;

public interface ClientService {
	
	public Map<String, Object> login(String token);

	public boolean saveTemplates(JSONObject data);
	
	public Map<String, String> getTemplateById(String examId, String gradeId, String subjectId);
	
	public List<ExamSubject> getExamSubjects(String examId, String gradeId);
	
	public List<ExamGrade> getExamGrades(String examId, String examStatus);
	
	public Map<String, List<Exam>> getExams(String examStatus);
	
}
