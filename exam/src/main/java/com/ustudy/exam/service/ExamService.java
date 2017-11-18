package com.ustudy.exam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ustudy.exam.model.Exam;

public interface ExamService {
	
	List<Exam> getAllExams();
	
	List<Exam> getExamsByStatus(String status);
	
	Exam getExamsById(Long id);

	ArrayList<Map> getGrades();

	ArrayList<Map> getSubjects();
}
