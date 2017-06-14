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

import com.ustudy.dashboard.model.Permission;
import com.ustudy.dashboard.services.PermissionService;

@RestController
@RequestMapping(value = "/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	@RequiresAuthentication
	@RequiresRoles(value={"admin"})
	@RequiresPermissions("dashboard:view")
	@RequestMapping("/list")
	@Transactional
	public List<Permission> list(Permission permission,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.list(permission);
	}
	
	@RequestMapping("/insert")
	public boolean insert(Permission permission,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.insertPermission(permission);
	}
	
	@RequestMapping("/delete/{id}")
	public boolean delete(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.deletePermissionById(id);
	}
	
	@RequestMapping("/detailByName/{name}")
	public Permission detailByName(@PathVariable("name")String name,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.findPermissionByName(name);
	}
	
	@RequestMapping("/detail/{id}")
	public Permission detailById(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.findPermissionById(id);
	}
	
	@RequestMapping("/update")
	public boolean update(Permission permission,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.updatePermission(permission);
	}

}
