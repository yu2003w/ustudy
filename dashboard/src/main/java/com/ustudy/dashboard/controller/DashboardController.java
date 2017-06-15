package com.ustudy.dashboard.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;


@RestController
@RequestMapping(value = "/")
public class DashboardController {

	private static final Logger logger = LogManager.getLogger(DashboardController.class);

	@RequiresAuthentication
	@RequiresRoles("admin")
	@RequiresPermissions("dashboard:update")
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String welcome(HttpServletRequest request) {
		logger.debug("/dashboard/index is accessed");
		return "Welcome to Administration dashboard";
	}
	
}
