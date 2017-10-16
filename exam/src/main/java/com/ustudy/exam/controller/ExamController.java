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

import com.ustudy.exam.service.ExamService;

@RestController
@RequestMapping(value = "/exam")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExamController {
	
	private static final Logger logger = LogManager.getLogger(ExamController.class);
	
	@Autowired
	private ExamService service;
	
	/**
	 * 获取考试信息
	 * @param request HttpServletRequest 
	 * @param response HttpServletResponse
	 * @return Map
	 */
	@RequestMapping(value = "/getAllExams", method = RequestMethod.GET)
	public Map getAllExams(HttpServletRequest request, HttpServletResponse response) {
		
		logger.debug("getExams().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getAllExams());

		return result;
	}
	
	/**
	 * 根据考试状态获取考试信息
	 * @param examStatus 考试状态
	 * @param request HttpServletRequest 
	 * @param response HttpServletResponse
	 * @return Map
	 */
	@RequestMapping(value = "/getExams/{examStatus}", method = RequestMethod.GET)
	public Map getExams(@PathVariable String examStatus, HttpServletRequest request, HttpServletResponse response) {
		
		logger.debug("getExams().");
		logger.debug("examStatus: " + examStatus);
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getExamsByStatus(examStatus));

		return result;
	}
	
	/**
	 * 根据考试状态获取考试信息
	 * @param examStatus 考试状态
	 * @param request HttpServletRequest 
	 * @param response HttpServletResponse
	 * @return Map
	 */
	@RequestMapping(value = "/getExam/{examId}", method = RequestMethod.GET)
	public Map getExam(@PathVariable String examId, HttpServletRequest request, HttpServletResponse response) {
		
		logger.debug("getExams().");
		logger.debug("examId: " + examId);
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getExamsById(examId));

		return result;
	}
	
}
