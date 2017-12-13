package com.ustudy.exam.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public interface ExamStudentService {

	JSONArray getStudentInfoByExamGrade(Long examId, Long gradeId);
	
	List<Map<String, Object>> getExamStudents(Long examId);
	
}
