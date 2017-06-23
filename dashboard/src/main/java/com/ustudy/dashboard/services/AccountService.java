package com.ustudy.dashboard.services;

import java.util.List;

import com.ustudy.dashboard.model.Account;

public interface AccountService {

	public List<Account> list(Account account, String startTime, String endTime) ;
	
	public Account findUserByLoginName(String username) ;
	
	public Account findUserById(int id) ;
	
	public boolean deleteUserById(int id) ;
	
	public boolean deleteUserByIds(String ids) ;
	
	public boolean insertUser(Account account) ;
	
	public boolean updateUser(Account account) ;
	
	public boolean addRoles(int userId,String roleId) ;
	
	public boolean deleteRoles(int userId,String roleId) ;
	
}