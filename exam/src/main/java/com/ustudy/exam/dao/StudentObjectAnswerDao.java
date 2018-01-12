package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.StudentObjectAnswer;

@MapperScan
public interface StudentObjectAnswerDao {

	void insertStudentObjectAnswer(StudentObjectAnswer answer);
	
	void updateStudentObjectAnswer(@Param("id")Long id, @Param("score")Integer score);
	
	void insertStudentObjectAnswers(List<StudentObjectAnswer> answers);
	
	void deleteStudentObjectAnswers(@Param("egsId")Long egsId, @Param("batchNum")Integer batchNum);
	
	List<StudentObjectAnswer> getQuestionAnswer(@Param("egsId")Long egsId, @Param("quesno")Integer quesno);
	
	List<StudentObjectAnswer> getQuestionsAnswer(@Param("egsId")Long egsId);
	
	void deleteOneStudentObjectAnswers(@Param("egsId")Long egsId, @Param("examCode")String examCode);
	
}
