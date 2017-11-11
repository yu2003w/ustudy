package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.School;

@MapperScan
public interface SchoolDao {

	School getSchoolById(String schid);
	
	List<School> getSchoolsByExamId(Long examId);
	
}
