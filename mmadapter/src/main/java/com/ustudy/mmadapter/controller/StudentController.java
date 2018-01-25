package com.ustudy.mmadapter.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.mmadapter.model.Student;

@RestController
@RequestMapping(value = "/adapter/")
public class StudentController {

	private static final Logger logger = LogManager.getLogger(StudentController.class);
	
	@RequestMapping(value = "/account/validate/", method = RequestMethod.GET)
	public boolean validateStudent(@RequestBody @Valid Student stu, HttpServletResponse resp) {
		
		logger.info("validateStudent(), validate information->" + stu.toString());
		if ((stu.getExamNo() == null || stu.getExamNo().isEmpty()) &&
				(stu.getStuNo() == null || stu.getStuNo().isEmpty())) {
			logger.warn("validateStudent(), neither student exam no nor student no is valid");
			return false;
		}
		
		return true;
	}
	
}
