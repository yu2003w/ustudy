package com.ustudy.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.dashboard.model.School;
import com.ustudy.dashboard.services.SchoolService;

@RestController
@RequestMapping(value="/school/")
public class SchoolController {

	private static final Logger logger = LogManager.getLogger(SchoolController.class);
	// private static final Log logger = LogFactory.getLog(SchoolController.class);
	
	@Autowired
	private SchoolService ss;
	
	@RequestMapping(value="/list/{id}/", method = RequestMethod.GET)
	public List<School> getList(@PathVariable int id) {
		logger.debug("endpoint /school/list/ is visited");
		
		return ss.getList(id);
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String createItem(@RequestBody @Valid School item, HttpServletResponse resp) {
		logger.debug("endpoint /school/add/ is visited.");
		logger.debug(item.toString());
		
		String result = null;
		try {
		    int index = ss.createItem(item);
		    logger.debug("School created successfully with id " + index);
		    resp.setHeader("Location", "school/item/" + index);
		    result = "create item successfully";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			result = "create item failed";
			resp.setStatus(500);
		}
		
		return result;
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	public String deleteItem(@PathVariable int id) {
		return null;
	}
}
