package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface MarkProgressDao {

	List<Map<String, Object>> getEgsTeachers(@Param("orgId")String orgId, @Param("egsId")int egsId);
	
	List<Map<String, Object>> getEgsTeacherMarkProgress(@Param("orgId")String orgId, @Param("egsId")int egsId);
	
	long getEgsStudentsCount(@Param("orgId")String orgId, @Param("egsId")int egsId);
	
	List<Map<String, Object>> getEgsTeacherFinalMarks(@Param("orgId")String orgId, @Param("egsId")int egsId);
	
}
