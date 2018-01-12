package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.statics.ScoreClass;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface ScoreService {

	boolean recalculateQuestionScore(Long egsId, Integer quesno, String answer) throws Exception;
	
	boolean recalculateQuestionScore(Long egsId) throws Exception;
	
	boolean publishExamScore(Long examId, Boolean release) throws Exception;
	
	JSONArray getStudentSubjects(Long examId, Long schId, Long gradeId, Long classId, Long subjectId, String branch, String text) throws Exception;
	
	JSONObject getStudentScores(Long stuId, Long examId) throws Exception;
	
	/**
	 * @param eid
	 * @param gid
	 * @return score of classes specified by exam id and grade id
	 */
	public List<ScoreClass> getClsScores(int eid, int gid);
	
}
