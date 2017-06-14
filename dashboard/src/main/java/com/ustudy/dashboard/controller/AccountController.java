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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.dashboard.model.Account;
import com.ustudy.dashboard.services.AccountService;

@RestController
@RequestMapping(value = "/user")
public class AccountController {

	private static final Log logger = LogFactory.getLog(AccountController.class);
	
	@Autowired
	private AccountService accountService;
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/user/list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<Account> list(Account account,HttpServletRequest request,HttpServletResponse response) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		logger.info("startTime:"+startTime+",endTime:"+endTime);
		return accountService.list(account,startTime,endTime);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/user/insert")
	@RequestMapping("/insert")
	public boolean insert(Account account,HttpServletRequest request,HttpServletResponse response) {
		return accountService.insertUser(account);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/user/delete")
	@RequestMapping("/delete/{id}")
	public boolean delete(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return accountService.deleteUserById(id);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/user/detail")
	@RequestMapping("/detailByName/{username}")
	public Account detailByName(@PathVariable("username")String username,HttpServletRequest request,HttpServletResponse response) {
		return accountService.findUserByLoginName(username);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/user/detail")
	@RequestMapping("/detail/{id}")
	public Account detailById(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return accountService.findUserById(id);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/user/update")
	@RequestMapping("/update")
	public boolean update(Account account,HttpServletRequest request,HttpServletResponse response) {
		return accountService.updateUser(account);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/user/addRole")
	@RequestMapping("/addRole/{id}/{roleId}")
	public boolean addRole(@PathVariable("id")int id,@PathVariable("roleId")int roleId,HttpServletRequest request,HttpServletResponse response) {
		return true;
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/user/deleteRole")
	@RequestMapping("/deleteRole/{id}/{roleIds}")
	public boolean deleteRole(@PathVariable("id")int id,@PathVariable("roleIds")String roleIds,HttpServletRequest request,HttpServletResponse response) {
		return true;
	}

}