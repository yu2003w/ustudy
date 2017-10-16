package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Exam;

@MapperScan
public interface ExamDao {

	public List<Exam> getAllExams();
	
	public List<Exam> getExamsByStatus(String status);
	
	public List<Exam> getExamsById(String id);
	
}
