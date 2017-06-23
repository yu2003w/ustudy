package com.ustudy.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.dashboard.model.Account;
import com.ustudy.dashboard.services.AccountService;

@RestController
@RequestMapping(value="/user")
public class AccountController {

	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<Account> list(Account account,HttpServletRequest request,HttpServletResponse response) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		LOGGER.info("startTime:"+startTime+",endTime:"+endTime);
		return accountService.list(account,startTime,endTime);
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public boolean insert(Account account,HttpServletRequest request,HttpServletResponse response) {
		return accountService.insertUser(account);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return accountService.deleteUserById(id);
	}
	
	@RequestMapping(value = "/detailByName/{username}", method = RequestMethod.GET)
	public Account detailByName(@PathVariable("username")String username,HttpServletRequest request,HttpServletResponse response) {
		return accountService.findUserByLoginName(username);
	}
	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public Account detailById(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return accountService.findUserById(id);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public boolean update(Account account,HttpServletRequest request,HttpServletResponse response) {
		return accountService.updateUser(account);
	}
	
	@RequestMapping(value = "/addRole/{id}/{roleId}", method = RequestMethod.GET)
	public boolean addRole(@PathVariable("id")int id,@PathVariable("roleId")int roleId,HttpServletRequest request,HttpServletResponse response) {
		return true;
	}
	
	@RequestMapping(value = "/deleteRole/{id}/{roleIds}", method = RequestMethod.GET)
	public boolean deleteRole(@PathVariable("id")int id,@PathVariable("roleIds")String roleIds,HttpServletRequest request,HttpServletResponse response) {
		return true;
	}
	
}
