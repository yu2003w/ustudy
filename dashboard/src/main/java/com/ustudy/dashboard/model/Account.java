package com.ustudy.dashboard.model;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {

	private static final long serialVersionUID = -5386334949501785419L;
	
	private String id = null;
	private String loginname = null;
	private String fullname = null;
	private String phone = null;
	private String passwd = null;
	private String userGroup = null;
	private String createTime = null;
	private String lastLoginTime = null;
	private String status = null;
	private List<Role> roles = null;
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Account(String id, String loginname, String fullname, String phone, String pswd, String userGroup,
			String createTime, String lastLoginTime, String status) {
		super();
		this.id = id;
		this.loginname = loginname;
		this.fullname = fullname;
		this.phone = phone;
		this.passwd = pswd;
		this.userGroup = userGroup;
		this.createTime = createTime;
		this.lastLoginTime = lastLoginTime;
		this.status = status;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", loginname=" + loginname + ", fullname=" + fullname + ", phone=" + phone
				+ ", pswd=" + passwd + ", userGroup=" + userGroup + ", createTime=" + createTime + ", lastLoginTime="
				+ lastLoginTime + ", status=" + status + "]";
	}

}