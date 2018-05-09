package com.ustudy.dashboard.model;

import java.io.Serializable;

public class UserBrife implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7290364200828300619L;

	// login name
	private String userId = null;
	// full name
	private String userName = null;
	// role
	private String role = null;
	
	public UserBrife() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserBrife(String userId, String userName, String role) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.role = role;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserBrife [userId=" + userId + ", userName=" + userName + ", role=" + role + "]";
	}
	
}
