package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.StudentObjectAnswer;

@MapperScan
public interface StudentObjectAnswerDao {

	void insertStudentObjectAnswer(StudentObjectAnswer answer);
	
	void insertStudentObjectAnswers(List<StudentObjectAnswer> answers);
	
	void deleteStudentObjectAnswers(@Param("egsId")Long egsId, @Param("batchNum")Integer batchNum);
	
}
