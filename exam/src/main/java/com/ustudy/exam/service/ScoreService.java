package com.ustudy.exam.service;

public interface ScoreService {

	boolean recalculateQuestionScore(Long egsId, Integer quesno, String answer) throws Exception;
	
	boolean recalculateQuestionScore(Long egsId) throws Exception;
	
}
