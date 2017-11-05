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

import com.ustudy.exam.service.SetAnswersService;

@RestController
@RequestMapping(value = "/task/allocation")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TaskAllocationController {

	private static final Logger logger = LogManager.getLogger(TaskAllocationController.class);

	@Autowired
	private SetAnswersService service;

	@RequestMapping(value = "/questions/{egsId}", method = RequestMethod.GET)
	public Map getQuestions(@PathVariable Integer egsId, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("getQuestions().");
		logger.debug("egsId: " + egsId);

		Map result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("data", service.getQuesAnswer(egsId));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/schools/{schoolId}", method = RequestMethod.POST)
	public Map getSchools(@PathVariable Integer schoolId, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("getSchools().");
		logger.debug("schoolId: " + schoolId);

		Map result = new HashMap<>();
		result.put("success", true);

		return result;
	}

	@RequestMapping(value = "/grades/{gradeId}", method = RequestMethod.POST)
	public Map getGrades(@PathVariable Integer gradeId, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("getGrades().");
		logger.debug("gradeId: " + gradeId);

		Map result = new HashMap<>();
		result.put("success", true);

		return result;
	}

}
