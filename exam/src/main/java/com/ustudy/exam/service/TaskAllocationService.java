package com.ustudy.exam.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface TaskAllocationService {

	JSONArray getQuestions(Long egsId) throws Exception;
	
	JSONArray getQuestions(Long examId, Long gradeId, Long subjectId) throws Exception;
	
	JSONObject getSchool(String schoolId) throws Exception;
	
	JSONObject getGrade(Long gradeId) throws Exception;
	
	List<Map<String, Object>> getGradeNotaskTeachers(Long gradeId) throws Exception;
	
	JSONArray getSchoolTeachers(String schId) throws Exception;
	
}
