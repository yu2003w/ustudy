package com.ustudy.mmadapter.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.mmadapter.UResp;
import com.ustudy.mmadapter.service.SchoolService;

@RestController
@RequestMapping(value = "/adapter/")
public class SchoolController {

	private static final Logger logger = LogManager.getLogger(SchoolController.class);
	
	@Autowired
	private SchoolService schS;
	
	@RequestMapping(value = "/info/schools/", method = RequestMethod.GET)
	public UResp getSchoolsByKey(@RequestParam(value = "schoolName", required = false) String key, 
			HttpServletResponse resp) {
		
		UResp res = new UResp();
		
		if (key == null || key.isEmpty()) {
			logger.info("getSchoolsByKey(), no key specified for search.");
			
		}
		
		try {
			res.setData(schS.getSchoolsByKey(key));
			res.setStatus(true);
		} catch (Exception e) {
			logger.error("getSchoolsByKey(), failed ->" + e.getMessage());
			res.setMsg(e.getMessage());
		}
		
		return res;
	}
	
}
