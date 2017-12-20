package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.ExameeScore;

@MapperScan
public interface ExameeScoreDao {

	void deleteExameeScores(Long examId);
	
	void insertExameeScores(List<ExameeScore> exameeScores);
	
}
