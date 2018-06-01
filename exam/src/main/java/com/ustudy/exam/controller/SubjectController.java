package com.ustudy.exam.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.exam.service.SubjectService;

@RestController
@RequestMapping(value = "/")
public class SubjectController {
	
	private static final Logger logger = LogManager.getLogger(SubjectController.class);
	
	@Autowired
	private SubjectService service;
	
	/**
	 * 获取科目信息
	 * @param request HttpServletRequest 
	 * @param response HttpServletResponse
	 * @return
	 */
	@RequestMapping(value = "/subjects", method = RequestMethod.GET)
	public Map getSubjects(HttpServletRequest request, HttpServletResponse response) {
		
		logger.debug("getSubjects().");
		
		Map result = new HashMap<>();

		/*result.put("success", true);
		result.put("data", service.getAllSubject());*/

		return result;
	}
	
}
