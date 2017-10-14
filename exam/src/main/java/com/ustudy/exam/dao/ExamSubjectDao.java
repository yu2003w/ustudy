package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.ExamSubject;

@MapperScan
public interface ExamSubjectDao {

	public List<ExamSubject> getExamSubjects(@Param("examId") String examId, @Param("gradeId") String gradeId);
	
}
