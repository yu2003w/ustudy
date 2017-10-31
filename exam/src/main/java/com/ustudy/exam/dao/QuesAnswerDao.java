package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.QuesAnswer;

@MapperScan
public interface QuesAnswerDao {

	List<QuesAnswer> getQuesAnswers(int egsId);
	
	boolean deleteQuesAnswers(int egsId);
	
	boolean insertQuesAnswers(List<QuesAnswer> quesAnswers);
	
}
