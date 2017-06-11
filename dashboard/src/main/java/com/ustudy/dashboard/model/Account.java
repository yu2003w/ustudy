package com.ustudy.dashboard.model;

import java.io.Serializable;

public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5386334949501785419L;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
