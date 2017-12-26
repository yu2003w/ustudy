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

import com.ustudy.exam.service.TaskAllocationService;

@RestController
@RequestMapping(value = "/task/allocation")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TaskAllocationController {

	private static final Logger logger = LogManager.getLogger(TaskAllocationController.class);

	@Autowired
	private TaskAllocationService service;

	@RequestMapping(value = "/questions/{egsId}", method = RequestMethod.GET)
	public Map getQuestions(@PathVariable Long egsId, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("getQuestions().");
		logger.debug("egsId: " + egsId);

		Map result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("data", service.getQuestions(egsId));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
	
	@RequestMapping(value = "/questions/{examId}/{gradeId}/{subjectId}", method = RequestMethod.GET)
	public Map getQuestions(@PathVariable Long examId, @PathVariable Long gradeId, @PathVariable Long subjectId, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("getQuestions().");
		logger.debug("examId: " + examId + ",gradeId: " + gradeId + ",subjectId: " + subjectId);

		Map result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("data", service.getQuestions(examId, gradeId, subjectId));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/school/{schoolId}", method = RequestMethod.GET)
	public Map getSchool(@PathVariable String schoolId, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("getSchool().");
		logger.debug("schoolId: " + schoolId);

		Map result = new HashMap<>();
		try {
			result.put("success", true);
			result.put("data", service.getSchool(schoolId));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/grade/{gradeId}", method = RequestMethod.GET)
	public Map getGrade(@PathVariable Long gradeId, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("getGrade().");
		logger.debug("gradeId: " + gradeId);

		Map result = new HashMap<>();
		try {
			result.put("success", true);
			result.put("data", service.getGrade(gradeId));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/grade/notask/teachers/{gradeId}", method = RequestMethod.GET)
	public Map getGradeNotaskTeachers(@PathVariable Long gradeId, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("getGrade(gradeId: "+gradeId+").");

		Map result = new HashMap<>();
		try {
			result.put("success", true);
			result.put("data", service.getGradeNotaskTeachers(gradeId));
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

}
