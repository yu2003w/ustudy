package com.ustudy.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
	@RequestMapping(value="/login/", method=RequestMethod.POST)
	public String login(HttpServletRequest request) {
		 
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		logger.debug("login() is invoked with username->" + username + 
				";password->" + password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(true);
		logger.debug("Token retrieved -> " + token.toString());
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
			logger.debug(currentUser.isAuthenticated());
		}catch (AuthenticationException ae) {
			logger.debug(ae.getMessage());
		}
		return null;
	}
}
