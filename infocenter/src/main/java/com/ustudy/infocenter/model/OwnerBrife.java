package com.ustudy.infocenter.model;

import java.io.Serializable;

public class OwnerBrife implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5618530837696678513L;

	private String loginname = null;
	private String name = null;
	private String role = null;
	
	public OwnerBrife() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OwnerBrife(String loginname, String name, String role) {
		super();
		this.loginname = loginname;
		this.name = name;
		this.role = role;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "OwnerBrife [loginname=" + loginname + ", name=" + name + ", role=" + role + "]";
	}	
	
}
