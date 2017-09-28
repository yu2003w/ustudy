package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.Teacher;

public interface TeacherService {

	public Teacher findUserById(String id);
	
	public List<String> getRolesById(String id);
	
}
