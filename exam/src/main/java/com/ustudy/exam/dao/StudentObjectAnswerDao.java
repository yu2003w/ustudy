package com.ustudy.exam.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.StudentObjectAnswer;

@MapperScan
public interface StudentObjectAnswerDao {

	void insertStudentObjectAnswer(StudentObjectAnswer answer);
	
	void deleteStudentObjectAnswers(@Param("csId")Integer csId, @Param("batchNum")Integer batchNum);
	
}
