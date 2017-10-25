package com.ustudy.exam.service;

import net.sf.json.JSONObject;

public interface StudentAnswerService {
	
	boolean saveStudentsAnswers(int egId, int csId, JSONObject data);
	
}
