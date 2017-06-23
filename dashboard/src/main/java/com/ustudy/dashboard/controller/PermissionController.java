package com.ustudy.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.dashboard.model.Permission;
import com.ustudy.dashboard.services.PermissionService;

@RestController
@RequestMapping(value = "/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<Permission> list(Permission permission,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.list(permission);
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public boolean insert(Permission permission,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.insertPermission(permission);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.deletePermissionById(id);
	}
	
	@RequestMapping(value = "/detailByName/{name}", method = RequestMethod.GET)
	public Permission detailByName(@PathVariable("name")String name,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.findPermissionByName(name);
	}
	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public Permission detailById(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.findPermissionById(id);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public boolean update(Permission permission,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.updatePermission(permission);
	}

}
