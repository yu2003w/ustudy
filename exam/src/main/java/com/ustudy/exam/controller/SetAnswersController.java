package com.ustudy.exam.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/setanswers")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SetAnswersController {
	
	private static final Logger logger = LogManager.getLogger(SetAnswersController.class);
	
	/**
	 * 根据考试ID获取考试科目信息
	 * @param examId 考试ID
	 * @param request HttpServletRequest 
	 * @param response HttpServletResponse
	 * @return
	 */
	@RequestMapping(value = "/getExamSubjects/{examId}", method = RequestMethod.POST)
	public Map getExamSubjects(@PathVariable String examId, HttpServletRequest request, HttpServletResponse response) {
		
		logger.debug("getSubjects().");
		logger.debug("examId: " + examId);
		
		Map result = new HashMap<>();

		result.put("success", true);

		return result;
	}
	
}
