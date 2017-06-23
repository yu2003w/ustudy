package com.ustudy.dashboard.services;

import java.util.List;

import com.ustudy.dashboard.model.Role;

public interface RoleService {

	public List<Role> list(Role role) ;
	
	public Role findRoleByName(String name) ;
	
	public Role findRoleById(int id) ;
	
	public boolean deleteRoleById(int id) ;
	
	public boolean deleteRoleByIds(String ids) ;
	
	public boolean insertRole(Role role) ;
	
	public boolean updateRole(Role role) ;
	
	public List<Role> getRoleByUserId(int userId) ;
	
	public List<Role> getRoleByUserName(String userName) ;
	
	public boolean addPermissions(int roleId,String permissionId) ;
	
	public boolean deletePermissions(int roleId,String permissionId) ;
	
}
