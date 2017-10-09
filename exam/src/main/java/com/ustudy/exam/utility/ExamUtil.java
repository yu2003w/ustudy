package com.ustudy.exam.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ExamUtil {

	private final static Logger logger = LogManager.getLogger(ExamUtil.class);
	
	/*
	 * Calling this method should catch exceptions throws by shiro
	 */
	public static String retrieveSessAttr(String key) {
		Session ses = SecurityUtils.getSubject().getSession();
		Object obj = ses.getAttribute(key);
		if (obj != null)
			return obj.toString();
		else
			return null;
	}
	
	public static String getCurrentUserId() {
		Subject cUser = null;
		String uid = null;
		try {
			cUser = SecurityUtils.getSubject();
		} catch (Exception e) {
			logger.warn("getCurrentUserId(),Failed to get subject --> " + e.getMessage());
			return uid;
		}
		if (cUser.getPrincipal() == null) {
			logger.warn("getCurrentUserId(), User didn't log in");
		} else {
			uid = cUser.getPrincipal().toString();
		}
		
		return uid;
	}
	
}
