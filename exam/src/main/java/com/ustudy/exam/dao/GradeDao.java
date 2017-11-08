package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Grade;

@MapperScan
public interface GradeDao {

	List<Grade> getGradesBySchoolId(String schoolId);
	
	List<Map> getGradesubBySchoolId(String schoolId);
	
}	
