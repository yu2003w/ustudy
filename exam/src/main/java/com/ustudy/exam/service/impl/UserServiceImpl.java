package com.ustudy.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.UserMapper;
import com.ustudy.exam.model.User;
import com.ustudy.exam.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userM;
	
	/*
	public UserMapper getUserM() {
		return userM;
	}

	public void setUserM(UserMapper userM) {
		this.userM = userM;
	} */


	@Override
	public User findUserById(String id) {
		
		User u = this.userM.findUserById(id);
		return u;
	}

}
