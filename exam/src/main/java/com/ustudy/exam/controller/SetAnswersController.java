package com.ustudy.exam.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.exam.service.SetAnswersService;

import net.sf.json.JSONObject;

@RestController
@RequestMapping(value = "/setanswers")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SetAnswersController {

	private static final Logger logger = LogManager.getLogger(SetAnswersController.class);

	@Autowired
	private SetAnswersService service;

	@RequestMapping(value = "/getAnswers/{egsId}", method = RequestMethod.POST)
	public Map getQuesAnswers(@PathVariable Integer egsId, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("getSubjects().");
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

	@RequestMapping(value = "/saveAnswers/{egsId}", method = RequestMethod.POST)
	public Map saveQuesAnswers(@PathVariable Integer egsId, @RequestBody String parameters, HttpServletRequest request,
			HttpServletResponse response) {

		logger.debug("getSubjects().");
		logger.debug("egsId: " + egsId);

		Map result = new HashMap<>();

		try {
			JSONObject data = JSONObject.fromObject(parameters);
			if (service.saveQuesAnswers(egsId, data)) {
				result.put("success", true);
			} else {
				result.put("success", false);
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

}
