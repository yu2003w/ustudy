package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.ExamSubject;

@MapperScan
public interface ExamSubjectDao {

	public List<ExamSubject> getAllExamSubject();
	
	public List<ExamSubject> getAllExamSubjectByExamId(String examId);
	
	public List<ExamSubject> getAllExamSubjectByExamIdAndGradeId(@Param("examId") String examId, @Param("gradeId") String gradeId);
	
	public List<ExamSubject> getExamSubjectByExamIdAndGradeIdAndSubjectId(@Param("examId") String examId, @Param("gradeId") String gradeId, @Param("subjectId") String subjectId);
	
	public List<ExamSubject> getExamSubjectById(String id);
	
	public void saveBlankAnswerPaper(@Param("id")String id, @Param("fileName") String fileName);
	
	public void saveBlankQuestionsPaper(@Param("id")String id, @Param("fileName") String fileName);
	
}
