package com.ustudy.dashboard.services;

import java.util.List;

import com.ustudy.dashboard.model.Account;

public interface AccountService {

	public List<Account> getList(int id) ;
	
	public Account findUserByLoginName(String login) ;
	
	public Account findUserById(int id) ;
	
	public int delItem(int id) ;
	
	public int delItemSet(String ids) ;
	
	public int createAccount(Account item);
	
	public int updateAccount(Account item, int id);
	
	public boolean updateLLTime(int id);
	
}