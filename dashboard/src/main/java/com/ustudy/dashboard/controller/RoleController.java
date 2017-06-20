package com.ustudy.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.dashboard.model.Role;
import com.ustudy.dashboard.services.RoleService;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<Role> list(Role role,HttpServletRequest request,HttpServletResponse response) {
		return roleService.list(role);
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public boolean insert(Role role,HttpServletRequest request,HttpServletResponse response) {
		return roleService.insertRole(role);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return roleService.deleteRoleById(id);
	}
	
	@RequestMapping(value = "/detailByName/{name}", method = RequestMethod.GET)
	public Role detailByName(@PathVariable("name")String name,HttpServletRequest request,HttpServletResponse response) {
		return roleService.findRoleByName(name);
	}
	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public Role detailById(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return roleService.findRoleById(id);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public boolean update(Role role,HttpServletRequest request,HttpServletResponse response) {
		return roleService.updateRole(role);
	}
	
	@RequestMapping(value = "/addPermission2Role/{id}/{permissionId}", method = RequestMethod.GET)
	public boolean addRole2User(@PathVariable("id")int id,@PathVariable("permissionId")int permissionId,HttpServletRequest request,HttpServletResponse response) {
		return true;
	}
	
	@RequestMapping(value = "/deletePermission/{id}/{permissionIds}", method = RequestMethod.GET)
	public boolean clearRoleByUserIds(@PathVariable("id")int id,@PathVariable("permissionIds")String permissionIds,HttpServletRequest request,HttpServletResponse response) {
		return true;
	}

}
