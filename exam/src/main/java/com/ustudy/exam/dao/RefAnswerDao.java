package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.RefAnswer;

@MapperScan
public interface RefAnswerDao {

	List<RefAnswer> getRefAnswers(Long egsId);
	
	RefAnswer getRefAnswer(@Param("egsId")Long egsId, @Param("quesno")Integer quesno);
	
	boolean deleteRefAnswers(Long egsId);
	
	boolean insertRefAnswers(List<RefAnswer> refAnswers);
	
	boolean updateRefAnswer(RefAnswer refAnswer);
	
}
