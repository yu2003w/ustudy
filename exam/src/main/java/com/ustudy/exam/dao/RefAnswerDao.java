package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.RefAnswer;

@MapperScan
public interface RefAnswerDao {

	List<RefAnswer> getRefAnswers(Long egsId);
	
	boolean deleteRefAnswers(Long egsId);
	
	boolean insertRefAnswers(List<RefAnswer> refAnswers);
	
	boolean updateRefAnswer(RefAnswer refAnswer);
	
}
