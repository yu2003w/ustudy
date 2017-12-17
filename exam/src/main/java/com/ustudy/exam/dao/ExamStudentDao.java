package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.ExamStudent;

@MapperScan
public interface ExamStudentDao {

	List<ExamStudent> getStudentInfoByExamIDAndGradeId(@Param("examId")Long examId, @Param("gradeId")Long gradeId);
	
	List<Map<String, Object>> getExamStudents(@Param("examId")Long examId, @Param("classId")Long classId, @Param("studentName")String studentName);
	
	List<Map<String, Object>> getMissExamStudents(@Param("egsId")Long egsId, @Param("classId")Long classId, @Param("studentName")String studentName);
	
}
