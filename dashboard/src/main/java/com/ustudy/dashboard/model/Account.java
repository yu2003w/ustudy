package com.ustudy.dashboard.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ustudy.dashboard.util.DashboardUtil;

public class Account implements Serializable {

	private static final long serialVersionUID = -5386334949501785419L;
	
	private int id = 0;
	
	// Noted: login name is the phone number
	@JsonProperty("userId")
	private String loginname = null;
	
	@JsonProperty("userName")
	private String fullname = null;
	
	@JsonProperty("password")
	private String passwd = null;
	
	@JsonProperty("userType")
	private String roleName = null;
	
	@JsonProperty("creationTime")
	private String createTime = null;
	
	private String lastLoginTime = null;
	
	@JsonProperty("userStatus")
	private String status = null;

	private String province = null;
	private String city = null;
	private String district = null;
	
	// additional permissions for special purposes
	@JsonIgnore
	private List<String> addiPerms = null;
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account(String loginname, String fullname, String roleName) {
		super();
		this.loginname = loginname;
		this.fullname = fullname;
		this.roleName = roleName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAddiPerms(List<String> addiPerms) {
		this.addiPerms = addiPerms;
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public List<String> getAddiPerms() {
		return addiPerms;
	}

	public void setAdditiPerms(List<String> additiPerms) {
		this.addiPerms = additiPerms;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", loginname=" + loginname + ", fullname=" + fullname + ", passwd=" + passwd
				+ ", roleName=" + roleName + ", createTime=" + createTime + ", lastLoginTime=" + lastLoginTime
				+ ", status=" + status + ", province=" + province + ", city=" + city + ", district=" + district
				+ ", addiPerms=" + addiPerms + "]";
	}
	
	public void convertRole() {
		this.roleName = DashboardUtil.getAcctRoleMap().get(this.roleName);
	}
	
}

