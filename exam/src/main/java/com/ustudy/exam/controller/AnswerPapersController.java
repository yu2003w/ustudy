package com.ustudy.exam.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.UResp;
import com.ustudy.exam.service.StudentPaperService;

import net.sf.json.JSONObject;

@RestController
public class AnswerPapersController {

	private static final Logger logger = LogManager.getLogger(AnswerPapersController.class);

	@Autowired
	private StudentPaperService service;
	
	@RequestMapping(value = "/answer/papers")
	public UResp getAnswerPapers(@RequestBody String parameter, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("getAnswerPapers().");
		logger.debug("parameter: " + parameter);

		UResp result = new UResp();

		try {
			result.setRet(true);
			result.setData(service.getAnswerPapers(JSONObject.fromObject(parameter)));
		} catch (Exception e) {
			result.setRet(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

}
