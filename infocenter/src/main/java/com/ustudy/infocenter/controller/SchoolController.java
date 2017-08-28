package com.ustudy.infocenter.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.infocenter.model.School;
import com.ustudy.infocenter.services.SchoolService;

@RestController
@RequestMapping("school/")
public class SchoolController {

	private static final Logger logger = LogManager.getLogger(SchoolController.class);
	
	@Autowired
	private SchoolService schS;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public School getSchool(HttpServletResponse resp) {
		logger.debug("endpoint school/ is accessed to retrieve information");
		School item = null;
		String msg = null;
		try {
			item = schS.getSchool();
		} catch (IncorrectResultSizeDataAccessException ie) {
			msg = "getSchool()" + ie.getLocalizedMessage();
			logger.warn(msg);
		} catch (Exception e) {
			msg = "getSchool(),  failed to retrieve school information --> \n" + e.getMessage();
			logger.warn(msg);
		}
		
		if (item == null) {
			resp.setStatus(404);
			resp.setHeader("reason", msg);
			logger.warn(msg);
		}
				
		return item;
	}
	
}
