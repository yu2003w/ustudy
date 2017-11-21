package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.QuesAnswerDiv;

@MapperScan
public interface QuesAnswerDivDao {

	List<QuesAnswerDiv> getAllQuesAnswerDivs(Long egsId);
	
	List<QuesAnswerDiv> getQuesAnswerDivs(Long quesId);
	
	boolean deleteQuesAnswerDivs(Long egsId);
	
	boolean insertQuesAnswerDivs(List<QuesAnswerDiv> quesAnswerDivs);
	
	boolean updateQuesAnswerDiv(QuesAnswerDiv quesAnswerDiv);
	
}
