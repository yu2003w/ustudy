package com.ustudy.exam.service;

import java.util.List;
import java.util.Map;

import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamGrade;
import com.ustudy.exam.model.ExamSubject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface ClientService {
	
	Map<String, Object> login(String token);

	boolean saveTemplates(Long csId, JSONObject data);
	
	Map<String, String> getTemplateById(Long examId, Long gradeId, Long subjectId);
	
	Map<String, String> getTemplateById(Long csId);
	
	List<ExamSubject> getExamSubjects(Long examId, Long gradeId);
	
	JSONArray getExamSubjectStatus(Long examId, String templateStatus, Integer gradeCode, String markingStatus);
	
	List<ExamGrade> getExamGrades(Long examId, String examStatus);
	
	List<Exam> getExams(String examStatus);
	
}
