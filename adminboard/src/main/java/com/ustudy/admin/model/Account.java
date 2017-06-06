package com.ustudy.admin.model;

public class Account {
	private String username = null;
	
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
