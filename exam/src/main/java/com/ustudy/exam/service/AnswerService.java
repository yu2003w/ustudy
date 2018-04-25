package com.ustudy.exam.service;

import com.ustudy.exam.model.AnswerSet;

import net.sf.json.JSONObject;

public interface AnswerService {

	AnswerSet getQuesAnswer(Long egsId) throws Exception;
	
	boolean deleteQuesAnswers(Long egsId) throws Exception;
	
	boolean saveQuesAnswers(Long egsId, JSONObject ques) throws Exception;
	
}
