package com.ustudy.exam.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Teacher;

@MapperScan
public interface ClientDao {

//	List<ExamResult> saveTemplates();
	
	Teacher getTeacher(int id);
	
}
