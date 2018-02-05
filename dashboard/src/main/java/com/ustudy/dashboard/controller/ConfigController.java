package com.ustudy.dashboard.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.dashboard.model.UResp;
import com.ustudy.dashboard.services.ConfigService;

@RestController
@RequestMapping(value = "/dashboard/")
public class ConfigController {

	private static final Logger logger = LogManager.getLogger(ConfigController.class);
	
	@Autowired
	private ConfigService confS;
	
	@RequestMapping(value = "config/sublist/")
	public UResp getSubList(HttpServletResponse resp) {
		UResp res = new UResp();
		
		try {
			res.setData(confS.getSubList());
			res.setRet(true);
		} catch (Exception e) {
			logger.error("getSubList(), failed to retrieve configuration for subject list");
			res.setMessage("failed to retrieve configuration for subject list");
		}
		
		return res;
	}
}
