package com.ustudy.dashboard.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.dashboard.model.School;
import com.ustudy.dashboard.services.SchoolService;

@RestController
@RequestMapping(value="/school/")
public class SchoolController {

	private static final Logger logger = LogManager.getLogger(SchoolController.class);
	
	@Autowired
	private SchoolService ss;
	
	@RequestMapping(value="/list/{id}/", method = RequestMethod.GET)
	public List<School> getList(@PathVariable int id) {
		logger.debug("endpoint /school/list/ is visited");
		
		return ss.getList(id);
	}
}