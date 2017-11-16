package com.ustudy.exam.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamGrade;

import net.sf.json.JSONArray;

public interface ClientService {
	
	Map<String, Object> login(String token);

	boolean saveTemplates(Long csId, String data);
	
	Map<String, String> getTemplateById(Long examId, Long gradeId, Long subjectId);
	
	Map<String, String> getTemplateById(Long csId);
	
	JSONArray getExamSubjects(Long examId, Long gradeId);
	
	JSONArray getExamSubjectStatus(Long examId, String templateStatus, Long gradeId, String markingStatus);
	
	List<ExamGrade> getExamGrades(Long examId, String examStatus);
	
	List<Exam> getExams(String examStatus);
	
	boolean addLog(HttpServletRequest request, String logs) throws Exception;
	
	List<String> getLog(HttpServletRequest request) throws Exception;
	
	boolean deleteLog(HttpServletRequest request) throws Exception;
	
	JSONArray getQuestionType();
	
}
