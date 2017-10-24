package com.ustudy.exam.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.StudentPaper;

@MapperScan
public interface StudentPaperDao {

	void insertStudentPaper(StudentPaper paper);
	
}
