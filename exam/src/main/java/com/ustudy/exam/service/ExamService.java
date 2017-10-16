package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.Exam;

public interface ExamService {
	
	public List<Exam> getAllExams();
	
	public List<Exam> getExamsByStatus(String status);
	
	public List<Exam> getExamsById(String id);
	
}
