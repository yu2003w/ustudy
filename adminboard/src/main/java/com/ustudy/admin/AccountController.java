package com.ustudy.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.admin.model.Account;

@RestController
@RequestMapping(value = "/users")
public class AccountController {

	@RequestMapping(value = "/list/", method = RequestMethod.GET)
	public Account list() {
		Account u = new Account("jared");
		return u;
	}

}
