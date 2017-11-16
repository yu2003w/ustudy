package com.ustudy.exam.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Exam;

@MapperScan
public interface ExamDao {

	List<Exam> getAllExams();
	
	List<Exam> getExamsByStatus(String status);
	
	Exam getExamsById(Long id);

	ArrayList<Map> getGrades();

	ArrayList<Map> getSubjects();

}
