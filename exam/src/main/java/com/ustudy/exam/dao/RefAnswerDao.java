package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ustudy.exam.model.RefAnswer;
import com.ustudy.exam.model.score.ScoreRule;

@Mapper
public interface RefAnswerDao {

	List<RefAnswer> getRefAnswers(Long egsId);
	
	/**
	 * Retrieving information of score rules for all object questions in egs
	 * @param egsId
	 * @return
	 */
	List<ScoreRule> getEgsScoreRules(long egsId);
	
	RefAnswer getRefAnswer(@Param("egsId")Long egsId, @Param("quesno")Integer quesno);
	
	boolean deleteRefAnswers(Long egsId);
	
	boolean insertRefAnswers(List<RefAnswer> refAnswers);
	
	boolean updateRefAnswer(RefAnswer refAnswer);
	
}
