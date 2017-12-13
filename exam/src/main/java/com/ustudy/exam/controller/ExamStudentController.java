package com.ustudy.exam.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.exam.service.ExamStudentService;

@RestController
@RequestMapping(value = "/")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExamStudentController {
	
	private static final Logger logger = LogManager.getLogger(ExamStudentController.class);
	
	@Autowired
	private ExamStudentService service;
	
	/**
	 * 获取科目信息
	 * @param request HttpServletRequest 
	 * @param response HttpServletResponse
	 * @return
	 */
	@RequestMapping(value = "/students/{examId}", method = RequestMethod.GET)
	public Map getExamStudents(@PathVariable Long examId, HttpServletRequest request, HttpServletResponse response) {
		
		logger.debug("getExamStudents().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getExamStudents(examId));

		return result;
	}
	
}
