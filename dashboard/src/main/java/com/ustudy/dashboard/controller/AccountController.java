package com.ustudy.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.dashboard.model.Account;
import com.ustudy.dashboard.services.AccountService;

@RestController
@RequestMapping(value = "/users")
public class AccountController {

	private static final Log logger = LogFactory.getLog(AccountController.class);
	
	@Autowired
	private AccountService accountService;
	
	@RequiresAuthentication
	@RequiresRoles(value={"admin"})
	@RequiresPermissions("dashboard:view")
	@RequestMapping("/list")
	@Transactional
	public List<Account> list(Account account,HttpServletRequest request,HttpServletResponse response) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		logger.info("startTime:"+startTime+",endTime:"+endTime);
		return accountService.list(account,startTime,endTime);
	}
	
	@RequestMapping("/insert")
	public boolean insert(Account account,HttpServletRequest request,HttpServletResponse response) {
		return accountService.insertUser(account);
	}
	
	@RequestMapping("/delete/{id}")
	public boolean delete(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return accountService.deleteUserById(id);
	}
	
	@RequestMapping("/detailByName/{username}")
	public Account detailByName(@PathVariable("username")String username,HttpServletRequest request,HttpServletResponse response) {
		return accountService.findUserByLoginName(username);
	}
	
	@RequestMapping("/detail/{id}")
	public Account detailById(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return accountService.findUserById(id);
	}
	
	@RequestMapping("/update")
	public boolean update(Account account,HttpServletRequest request,HttpServletResponse response) {
		return accountService.updateUser(account);
	}

}