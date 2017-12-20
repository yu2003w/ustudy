package com.ustudy.exam.service;

import net.sf.json.JSONArray;

public interface ScoreService {

	boolean recalculateQuestionScore(Long egsId, Integer quesno, String answer) throws Exception;
	
	boolean recalculateQuestionScore(Long egsId) throws Exception;
	
	boolean publishExamScore(Long examId, Boolean release) throws Exception;
	
	JSONArray getStudentSubjects(Long examId, Long schId, Long gradeId, Long classId, Long subjectId, String branch, String text) throws Exception;
	
}
