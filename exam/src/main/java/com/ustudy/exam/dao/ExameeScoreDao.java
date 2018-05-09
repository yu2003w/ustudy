package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ustudy.exam.model.ExameeScore;
import com.ustudy.exam.model.score.StudentScore;

@Mapper
public interface ExameeScoreDao {

	//int deleteExameeScores(Long examId);
	
	int insertExameeScores(List<ExameeScore> exameeScores);
	
	List<StudentScore> getExameeScores(@Param("examId")Long examId, @Param("schId")Long schId, @Param("gradeId")Long gradeId, 
	        @Param("classId")Long classId, @Param("branch")String branch, @Param("text")String text);
	
	List<Map<String, Object>> getStudentScores(@Param("examId")Long examId, @Param("schId")Long schId, @Param("gradeId")Long gradeId, 
	        @Param("classId")Long classId, @Param("subjectId")Long subjectId, @Param("branch")String branch, @Param("text")String text);
	
}
