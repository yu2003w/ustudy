package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.ExamSubject;

@MapperScan
public interface ExamSubjectDao {

	List<ExamSubject> getAllExamSubject();
	
	List<ExamSubject> getAllExamSubjectByExamId(String examId);
	
	List<ExamSubject> getAllExamSubjectByExamIdAndGradeId(@Param("examId") String examId, @Param("gradeId") String gradeId);
	
	List<ExamSubject> getExamSubjectByExamIdAndGradeIdAndSubjectId(@Param("examId") String examId, @Param("gradeId") String gradeId, @Param("subjectId") String subjectId);
	
	List<ExamSubject> getExamSubjectById(String id);
	
	void saveBlankAnswerPaper(@Param("id")String id, @Param("fileName") String fileName);
	
	void saveBlankQuestionsPaper(@Param("id")String id, @Param("fileName") String fileName);

	void saveOriginalData(@Param("id")String id, @Param("originalData") String originalData);
	
}
