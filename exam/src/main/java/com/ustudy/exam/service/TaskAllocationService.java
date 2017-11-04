package com.ustudy.exam.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface TaskAllocationService {

	JSONArray getQuestions(int egsId) throws Exception;
	
	JSONObject getSchools(int schoolId) throws Exception;
	
	JSONObject getGrades(int gradeId) throws Exception;
	
}
