package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.QuesAnswerDiv;

@MapperScan
public interface QuesAnswerDivDao {

	List<QuesAnswerDiv> getAllQuesAnswerDivs(int egsId);
	
	List<QuesAnswerDiv> getQuesAnswerDivs(int quesId);
	
	boolean deleteQuesAnswerDivs(int egsId);
	
	boolean insertQuesAnswerDivs(List<QuesAnswerDiv> quesAnswerDivs);
	
	boolean updateQuesAnswerDiv(QuesAnswerDiv quesAnswerDiv);
	
}
