package com.ustudy.exam.service;

import net.sf.json.JSONObject;

public interface StudentAnswerService {
	
	boolean saveStudentsAnswers(String egId, String csId, JSONObject data);
	
}
