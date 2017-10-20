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

import com.ustudy.info.model.Student;
import com.ustudy.info.services.StudentService;

@RestController
@RequestMapping(value = "info/student/")
public class StudentController {

	private static final Logger logger = LogManager.getLogger(StudentController.class);
	
	@Autowired
	private StudentService stus;
	
	@RequestMapping(value = "list/{id}", method = RequestMethod.GET)
	public List<Student> getList(@PathVariable int id, HttpServletResponse resp) {
		logger.debug("endpoint /student/list/");
		List<Student> ret = null;
		try {
			ret = stus.getList(id);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			resp.setStatus(500);
		}
		if (ret == null) {
			logger.warn("No student items fetched from id " + id);
			resp.setStatus(500);
		}
		return ret;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String createItem(@RequestBody @Valid Student data, HttpServletResponse resp, 
			UriComponentsBuilder builder) {
		logger.debug("endpoint /student/add is visited");
		String result = null;
		try {
			int id = stus.createItem(data);
			logger.debug("Student item created with id " + id);
			resp.setHeader("Location",
				builder.path("/student/view/{id}").buildAndExpand(id).toString());
			result = "item created succesfully";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			resp.setStatus(500);
			result = "create item failed";
		}
		return result;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateItem(@RequestBody @Valid Student data, HttpServletResponse resp) {
		logger.debug("endpoint /student/update is visited");
		String result = null;
		try {
			int numOfRows = stus.updateItem(data);
			if (numOfRows == 1)
				logger.debug("updateItem(), update item successfully");
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
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public String deleteItem(@PathVariable int id, HttpServletResponse resp) {
		logger.debug("endpoint /student/delete is visited");
		String result = null;
		try {
			int numOfRows = stus.deleteItem(id);
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
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delItemSet(@RequestBody String ids, HttpServletResponse resp) {
		logger.debug("endpoint /student/delete is visited");
		String result = null;
		try {
			int num = stus.delItemSet(ids);
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
	
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public Student viewItem(@PathVariable int id, HttpServletResponse resp) {
		Student item = null;
		String msg = null;
		try {
			item = stus.displayItem(id);
		} catch (IncorrectResultSizeDataAccessException ie) {
			logger.warn("displayItem(), " + ie.getLocalizedMessage());
			msg = "No items found for specified id = " + id;
		} catch (Exception e) {
			logger.warn("displayItem(), " + e.getMessage());
			msg = "Failed to retrieve item " + id;
		}
		
		if (item == null) {
			try {
				resp.sendError(500, msg);
			} catch (Exception re) {
				logger.warn("viewItem(), Failed to set error status in response");
			}
		}
				
		return item;
	}
}