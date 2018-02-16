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
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.dashboard.model.Account;
import com.ustudy.dashboard.services.AccountService;
import com.ustudy.dashboard.util.DashboardUtil;

@RestController
public class LoginController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
	@Autowired
	AccountService accS;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public Account login(HttpServletRequest request, HttpServletResponse response) {
				
		logger.debug("endpoint /login is visited");
		String msg = null;
		boolean status = true;
		
		Account user = null;
		
		Subject currentUser = SecurityUtils.getSubject();
		
		if (!currentUser.isAuthenticated()) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			logger.debug("login() is invoked with username->" + username + ";password->" + password);
			
			try {
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				token.setRememberMe(true);
				currentUser.login(token);
				
				logger.trace("Token retrieved -> " + token.toString());
				
			} catch (UnknownAccountException | IncorrectCredentialsException uae) {
				msg = "Attempt to access with invalid account -> username:" + username;
				status = false;
			} catch (LockedAccountException lae) {
				msg = "Account is locked, username:" + username;
				status = false;
			} catch (AuthenticationException ae) {
				msg = ae.getMessage();
				status = false;
			} catch (Exception e) {
				msg = e.getMessage();
				status = false;
			}
			
		}
		
		if (status) {
			logger.debug("user [" + currentUser.getPrincipal() + "] loggined successfully");
			
			/* Don't need to redirect, front end will handle redirect stuff
			SavedRequest sr = WebUtils.getAndClearSavedRequest(request);
			redirectUrl = sr.getRequestUrl();
			*/
			try {
				Session ses = currentUser.getSession(true);
				
				user = accS.findUserByLoginName(currentUser.getPrincipal().toString());
				
				if (user != null) {
					if (!accS.updateLLTime(user.getId())) {
						logger.error("login(), failed to set ll_time for user " + currentUser.getPrincipal().toString());
					}
					
					ses.setAttribute("uid", user.getLoginname());
					ses.setAttribute("uname", user.getFullname());
					ses.setAttribute("role", DashboardUtil.getAcctRoleMap().get(user.getRoleName()));
				}
				else {
					logger.error("login(), failed to retrieve user information for " + currentUser.getPrincipal().toString());
					response.setStatus(500);
					response.setHeader("loginresult", "failed to retrieve user information");
				} 
				
			} catch (Exception e) {
				logger.error("login(), populate user information failed->" + e.getMessage());
				response.setStatus(500);
				response.setHeader("loginresult", "populate user information failed");
			}
			
			
		} else {
			logger.error(msg);
			response.setStatus(500);
			response.setHeader("loginresult", "authentication failed");
		}
		
		/*
		 * No need to redirect, front end will control logic for login request
		try {
			logger.debug("redirect to " + redirectUrl);
			response.sendRedirect(redirectUrl);
		} catch (Exception re) {
			logger.warn("Failed to redirect to login pages --> " + re.getMessage());
		}
		*/
		
		return user;
		
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET) 
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		
		Subject currentUser = SecurityUtils.getSubject();
		logger.info("user " + currentUser.getPrincipal().toString() + " logged out");
		
		currentUser.logout();
		
	}
	
	
	@RequestMapping(value="/loginId", method = RequestMethod.GET)
	public Account getLoginUser(HttpServletResponse resp) {
		logger.debug("endpoint /loginId is visited");
		Subject cUser = null;
		Account u = null;
		
		try {
			cUser = SecurityUtils.getSubject();
		} catch (Exception e) {
			logger.error("getLoginUser(), Failed to get subject --> " + e.getMessage());
			return u;
		}
		
		if (cUser.getPrincipal() == null) {
			logger.info("getLoginUser(), User didn't log in");
			resp.setStatus(530);
			resp.setHeader("Failure reason:", "No User logged in");
			return u;
		}
		else {
			//user already logged in, retrieve information from session storage
			Session ses = cUser.getSession();
			u = new Account(ses.getAttribute("uid").toString(), ses.getAttribute("uname").toString(), 
					ses.getAttribute("role").toString());
			logger.debug("getLoginUser(), user->" + u.toString());
		}
		
		return u;
		
	}
	
}
