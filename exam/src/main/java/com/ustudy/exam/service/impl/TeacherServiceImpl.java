package com.ustudy.exam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.TeacherMapper;
import com.ustudy.exam.model.Teacher;
import com.ustudy.exam.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherMapper userM;

	@Override
	public Teacher findUserById(String id) {
		
		Teacher u = this.userM.findUserById(id);
		return u;
	}

	@Override
	public List<String> getRolesById(String id) {
		List<String> r = userM.getRolesById(id);
		return r;
	}

}
