package com.ustudy.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;


@RestController
@RequestMapping(value = "/admin")
public class DashboardController {

	@RequiresAuthentication
	@RequiresPermissions("admin:view")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome() {
		return "Welcome to Administration dashboard";
	}
	
}
