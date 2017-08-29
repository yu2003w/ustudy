package com.ustudy.infocenter.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.infocenter.model.ClassInfo;
import com.ustudy.infocenter.model.Grade;
import com.ustudy.infocenter.model.School;
import com.ustudy.infocenter.model.SubjectLeader;
import com.ustudy.infocenter.model.SubjectTeac;
import com.ustudy.infocenter.services.SchoolService;

@RestController
@RequestMapping("school/")
public class SchoolController {

	private static final Logger logger = LogManager.getLogger(SchoolController.class);
	
	@Autowired
	private SchoolService schS;
	
	@RequestMapping(value="/detail", method = RequestMethod.GET)
	public School getSchool(HttpServletResponse resp) {
		logger.debug("endpoint school/detail is accessed to retrieve information");
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
			resp.setStatus(500);
			resp.setHeader("reason", msg);
			return null;
		}
		
		if (item == null) {
			resp.setStatus(404);
			resp.setHeader("reason", msg);
			logger.warn(msg);
		}
				
		return item;
	}
	
	@RequestMapping(value = "/departsubs/{dName}", method = RequestMethod.GET)
	public List<SubjectLeader> getDepartSubs(@PathVariable String dName, HttpServletResponse resp) {
		logger.debug("endpoint /school/departsubs/" + dName + " is accessed");
		List<SubjectLeader> subL = null;
		String msg = null;
		try {
			subL = schS.getDepSubs(dName);
		} catch (Exception e) {
			msg = "getDepartSubs(), failed to retrieve deppartment subject-teacher information";
			logger.warn(msg + e.getMessage());
			resp.setStatus(500);
			resp.setHeader("reason", msg);
			return null;
		}
		
		if (subL == null) {
			resp.setStatus(404);
			resp.setHeader("reason", msg);
			logger.warn(msg);
		}
		return subL;
	}
	
	@RequestMapping(value = "/departsubs/update/{dName}", method = RequestMethod.POST)
	public String updateDepartSubs(@PathVariable String dName, @RequestBody @Valid List<SubjectLeader> subs,
			HttpServletResponse resp) {
		logger.debug("updateDepartSubs(), endpoint /school/departsubs/update/ " + dName + " is visited");
		String result = null;
		try {
			int numR = schS.updateDepSubs(subs, dName);
			if (numR < 0) {
				String msg = "updateDepartSubs(), department-subjects update failed, maybe something goes wrong";
				logger.warn(msg);
				throw new RuntimeException(msg);
			}
			result = "update department subjects-teachers succeed.";
		} catch (Exception e) {
			resp.setStatus(500);
			result = "update department subjects-teachers failed. ";
			logger.warn("updateDepartSubs(), " + result + e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/grade/{id}", method = RequestMethod.GET)
	public Grade getGrade(@PathVariable String id, HttpServletResponse resp) {
		logger.debug("getGrade(), endpoint /school/grade/" + id + " is visited.");
		return null;
	}
	
	@RequestMapping(value = "/grade/update/", method = RequestMethod.POST)
	public String updateGrade(@RequestBody @Valid Grade g, HttpServletResponse resp) {
		return null;
	}
	
	@RequestMapping(value = "/gradesub/{id}", method = RequestMethod.GET)
	public List<SubjectTeac> getGradeSub(@PathVariable String id, HttpServletResponse resp) {
		return null;
	}
	
	@RequestMapping(value = "/class/{id}", method = RequestMethod.GET)
	public ClassInfo getClassInfo(@PathVariable String id, HttpServletResponse resp) {
		return null;
	}
	
	@RequestMapping(value = "/class/update/", method = RequestMethod.POST)
	public String updateClassInfo(@RequestBody @Valid ClassInfo cls, HttpServletResponse resp) {
		return null;
	}
	
	@RequestMapping(value = "/classsub/{id}", method = RequestMethod.GET)
	public List<SubjectTeac> getClassSub(@PathVariable String id, HttpServletResponse resp) {
		return null;
	}
}
