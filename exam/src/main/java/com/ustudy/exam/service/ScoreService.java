package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.score.ExameeSubScore;
import com.ustudy.exam.model.score.StudentScore;
import com.ustudy.exam.model.statics.ScoreClass;

public interface ScoreService {

	boolean recalculateQuestionScore(Long egsId, Integer quesno, String answer) throws Exception;
	
	boolean calObjScoreOfEgs(Long egsId) throws Exception;
	
	boolean publishExamScore(Long examId, Boolean release) throws Exception;
	
	List<StudentScore> getStudentScores(Long examId, Long schId, Long gradeId, Long classId, Long subjectId, String branch, String text) throws Exception;
	
	ExameeSubScore getDetailedExameeScore(Long exameeId, Long examId) throws Exception;
	
	/**
	 * @param eid
	 * @param gid
	 * @return score of classes specified by exam id and grade id
	 */
	public List<ScoreClass> getClsScores(int eid, int gid);
	
	public boolean isScoreCalculated(int egsid);
	
}
