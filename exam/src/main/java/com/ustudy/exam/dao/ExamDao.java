package com.ustudy.exam.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Exam;

@MapperScan
public interface ExamDao {

	List<Exam> getAllExams();
	
	List<Exam> getExams(@Param("finished")Boolean finished, @Param("gradeId")Long gradeId, @Param("subjectId")Long subjectId, @Param("starDate")String starDate, @Param("endDate")String endDate, @Param("name")String name);
	
	List<Map<String, Object>> getExamGrades(Long examid);
	
	List<Map<String, Object>> getExamSubjects(Long examid);
	
	List<Exam> getExamsByStatus(String status);
	
	Exam getExamsById(Long id);
	
	Exam getLastExam();

	ArrayList<Map> getGrades();

	ArrayList<Map> getSubjects();

}
