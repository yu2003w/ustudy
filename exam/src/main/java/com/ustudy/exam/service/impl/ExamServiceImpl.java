package com.ustudy.exam.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ExamDao;
import com.ustudy.exam.model.Exam;
import com.ustudy.exam.service.ExamService;

@Service
public class ExamServiceImpl implements ExamService {
	
	private static final Logger logger = LogManager.getLogger(ExamServiceImpl.class);
	
	@Resource
	private ExamDao examDaoImpl;
	
	public List<Exam> getAllExams(){
		return examDaoImpl.getAllExams();
	}
	
	public List<Exam> getExamsByStatus(String status){
		logger.debug("getExamsByStatus -> status:" + status);
		return examDaoImpl.getExamsByStatus(status);
	}
	
	public Exam getExamsById(Long id){
		logger.debug("getExamsById -> id:" + id);
		return examDaoImpl.getExamsById(id);
	}
	
}
