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

import com.ustudy.dashboard.model.Role;
import com.ustudy.dashboard.services.RoleService;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/role/list")
	@RequestMapping("/list")
	public List<Role> list(Role role,HttpServletRequest request,HttpServletResponse response) {
		return roleService.list(role);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/role/insert")
	@RequestMapping("/insert")
	public boolean insert(Role role,HttpServletRequest request,HttpServletResponse response) {
		return roleService.insertRole(role);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/role/delete")
	@RequestMapping("/delete/{id}")
	public boolean delete(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return roleService.deleteRoleById(id);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/role/detail")
	@RequestMapping("/detailByName/{name}")
	public Role detailByName(@PathVariable("name")String name,HttpServletRequest request,HttpServletResponse response) {
		return roleService.findRoleByName(name);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/role/detail")
	@RequestMapping("/detail/{id}")
	public Role detailById(@PathVariable("id")int id,HttpServletRequest request,HttpServletResponse response) {
		return roleService.findRoleById(id);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/role/update")
	@RequestMapping("/update")
	public boolean update(Role role,HttpServletRequest request,HttpServletResponse response) {
		return roleService.updateRole(role);
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/role/addPermission")
	@RequestMapping("/addPermission2Role/{id}/{permissionId}")
	public boolean addRole2User(@PathVariable("id")int id,@PathVariable("permissionId")int permissionId,HttpServletRequest request,HttpServletResponse response) {
		return true;
	}
	
	@RequiresAuthentication
	@RequiresRoles(value={"888888"})
	@RequiresPermissions("/role/deletePermission")
	@RequestMapping("/deletePermission/{id}/{permissionIds}")
	public boolean clearRoleByUserIds(@PathVariable("id")int id,@PathVariable("permissionIds")String permissionIds,HttpServletRequest request,HttpServletResponse response) {
		return true;
	}


}
