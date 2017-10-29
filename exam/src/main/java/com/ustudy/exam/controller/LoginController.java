package com.ustudy.exam.controller;

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

import com.ustudy.exam.model.TeacRole;
import com.ustudy.exam.model.Teacher;
import com.ustudy.exam.service.TeacherService;

@RestController
public class LoginController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);

	// for each login user, need to retrieve some meta information such as
	// orgtype, orgid, orgname, roles and so on.
	@Autowired
	private TeacherService userS;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Teacher login(HttpServletRequest request, HttpServletResponse response) {

		logger.debug("endpoint /login is visited");
		String msg = null;
		boolean status = true;
		Teacher tea = null;

		Subject currentUser = SecurityUtils.getSubject();

		if (!currentUser.isAuthenticated()) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			logger.debug("login() is invoked with username->" + username + ";password->" + password);

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
				msg = ae.getMessage();
				status = false;
			} catch (Exception e) {
				msg = e.getMessage();
				status = false;
			}

		}

		if (status) {
			logger.debug("user [" + currentUser.getPrincipal() + "] logged in successfully");
			
			// need to populate current user's orgtype, orgid information
			try {
				Session ses = currentUser.getSession(true);
				tea = userS.findUserById(currentUser.getPrincipal().toString());
				if (tea != null) {
					ses.setAttribute("uname", tea.getUname());
					ses.setAttribute("orgType", tea.getOrgtype());
					ses.setAttribute("orgId", tea.getOrgid());
				} else {
					logger.warn("login(), failed to retrieve user information for id " + currentUser.getPrincipal());
					response.setStatus(404);
					response.setHeader("loginresult", "failed to retrieve user information");
					return tea;
				}
				tea.setRoles(userS.getRolesById(tea.getUid()));
				logger.debug("login()," + tea.toString());
				
			} catch (Exception e) {
				logger.warn("login(), session failed -> " + e.getMessage());
				response.setStatus(404);
				response.setHeader("loginresult", "session operation failed");
				return tea;
			}
			
		} else {
			logger.warn(msg);
			response.setStatus(404);
			response.setHeader("loginresult", "authencation failed");
		}
		
		return tea;

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) {

		Subject currentUser = SecurityUtils.getSubject();
		logger.info("user " + currentUser.getPrincipal().toString() + " logged out");

		currentUser.logout();

	}

	@RequestMapping(value = "/loginId", method = RequestMethod.GET)
	public TeacRole getLoginUser(HttpServletRequest request, HttpServletResponse resp) {
		logger.debug("getLoginUser(), endpoint /loginId is visited");
		Subject cUser = null;
		TeacRole u = null;
		try {
			cUser = SecurityUtils.getSubject();
		} catch (Exception e) {
			logger.warn("getLoginUser(),Failed to get subject --> " + e.getMessage());
			return u;
		}
		if (cUser.getPrincipal() == null) {
			logger.warn("getLoginUser(), User didn't log in");
			resp.setStatus(530);
			resp.setHeader("Failure reason:", "No User logged in");
			return u;
		} else {
			// at this point, user information could be retrieved from session
			String uId = cUser.getPrincipal().toString();
			//Session ses = cUser.getSession();
			
			/*
			u = new Teacher(uId, ses.getAttribute("uname").toString(), 
					ses.getAttribute("orgtype").toString(), ses.getAttribute("orgid").toString());
			// need to retrieve roles for the login teacher
			// u.setRoles(userS.getRolesById(uId));
			// only retrieve highest priority role for the logined user */
			
			u = new TeacRole(uId, userS.findPriRoleById(uId));
			logger.debug("getLoginUser(), " + u.toString());
			return u;
		}

	}
}
