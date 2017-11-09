package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface TaskAllocationDao {

	Map getTeacherBySchoolIdType(@Param("schoolId")String schoolId, @Param("type")String type);
	
	List<Map> getTeachersBySchoolId(String schoolId);
	
}