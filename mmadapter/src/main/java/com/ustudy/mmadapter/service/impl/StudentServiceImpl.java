package com.ustudy.mmadapter.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.mmadapter.model.Student;
import com.ustudy.mmadapter.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	
	private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

	@Override
	public boolean validateStudent(Student s) {

		logger.debug("validateStudent(),");
		return false;
	}

}
