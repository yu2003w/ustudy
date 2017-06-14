package com.ustudy.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
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
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/permission/list")
	@RequestMapping("/list")
	public List<Permission> list(Permission permission,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.list(permission);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/permission/insert")
	@RequestMapping("/insert")
	public boolean insert(Permission permission,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.insertPermission(permission);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/permission/delete")
	@RequestMapping("/delete/{id}")
	public boolean delete(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.deletePermissionById(id);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/permission/detail")
	@RequestMapping("/detailByName/{name}")
	public Permission detailByName(@PathVariable("name")String name,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.findPermissionByName(name);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/permission/detail")
	@RequestMapping("/detail/{id}")
	public Permission detailById(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.findPermissionById(id);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/permission/update")
	@RequestMapping("/update")
	public boolean update(Permission permission,HttpServletRequest request,HttpServletResponse response) {
		return permissionService.updatePermission(permission);
	}

}
