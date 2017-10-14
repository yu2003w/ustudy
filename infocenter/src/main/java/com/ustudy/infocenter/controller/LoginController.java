package com.ustudy.infocenter.controller;

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
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.infocenter.model.TeacRole;
import com.ustudy.infocenter.model.Teacher;
import com.ustudy.infocenter.services.TeacherService;


/**
 * @author jared
 * This class is similar with that in other modules such as dashboard.
 * 
 *
 */
@RestController
public class LoginController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
	// for each login user, need to retrieve some meta information such as orgtype, orgid,
	// orgname and so on.
	@Autowired
	private TeacherService teaS;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public TeacRole login(HttpServletRequest request, HttpServletResponse response) {
				
		logger.debug("endpoint /login is visited");
		String msg = null;
		boolean status = true;
		TeacRole teaR = null;
		
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
		
		// current architecture is frontend served by nginx, and nginx then talked with tomcat container.
		// For login operation, no need to redirect and only set response status and json data.
		
		if (status) {
			logger.debug("user [" + currentUser.getPrincipal() + "] logged in successfully");
			
			// need to populate current user's orgtype, orgid information into session
			Teacher tea = teaS.findTeacherById(currentUser.getPrincipal().toString());
			
			// update last login time for logged teacher
			if (!teaS.updateLLTime(tea.getId())) {
				logger.warn("login(), failed to set login time for user " + tea.getTeacId());
				// although update login time failed, login succeed. only warning here.
			}
			
			try {
				Session ses = currentUser.getSession(true);
				ses.setAttribute("orgType", tea.getOrgtype());
				ses.setAttribute("orgId", tea.getOrgid());
			} catch (SessionException e) {
				logger.warn("login(), session failed -> " + e.getMessage());
				response.setStatus(404);
				response.setHeader("loginresult", "session operation failed.");
				return teaR;
			}
			
			logger.debug("logged user: orgtype -> " + tea.getOrgtype() + "; orgid -> " + tea.getOrgid());
			teaR = new TeacRole(tea.getTeacId(), teaS.findPriRoleById(tea.getTeacId()));
			logger.debug("login(), " + teaR.toString());
		} else {
			logger.warn(msg);
			response.setStatus(404);
			response.setHeader("loginresult", "login failed");
		}
		
		return teaR;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET) 
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		
		Subject currentUser = SecurityUtils.getSubject();
		
		if(null != currentUser && null != currentUser.getPrincipal()){
			logger.info("user " + currentUser.getPrincipal().toString() + " logged out");
		}
		
		currentUser.logout();
		
	}
	
	
	@RequestMapping(value="/loginId", method = RequestMethod.GET)
	public TeacRole getLoginUser(HttpServletResponse resp) {
		logger.debug("getLoginUser(), endpoint /loginId is visited");
		Subject cUser = null;
		TeacRole u = null;
		try {
			cUser = SecurityUtils.getSubject();
		} catch (Exception e) {
			logger.warn("getLoginUser(),Failed to get subject --> " + e.getMessage());
		}
		if (cUser.getPrincipal() == null) {
			logger.warn("getLoginUser(), User didn't log in." + cUser.toString());
			resp.setStatus(404);
			resp.setHeader("Failure reason:", "No User logged in");
			return u;
		}
		else {
			String uId = cUser.getPrincipal().toString();
			u = new TeacRole(uId, teaS.findPriRoleById(uId));
			logger.debug("getLoginUser(), " + u.toString());
			return u;
		}
		
	}
}
