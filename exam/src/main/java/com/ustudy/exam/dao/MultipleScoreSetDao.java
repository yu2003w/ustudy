package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.MultipleScoreSet;

@MapperScan
public interface MultipleScoreSetDao {

	List<MultipleScoreSet> getAllMultipleScoreSets(Long egsId);
	
	boolean deleteMultipleScoreSets(Long egsId);
	
	boolean insertMultipleScoreSets(List<MultipleScoreSet> multipleScoreSet);
	
}
