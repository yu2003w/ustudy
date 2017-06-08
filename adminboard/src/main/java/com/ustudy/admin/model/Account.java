package com.ustudy.admin.model;

public class Account {
	private String username = null;
	private String password = null;
	
	public Account(String name) {
		username = name;
	}
	
	public String getUserName() {
		return username;
	}
	
	public void setUserName(String name) {
		username = name;
	}

}
