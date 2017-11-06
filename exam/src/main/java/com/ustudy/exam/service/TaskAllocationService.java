package com.ustudy.exam.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface TaskAllocationService {

	JSONArray getQuestions(Long egsId) throws Exception;
	
	JSONArray getQuestions(Long examId, Long gradeId, Long subjectId) throws Exception;
	
	JSONObject getSchools(Long schoolId) throws Exception;
	
	JSONObject getGrades(Long gradeId) throws Exception;
	
}
