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
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.exam.model.User;
import com.ustudy.exam.service.UserService;

@RestController
public class LoginController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);

	// for each login user, need to retrieve some meta information such as
	// orgtype, orgid,
	// orgname and so on.
	@Autowired
	private UserService userS;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) {

		logger.debug("endpoint /login is visited");
		String msg = null;
		boolean status = true;

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
				logger.warn(ae.getMessage());
				status = false;
			} catch (Exception e) {
				logger.warn(e.getMessage());
				status = false;
			}

		}

		String redirectUrl = null;

		if (status) {
			logger.debug("user [" + currentUser.getPrincipal() + "] logged in successfully");
			
			// login successful, redirect to original request or /exam/welcome
			SavedRequest sr = WebUtils.getAndClearSavedRequest(request);
			// WebUtils.redirectToSavedRequest(request, response, null);
			// WebUtils.issueRedirect(request, response, sr.getRequestUrl(), null, false);
			if (sr == null) {
				logger.debug("login(), no saved request existed here.");
				redirectUrl = "/info/welcome";
			}
			else
				redirectUrl = sr.getRequestUrl();
			
			// need to populate current user's orgtype, orgid information
			try {
				Session ses = currentUser.getSession(true);
				User u = userS.findUserById(currentUser.getPrincipal().toString());
				if (u != null) {
					ses.setAttribute("userName", u.getUname());
				} else {
					logger.warn("login(), failed to retrieve user information for id " + currentUser.getPrincipal());
					redirectUrl = "/exam/error.jsp";
				}
					
			} catch (Exception e) {
				logger.warn("login(), session failed -> " + e.getMessage());
				redirectUrl = "/exam/error.jsp";
			}
			
			logger.debug("login(), redirect url ->" + redirectUrl);

			/*
			 * ses.setAttribute("orgType", u.getOrgtype());
			 * ses.setAttribute("orgId", u.getUid());
			 * 
			 * // update last login time for logged teacher if
			 * (!userS.updateLLTime(u.getUid()))
			 * logger.warn("login(), failed to set last login time for user " +
			 * currentUser.getPrincipal().toString());
			 * 
			 * // TODO: need to redirect user to proper pages based on
			 * organization type
			 * 
			 * logger.debug("logged user: orgtype -> " + tea.getOrgtype() +
			 * "; orgid -> " + tea.getOrgid());
			 */
		} else {
			logger.warn(msg);
			// login failed here, need to redirect to error pages
			redirectUrl = "/exam/error.jsp";
		}

		try {
			logger.debug("login(), redirect to " + redirectUrl);
			response.sendRedirect(redirectUrl);
		} catch (Exception re) {
			logger.warn("Failed to redirect --> " + re.getMessage());
		}

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) {

		Subject currentUser = SecurityUtils.getSubject();
		logger.info("user " + currentUser.getPrincipal().toString() + " logged out");

		currentUser.logout();

	}

	@RequestMapping(value = "/loginId", method = RequestMethod.GET)
	public User getLoginUser(HttpServletResponse resp) {
		logger.debug("getLoginUser(), endpoint /loginId is visited");
		Subject cUser = null;
		User u = null;
		try {
			cUser = SecurityUtils.getSubject();
		} catch (Exception e) {
			logger.warn("getLoginUser(),Failed to get subject --> " + e.getMessage());
		}
		if (cUser.getPrincipal() == null) {
			logger.warn("getLoginUser(), User didn't log in");
			resp.setStatus(404);
			resp.setHeader("Failure reason:", "No User logged in");
			return u;
		} else {
			String uId = cUser.getPrincipal().toString();
			u = userS.findUserById(uId);
			logger.debug("getLoginUser(), " + u.toString());
			return u;
		}

	}
}
