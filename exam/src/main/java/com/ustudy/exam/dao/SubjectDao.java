package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Subject;

@MapperScan
public interface SubjectDao {

	List<Subject> getAllSubject();
	
}
