package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.Exam;

public interface ExamService {
	
	List<Exam> getAllExams();
	
	List<Exam> getExamsByStatus(String status);
	
	List<Exam> getExamsById(Long id);
	
}
