package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.ExameeScore;

@MapperScan
public interface ExameeScoreDao {

	void deleteExameeScores(Long examId);
	
	void insertExameeScores(List<ExameeScore> exameeScores);
	
	List<Map<String, Object>> getExameeScores(@Param("examId")Long examId, @Param("schId")Long schId, @Param("gradeId")Long gradeId, 
	        @Param("classId")Long classId, @Param("branch")String branch, @Param("text")String text);
	
	List<Map<String, Object>> getStudentScores(@Param("examId")Long examId, @Param("schId")Long schId, @Param("gradeId")Long gradeId, 
	        @Param("classId")Long classId, @Param("subjectId")Long subjectId, @Param("branch")String branch, @Param("text")String text);
	
}
