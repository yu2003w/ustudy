package com.ustudy.exam.service;

import java.util.Map;

import net.sf.json.JSONObject;

public interface SetAnswersService {

	Map<String, Object> getQuesAnswer(Long egsId) throws Exception;
	
	boolean deleteQuesAnswers(Long egsId) throws Exception;
	
	boolean saveQuesAnswers(Long egsId, JSONObject ques) throws Exception;
	
}
