package com.ustudy.exam.service;

import java.util.Map;

import net.sf.json.JSONArray;

public interface ExamStudentService {

	JSONArray getStudentInfoByExamGrade(Long examId, Long gradeId);
	
	Map<String, Object> getExamStudents(Long examId, Long classId, String studentName);
	
	Map<String, Object> getMissExamStudents(Long egsId, Long classId, String studentName);
	
}
