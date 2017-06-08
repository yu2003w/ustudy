package com.ustudy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.admin.model.Account;

@RestController
@RequestMapping(value = "/users")
public class AccountController {

	private static final Logger logger = LogManager.getLogger(AccountController.class);
	
	@RequestMapping(value = "/list/", method = RequestMethod.GET)
	public Account list() {
		
		logger.debug("endpoint /list is visited");
		Account u = new Account("jared");
		return u;
	}

}
