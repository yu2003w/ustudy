package com.ustudy.exam.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Teacher;

@MapperScan
public interface ClientDao {

//	public List<ExamResult> saveTemplates();
	
	public Teacher getTeacher(int id);
	
}
