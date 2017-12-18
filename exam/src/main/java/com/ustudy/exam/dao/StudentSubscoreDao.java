package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.StudentSubscore;

@MapperScan
public interface StudentSubscoreDao {

	void deleteStudentSubscores(Long egsId);
	
	void insertStudentSubscores(List<StudentSubscore> subscores);
	
}
