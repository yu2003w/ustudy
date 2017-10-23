package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.Teacher;

public interface TeacherService {

	Teacher findUserById(String id);
	
	List<String> getRolesById(String id);
	
	public String findPriRoleById(String id);
	
}
