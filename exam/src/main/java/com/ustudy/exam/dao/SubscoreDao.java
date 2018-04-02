package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ustudy.exam.model.score.ExameeSubScore;
import com.ustudy.exam.model.score.SubScore;

@Mapper
public interface SubscoreDao {

	void deleteSubscores(Long egsId);
	
	void insertSubscores(List<SubScore> subscores);
	
	List<Map<String, Object>> getExamScores(Long examId);
	
	ExameeSubScore getExameeSubScores(@Param("exameeId")Long exameeId, @Param("examId")Long examId);
	
	List<Map<String, Object>> getStudentObjScores(@Param("stuId")Long stuId, @Param("examId")Long examId, @Param("subjectId")Long subjectId);
	
	List<Map<String, Object>> getStudentSubScores(@Param("stuId")Long stuId, @Param("examId")Long examId, @Param("subjectId")Long subjectId);
	
	List<Map<String, Object>> getStudentStepScores(@Param("stuId")Long stuId, @Param("examId")Long examId, @Param("subjectId")Long subjectId);
	
}
