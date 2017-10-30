package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.RefAnswer;

@MapperScan
public interface RefAnswerDao {

	List<RefAnswer> getRefAnswers(int egsId);
	
	boolean deleteRefAnswers(int egsId);
	
	boolean insertRefAnswers(List<RefAnswer> refAnswers);
	
}
