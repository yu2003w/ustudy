package com.ustudy.dashboard.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
	@RequestMapping(value="/login/", method=RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) {
				
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
		} catch (UnknownAccountException | IncorrectCredentialsException uae) {
			logger.info("Attempt to access with invalid account -> username:" + username);
			request.setAttribute("loginresult", "invalid account");
		} catch (LockedAccountException lae) {
			logger.warn("Account is locked, username:" + username);
		} catch (AuthenticationException ae) {
			logger.debug(ae.getMessage());
		}
		
		if (currentUser.isAuthenticated()) {
			logger.debug("Authentication successful");
			response.setHeader("currentUser", username);
			// create session here
			Session ses = currentUser.getSession();
		}
		else {
			token.clear();
		}
		
		return "/login";
	}
}
