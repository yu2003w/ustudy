package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Subscore;

@MapperScan
public interface SubscoreDao {

	void deleteSubscores(Long egsId);
	
	void insertSubscores(List<Subscore> subscores);
	
	List<Map<String, Object>> getExamScores(Long examId);
	
}
