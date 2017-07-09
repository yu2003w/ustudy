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
				
		logger.debug("endpoint /login is visited");
		String msg = null;
		boolean status = true;
		
		Subject currentUser = SecurityUtils.getSubject();
		
		if (!currentUser.isAuthenticated()) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			logger.debug("login() is invoked with username->" + username + 
					";password->" + password);
			
			try {
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				token.setRememberMe(true);
				currentUser.login(token);
				logger.debug("Token retrieved -> " + token.toString());
			} catch (UnknownAccountException | IncorrectCredentialsException uae) {
				msg = "Attempt to access with invalid account -> username:" + username;
				status = false;
			} catch (LockedAccountException lae) {
				msg = "Account is locked, username:" + username;
				status = false;
			} catch (AuthenticationException ae) {
				logger.warn(ae.getMessage());
				status = false;
			} catch (Exception e) {
				logger.warn(e.getMessage());
				status = false;
			}
			
		} 
		
		String redirectUrl = null;
		
		if (status) {
			logger.debug("user [" + currentUser.getPrincipal() + "] loggined successfully");
			// login successful, redirect to original request
			msg = "Authentication successful";
			SavedRequest sr = WebUtils.getAndClearSavedRequest(request);
			//WebUtils.redirectToSavedRequest(request, response, null);
			//WebUtils.issueRedirect(request, response, sr.getRequestUrl(), null, false);
			redirectUrl = sr.getRequestUrl();
		} else {
			logger.warn(msg);
			redirectUrl = "/dashboard/login.jsp";
			// login failed here, need to redirect to login pages
		}
		
		try {
			logger.debug("redirect to " + redirectUrl);
			response.sendRedirect(redirectUrl);
		} catch (Exception re) {
			logger.warn("Failed to redirect to login pages --> " + re.getMessage());
		}
		
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET) 
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		
		Subject currentUser = SecurityUtils.getSubject();
		logger.info("user " + currentUser.getPrincipal().toString() + " logged out");
		
		currentUser.logout();
		
	}
	
	
	@RequestMapping(value="/loginId", method = RequestMethod.GET)
	public String getLoginName(HttpServletResponse resp) {
		logger.debug("endpoint /loginId is visited");
		Subject cUser = null;
		try {
			cUser = SecurityUtils.getSubject();
		} catch (Exception e) {
			logger.warn("Failed to get subject --> " + e.getMessage());
		}
		
		logger.debug(cUser.getPrincipal().toString());
		return cUser.getPrincipal().toString();
		
	}
	
}
