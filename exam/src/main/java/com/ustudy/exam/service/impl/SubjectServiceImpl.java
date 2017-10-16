package com.ustudy.exam.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.SubjectDao;
import com.ustudy.exam.model.Subject;
import com.ustudy.exam.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	private static final Logger logger = LogManager.getLogger(SubjectServiceImpl.class);
	
	@Resource
	private SubjectDao subjectDaoImpl;
	
	public List<Subject> getAllSubject(){
		logger.debug("getAllSubject");
		return subjectDaoImpl.getAllSubject();
	}
	
}
