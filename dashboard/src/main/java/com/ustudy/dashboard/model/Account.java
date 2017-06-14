package com.ustudy.dashboard.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account implements Serializable {

	private static final long serialVersionUID = -5386334949501785419L;
	
	private int id;
	private String name = null;
	private String loginname = null;
	private String pswd = null;
	private String email = null;
	private String phone = null;
	private String createTime = null;
	private String lastLoginTime = null;
	private int status = 1;
	
	public Account() {}
	
	public Account(String name, String loginname, String pswd,String email,String phone,int status) {
		this.name = name;
		this.loginname = loginname;
		this.pswd = pswd;
		this.email = email;
		this.phone = phone;
		this.status = status;
	}
	
	public Account(int id, String name, String loginname, String pswd,String email,String phone,String createTime,String lastLoginTime,int status) {
		this.id = id;
		this.name = name;
		this.loginname = loginname;
		this.pswd = pswd;
		this.email = email;
		this.phone = phone;
		this.createTime = createTime;
		this.lastLoginTime = lastLoginTime;
		this.status = status;
	}
	
	public Account(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.name = rs.getString("name");
		this.loginname = rs.getString("login_name");
		this.pswd = rs.getString("pswd");
		this.email = rs.getString("email");
		this.phone = rs.getString("phone");
		this.createTime = rs.getString("create_time");
		this.lastLoginTime = rs.getString("last_login_time");
		this.status = rs.getInt("status");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}