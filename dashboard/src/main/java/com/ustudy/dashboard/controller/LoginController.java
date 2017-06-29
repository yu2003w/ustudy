package com.ustudy.dashboard.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/")
public class LoginController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) {
				
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String msg = null;
		
		logger.debug("login() is invoked with username->" + username + 
				";password->" + password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(true);
		logger.debug("Token retrieved -> " + token.toString());
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
			if (currentUser.isAuthenticated()) {
				msg = "Authentication successful";
				
				SavedRequest sr = WebUtils.getAndClearSavedRequest(request);
				//WebUtils.redirectToSavedRequest(request, response, null);
				//WebUtils.issueRedirect(request, response, sr.getRequestUrl(), null, false);
				logger.debug("redirect to " + sr.getRequestUrl());
				response.sendRedirect(sr.getRequestUrl());
			}
			else {
				token.clear();
				msg = "Authentication failure";
			}
			
		} catch (UnknownAccountException | IncorrectCredentialsException uae) {
			msg = "Attempt to access with invalid account -> username:" + username;
			logger.warn(msg);
		} catch (LockedAccountException lae) {
			msg = "Account is locked, username:" + username;
			logger.warn(msg);
		} catch (AuthenticationException ae) {
			logger.warn(ae.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}

		logger.warn(msg);
		
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET) 
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		
		Subject currentUser = SecurityUtils.getSubject();
		logger.info("user " + currentUser.getPrincipal().toString() + " logged out");
		
		currentUser.logout();
		
	}
}
