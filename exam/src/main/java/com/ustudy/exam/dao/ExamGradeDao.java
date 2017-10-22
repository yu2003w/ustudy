package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.ExamGrade;

@MapperScan
public interface ExamGradeDao {

	List<ExamGrade> getExamGrades(@Param("examId") String examId, @Param("status") String status);
	
}
