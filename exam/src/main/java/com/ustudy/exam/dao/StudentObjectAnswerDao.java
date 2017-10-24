package com.ustudy.exam.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.StudentObjectAnswer;

@MapperScan
public interface StudentObjectAnswerDao {

	void insertStudentObjectAnswer(StudentObjectAnswer answer);
	
}
