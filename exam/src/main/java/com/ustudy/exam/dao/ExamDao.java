package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Exam;

@MapperScan
public interface ExamDao {

	public List<Exam> getExams(String type);
	
}
