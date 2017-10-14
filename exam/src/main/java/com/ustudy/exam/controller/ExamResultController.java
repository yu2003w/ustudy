package com.ustudy.exam.controller;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.exam.model.ExamResult;
import com.ustudy.exam.service.ExamResultService;

@RestController
public class ExamResultController {

	private static final Logger logger = LogManager.getLogger(ExamResultController.class);
	
	@Autowired
	private ExamResultService erS; 
	
	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public List<ExamResult> getAllResult() {
		logger.debug("getAllResult(), start to retriing all examination result.");
		
		return erS.getAllResult();
	}
}
