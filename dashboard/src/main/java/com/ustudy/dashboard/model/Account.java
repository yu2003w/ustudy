package com.ustudy.dashboard.model;

import java.io.Serializable;

public class Account implements Serializable {

	private static final long serialVersionUID = -5386334949501785419L;
	
	private String id;
	private String name = null;
	private String loginname = null;
	private String phone = null;
	private String pswd = null;
	private String createTime = null;
	private String lastLoginTime = null;
	private String userGroup = null;
	private String status = null;
	
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account(String id, String name, String loginname, String phone, String pswd, String createTime,
			String lastLoginTime, String userGroup, String status) {
		super();
		this.id = id;
		this.name = name;
		this.loginname = loginname;
		this.phone = phone;
		this.pswd = pswd;
		this.createTime = createTime;
		this.lastLoginTime = lastLoginTime;
		this.userGroup = userGroup;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", loginname=" + loginname + ", pswd=" + pswd + ", phone="
				+ phone + ", createTime=" + createTime + ", lastLoginTime=" + lastLoginTime + ", userGroup=" + userGroup
				+ ", status=" + status + "]";
	}
	
}