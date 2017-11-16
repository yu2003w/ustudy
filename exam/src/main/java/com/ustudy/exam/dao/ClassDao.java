package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

@MapperScan
@SuppressWarnings("rawtypes")
public interface ClassDao {

	List<com.ustudy.exam.model.Class> getClassBySchoolId(String schoolId);
	
	List<com.ustudy.exam.model.Class> getClassByGradeId(Long gradeId);
	
	List<Map> getClassubBySchoolId(String schoolId);
	
	List<Map> getClassubByGradeId(Long gradeId);
	
}	
