package com.ustudy.info.controller;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.ustudy.info.model.Teacher;
import com.ustudy.info.services.TeacherService;

/**
 * @author jared
 *
 */
@RestController
@RequestMapping(value = "info/teacher/")
public class TeacherController {

	private static final Logger logger = LogManager.getLogger(TeacherController.class);
	
	@Autowired
	private TeacherService teaS;
	
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public List<Teacher> getList(@PathVariable int id, HttpServletResponse resp) {
		logger.debug("endpoint /teacher/list/" + id + " is visited");
		List<Teacher> res = null;
		try {
			res = teaS.getList(id);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			String msg = "Failed to retrieve item list from " + id;
			try {
				resp.sendError(500, msg);
			} catch (Exception re) {
				logger.warn("Failed to set error status in response");
			}
		}
		return res;
	}
	
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public Teacher displayItem(@PathVariable int id, HttpServletResponse resp) {
		logger.debug("endpoint /teacher/view/" + id + " is visited.");
		Teacher item = null;
		String msg = null;
		try {
			item = teaS.displayTeacher(id);
		} catch (IncorrectResultSizeDataAccessException ie) {
			logger.warn("displayItem(), " + ie.getLocalizedMessage());
			msg = "No item found for specified id " + id;
		} catch (Exception e) {
			logger.warn("displayItem(), " + e.getMessage());
			msg = "Failed to retrieve item " + id;
		}
		
		if (item == null) {
			resp.setStatus(404);
			resp.setHeader("reason", msg);
			logger.warn(msg);
		}
				
		return item;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable int id, HttpServletResponse resp) {
		logger.debug("endpoint /teacher/delete/" + id + " is visited.");
		String result = null;
		try {
			int numOfRows = teaS.deleteTeacher(id);
			if (numOfRows != 1)
				throw new RuntimeException("Number of affected rows is " + numOfRows);
			else
				result = "item deleted";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			result = "delete item failed";
			resp.setStatus(500);
		}
		return result;
	}
	
	/**
	 * endpoint for batch delete operation
	 * @param data
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public String delSet(@RequestBody @Valid String data, HttpServletResponse resp) {
		logger.debug("endpoint /teacher/delete is visited.");
		String result = null;
		try {
			int num = teaS.delTeas(data);
			if (num < 1)
				throw new RuntimeException("Number of deleted items is " + num);
			else {
				result = num + " items deleted";
			}
				
		} catch (Exception e) {
			result = "delete items failed";
			logger.warn("delSet()," + result + " --> " + e.getMessage());
			resp.setStatus(500);
		}
		return result;
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String createTeacher(@RequestBody @Valid Teacher item, HttpServletResponse resp, UriComponentsBuilder builder) {
		logger.debug("endpoint /teacher/add is visited.");
		String result = null;
		try {
			// Before create item, need to check whether corresponding orgId is valid.
		    int index = teaS.createTeacher(item);
		    logger.debug("createTeacher(), item created successfully with id " + index);
		    //set header location
		    resp.setHeader("Location", 
		    	builder.path("/teacher/view/{index}").buildAndExpand(index).toString());
		    result = "create item successfully";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			result = "create item failed";
			resp.setStatus(500);
		}
		
		return result;
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String updateTeacher(@RequestBody @Valid Teacher data, HttpServletResponse resp) {
		logger.debug("updateTeacher(), endpoint /teacher/update " + data.getId() + " is visited.");
		String result = null;
		try {
			teaS.updateTeacher(data, data.getId());
			
			logger.debug("updateTeacher(), update item successfully");
			
			result = "update item successfully";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			result = "update item failed";
			resp.setStatus(500);
		}
		return result;
	}
	
}
