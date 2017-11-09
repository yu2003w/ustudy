package com.ustudy.exam.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface TaskAllocationService {

	JSONArray getQuestions(Long egsId) throws Exception;
	
	JSONArray getQuestions(Long examId, Long gradeId, Long subjectId) throws Exception;
	
	JSONObject getSchool(String schoolId) throws Exception;
	
	JSONObject getGrade(Long gradeId) throws Exception;
	
}
