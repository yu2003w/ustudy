package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.ExamStudent;

@MapperScan
public interface ExamStudentDao {

	List<ExamStudent> getStudentInfoByExamIDAndGradeId(@Param("examId")Long examId, @Param("gradeId")Long gradeId);
	
}
