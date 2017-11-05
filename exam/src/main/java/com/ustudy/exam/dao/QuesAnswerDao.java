package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.QuesAnswer;

@MapperScan
public interface QuesAnswerDao {

	List<QuesAnswer> getQuesAnswers(int egsId);
	
	boolean deleteQuesAnswers(int egsId);
	
	boolean deleteQuesAnswerByIds(@Param("egsId")int egsId, @Param("quesAnswerIds")List<Integer> quesAnswerIds);
	
	boolean insertQuesAnswers(List<QuesAnswer> quesAnswers);
	
	boolean updateQuesAnswer(QuesAnswer quesAnswer);
	
}
