package com.ustudy.dashboard.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account implements Serializable {

	private static final long serialVersionUID = -5386334949501785419L;
	
	private String id = null;
	
	// Noted: login name is the phone number
	@JsonProperty("userId")
	private String loginname = null;
	
	@JsonProperty("userName")
	private String fullname = null;
	
	@JsonProperty("password")
	private String passwd = null;
	
	@JsonProperty("userType")
	private String userGroup = null;
	
	@JsonIgnore
	private String createTime = null;
	private String lastLoginTime = null;
	
	@JsonProperty("userStatus")
	private String status = null;
	
	private String province = null;
	private String city = null;
	private String district = null;
	
	@JsonIgnore
	private List<Role> roles = null;
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Account(String id, String loginname, String fullname, String passwd, String userGroup, String createTime,
			String lastLoginTime, String status, String province, String city, String district) {
		super();
		this.id = id;
		this.loginname = loginname;
		this.fullname = fullname;
		this.passwd = passwd;
		this.userGroup = userGroup;
		this.createTime = createTime;
		this.lastLoginTime = lastLoginTime;
		this.status = status;
		this.province = province;
		this.city = city;
		this.district = district;
	}

	// constructor without field of password
	public Account(String id, String loginname, String fullname, String userGroup, String createTime,
			String lastLoginTime, String status, String province, String city, String district) {
		super();
		this.id = id;
		this.loginname = loginname;
		this.fullname = fullname;
		this.userGroup = userGroup;
		this.createTime = createTime;
		this.lastLoginTime = lastLoginTime;
		this.status = status;
		this.province = province;
		this.city = city;
		this.district = district;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", loginname=" + loginname + ", fullname=" + fullname + ", passwd=" + passwd
				+ ", userGroup=" + userGroup + ", createTime=" + createTime + ", lastLoginTime=" + lastLoginTime
				+ ", status=" + status + ", province=" + province + ", city=" + city + ", district=" + district
				+ ", roles=" + roles + "]";
	}
	

}

