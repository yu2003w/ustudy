package com.ustudy.dashboard.services;

import java.util.List;

import com.ustudy.dashboard.model.Permission;

public interface PermissionService {

	public List<Permission> list(Permission permission) ;
	
	public Permission findPermissionByName(String name) ;
	
	public Permission findPermissionById(int id) ;
	
	public boolean deletePermissionById(int id) ;
	
	public boolean deletePermissionByIds(String ids) ;
	
	public boolean insertPermission(Permission permission) ;
	
	public boolean updatePermission(Permission permission) ;
	
	public List<Permission> getPermissionByUserId(int userId) ;
	
	public List<Permission> getPermissionByUserName(String userName) ;
	
}
