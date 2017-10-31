package com.ustudy.exam.service;

import java.util.Map;

import net.sf.json.JSONObject;

public interface SetAnswersService {

	Map<String, Object> getQuesAnswer(int egsId) throws Exception;
	
	boolean deleteQuesAnswers(int egsId) throws Exception;
	
	boolean saveQuesAnswers(int egsId, JSONObject ques) throws Exception;
	
}
