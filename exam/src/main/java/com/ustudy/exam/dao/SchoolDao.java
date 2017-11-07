package com.ustudy.exam.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.School;

@MapperScan
public interface SchoolDao {

	School getSchoolById(String schid);
	
}
