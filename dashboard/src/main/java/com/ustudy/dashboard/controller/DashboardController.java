package com.ustudy.dashboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class DashboardController {

	private static final Logger logger = LogManager.getLogger(DashboardController.class);
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String welcome(HttpServletRequest request) {
		logger.debug("/dashboard/index is visited");
		return "Welcome to Administration dashboard";
	}
	
}