package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Exam;

@MapperScan
public interface ExamDao {

	List<Exam> getAllExams();
	
	List<Exam> getExamsByStatus(String status);
	
	Exam getExamsById(Long id);
	
}
