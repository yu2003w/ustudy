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
import com.ustudy.infocenter.model.TeacherSub;
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
			msg = "getSchool()" + ie.getMessage();
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
	
	@RequestMapping(value = "departteac/{id}", method = RequestMethod.GET)
	public List<TeacherSub> getDepartTea(@PathVariable String id, HttpServletResponse resp) {
		logger.debug("getDepartTea(), endpoint /school/departteac/" + id + " is visited.");
		List<TeacherSub> itemL = null;
		String msg = null;
		try {
			itemL = schS.getDepTeac(id);
		} catch (IncorrectResultSizeDataAccessException ie) {
			msg = "getDepartTea()" + ie.getMessage();
			logger.warn(msg);
		} catch (Exception e) {
			msg = "getDepartTea(), failed to retrieve teacher information for department " + id + "\n"
					+ e.getMessage();
			logger.warn(msg);
			resp.setStatus(500);
			resp.setHeader("reason", msg);
			return null;
		}
		
		if (itemL == null) {
			resp.setStatus(404);
			resp.setHeader("reason", msg);
			logger.warn(msg);
		}
		return itemL;
	}
	
	@RequestMapping(value = "/grade/{id}", method = RequestMethod.GET)
	public Grade getGrade(@PathVariable String id, HttpServletResponse resp) {
		logger.debug("getGrade(), endpoint /school/grade/" + id + " is visited.");
		Grade item = null;
		String msg = null;
		try {
			item = schS.getGradeInfo(id);
		} catch (IncorrectResultSizeDataAccessException ie) {
			msg = "getGrade()" + ie.getMessage();
			logger.warn(msg);
		} catch (Exception e) {
			msg = "getGrade(),  failed to retrieve grade information --> \n" + e.getMessage();
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
	
	@RequestMapping(value = "/grade/update/", method = RequestMethod.POST)
	public String updateGrade(@RequestBody @Valid Grade g, HttpServletResponse resp) {
		logger.debug("endpoint /grade/update is to update " + g.getId());
		String result = null;
		try {
			int numOfRows = schS.updateGradeInfo(g);
			if (numOfRows == 1)
				logger.debug("updateGrade(), update item successfully");
			else {
				String msg = numOfRows + " items are updated, maybe something goes wrong";
				logger.warn(msg);
				throw new RuntimeException(msg);
			}
			result = "update item successfully";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			result = "update item failed";
			resp.setStatus(500);
		}
		return result;
	}
	
	@RequestMapping(value = "/gradeteac/{id}", method = RequestMethod.GET)
	public List<TeacherSub> getGradeTea(@PathVariable String id, HttpServletResponse resp) {
		logger.debug("getGradeTea(), endpoint /school/gradeteac/" + id + " is visited.");
		List<TeacherSub> itemL = null;
		String msg = null;
		try {
			itemL = schS.getGradeTeac(id);
		} catch (IncorrectResultSizeDataAccessException ie) {
			msg = "getGradeTea()" + ie.getMessage();
			logger.warn(msg);
		} catch (Exception e) {
			msg = "getGradeTea(), failed to retrieve teacher information for grade " + id + "\n"
					+ e.getMessage();
			logger.warn(msg);
			resp.setStatus(500);
			resp.setHeader("reason", msg);
			return null;
		}
		
		if (itemL == null) {
			resp.setStatus(404);
			resp.setHeader("reason", msg);
			logger.warn(msg);
		}
		return itemL;
	}
	
	@RequestMapping(value = "/class/{id}", method = RequestMethod.GET)
	public ClassInfo getClassInfo(@PathVariable String id, HttpServletResponse resp) {
		logger.debug("getClassInfo(), endpoint /school/class/" + id + " is visited.");
		ClassInfo item = null;
		String msg = null;
		try {
			item = schS.getClassInfo(id);
		} catch (IncorrectResultSizeDataAccessException ie) {
			msg = "getClassInfo()" + ie.getMessage();
			logger.warn(msg);
		} catch (Exception e) {
			msg = "getClassInfo(),  failed to retrieve class information --> \n" + e.getMessage();
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
	
	@RequestMapping(value = "/class/update/", method = RequestMethod.POST)
	public String updateClassInfo(@RequestBody @Valid ClassInfo cls, HttpServletResponse resp) {
		logger.debug("endpoint /class/update is to update " + cls.getId());
		String result = null;
		try {
			int numOfRows = schS.updateClassInfo(cls);
			if (numOfRows == 1)
				logger.debug("updateClassInfo(), update item successfully");
			else {
				String msg = numOfRows + " items are updated, maybe something goes wrong";
				logger.warn(msg);
				throw new RuntimeException(msg);
			}
			result = "update item successfully";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			result = "update item failed";
			resp.setStatus(500);
		}
		return result;
	}
	
	@RequestMapping(value = "/classteac/{id}", method = RequestMethod.GET)
	public List<TeacherSub> getClassTea(@PathVariable String id, HttpServletResponse resp) {
		logger.debug("getClassTea(), endpoint /school/classteac/" + id + " is visited.");
		List<TeacherSub> itemL = null;
		String msg = null;
		try {
			itemL = schS.getClassTeac(id);
		} catch (IncorrectResultSizeDataAccessException ie) {
			msg = "getClassTea()" + ie.getMessage();
			logger.warn(msg);
		} catch (Exception e) {
			msg = "getClassTea(), failed to retrieve teacher information for class " + id + "\n"
					+ e.getMessage();
			logger.warn(msg);
			resp.setStatus(500);
			resp.setHeader("reason", msg);
			return null;
		}
		
		if (itemL == null) {
			resp.setStatus(404);
			resp.setHeader("reason", msg);
			logger.warn(msg);
		}
		return itemL;
	}
}
