package com.ustudy.
dashboard.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrgOwner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6779993496312183819L;

	private long id = 0;
	
	@JsonProperty("userName")
	private String name = null;
	
	@JsonProperty("userId")
	private String loginname = null;
	
	@JsonProperty("password")
	private String passwd = null;
	
	@JsonProperty("orgType")
	private String orgType = null;
	
	@JsonProperty("orgId")
	private String orgId = null;
	
	@JsonProperty("role")
	private String role = null;
	
	@JsonProperty("creationTime")
	private String createTime = null;
	
	public OrgOwner() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrgOwner(long id, String name, String loginname, String passwd, String orgType, String orgId, String role,
			String createTime) {
		super();
		this.id = id;
		this.name = name;
		this.loginname = loginname;
		this.passwd = passwd;
		this.orgType = orgType;
		this.orgId = orgId;
		this.role = role;
		this.createTime = createTime;
	}

	// construct without the field of password
	public OrgOwner(long id, String name, String loginname, String orgType, String orgId, String role,
			String createTime) {
		super();
		this.id = id;
		this.name = name;
		this.loginname = loginname;
		this.orgType = orgType;
		this.orgId = orgId;
		this.role = role;
		this.createTime = createTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "OrgOwner [id=" + id + ", name=" + name + ", loginname=" + loginname + ", passwd=" + passwd
				+ ", orgType=" + orgType + ", orgId=" + orgId + ", role=" + role + ", createTime=" + createTime + "]";
	}

	public Map<String, String> compare(OrgOwner item) {
		if (item == this)
			return null;
		HashMap<String, String> ret = new HashMap<String, String>();
		if (this.getName().compareTo(item.getName()) != 0)
			ret.put("name", this.getName());
		if (this.getLoginname().compareTo(item.getLoginname()) != 0) 
			ret.put("loginname", this.getLoginname());
		if (this.getPasswd().compareTo(item.getPasswd()) != 0)
			ret.put("passwd", this.getPasswd());
		if (this.getOrgType().compareTo(item.getOrgType()) != 0)
			ret.put("orgtype", this.getOrgType());
		if (this.getRole().compareTo(item.getRole()) != 0)
			ret.put("role", this.getRole());
		if (this.getOrgId().compareTo(item.getOrgId()) != 0)
			ret.put("orgid", this.getOrgId());
		return ret;
	}
	
}
