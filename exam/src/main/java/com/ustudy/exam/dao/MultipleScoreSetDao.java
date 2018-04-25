package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.ustudy.exam.model.MultipleScoreSet;

@Mapper
public interface MultipleScoreSetDao {

	List<MultipleScoreSet> getAllMultipleScoreSets(Long egsId);
	
	List<MultipleScoreSet> getAggrMulScoreSet(Long egsId);
	
	boolean deleteMultipleScoreSets(Long egsId);
	
	boolean insertMultipleScoreSets(List<MultipleScoreSet> multipleScoreSet);
	
}
