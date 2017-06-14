package com.ustudy.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.dashboard.model.Role;
import com.ustudy.dashboard.services.RoleService;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequiresAuthentication
	@RequiresRoles(value={"admin"})
	@RequiresPermissions("dashboard:view")
	@RequestMapping("/list")
	@Transactional
	public List<Role> list(Role role,HttpServletRequest request,HttpServletResponse response) {
		return roleService.list(role);
	}
	
	@RequestMapping("/insert")
	public boolean insert(Role role,HttpServletRequest request,HttpServletResponse response) {
		return roleService.insertRole(role);
	}
	
	@RequestMapping("/delete/{id}")
	public boolean delete(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return roleService.deleteRoleById(id);
	}
	
	@RequestMapping("/detailByName/{name}")
	public Role detailByName(@PathVariable("name")String name,HttpServletRequest request,HttpServletResponse response) {
		return roleService.findRoleByName(name);
	}
	
	@RequestMapping("/detail/{id}")
	public Role detailById(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return roleService.findRoleById(id);
	}
	
	@RequestMapping("/update")
	public boolean update(Role role,HttpServletRequest request,HttpServletResponse response) {
		return roleService.updateRole(role);
	}

}
