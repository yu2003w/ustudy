package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.ExamSubject;

@MapperScan
public interface ExamSubjectDao {

	List<ExamSubject> getAllExamSubject();
	
	List<ExamSubject> getAllExamSubjectByExamId(Long examId);
	
	List<ExamSubject> getAllExamSubjectByExamIdAndGradeId(@Param("examId") Long examId, @Param("gradeId") Long gradeId);
	
	List<ExamSubject> getExamSubjectByExamIdAndGradeIdAndSubjectId(@Param("examId") Long examId, @Param("gradeId") Long gradeId, @Param("subjectId") Long subjectId);
	
	ExamSubject getExamSubjectById(Long id);
	
	void saveBlankAnswerPaper(@Param("id")Long id, @Param("fileName") String fileName);
	
	void saveBlankQuestionsPaper(@Param("id")Long id, @Param("fileName") String fileName);

	void saveOriginalData(@Param("id")Long id, @Param("xmlServerPath")String xmlServerPath, @Param("originalData") String originalData);
	
}
