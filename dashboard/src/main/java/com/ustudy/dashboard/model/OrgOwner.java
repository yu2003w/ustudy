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

	private String id = null;
	
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
	
	private String province = null;
	
	private String city = null;
	
	private String district = null;
	
	@JsonProperty("creationTime")
	private String createTime = null;
	
	private String lastLoginTime = null;

	@JsonProperty("status")
	private String status = null;
	
	public OrgOwner() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OrgOwner(String id, String name, String loginname, String passwd, String orgType, String orgId,
			String province, String city, String district, String createTime, String lastLoginTime, String status) {
		super();
		this.id = id;
		this.name = name;
		this.loginname = loginname;
		this.passwd = passwd;
		this.orgType = orgType;
		this.orgId = orgId;
		this.province = province;
		this.city = city;
		this.district = district;
		this.createTime = createTime;
		this.lastLoginTime = lastLoginTime;
		this.status = status;
	}

	// construct without the field of password
	public OrgOwner(String id, String name, String loginname, String orgType, String orgId, String province,
			String city, String district, String createTime, String lastLoginTime, String status) {
		super();
		this.id = id;
		this.name = name;
		this.loginname = loginname;
		this.orgType = orgType;
		this.orgId = orgId;
		this.province = province;
		this.city = city;
		this.district = district;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "OrgOwner [id=" + id + ", loginname=" + loginname + ", name=" + name + ", passwd=" + passwd
				+ ", orgType=" + orgType + ", orgId=" + orgId + ", status=" + status + ", province=" + province
				+ ", city=" + city + ", district=" + district + ", createTime=" + createTime + ", lastLoginTime="
				+ lastLoginTime + "]";
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
		if (this.getOrgId().compareTo(item.getOrgId()) != 0)
			ret.put("orgid", this.getOrgId());
		if (this.getProvince().compareTo(item.getProvince()) != 0)
			ret.put("province", this.getProvince());
		if (this.getCity().compareTo(item.getCity()) != 0)
			ret.put("city", this.getCity());
		if (this.getDistrict().compareTo(item.getDistrict()) != 0)
			ret.put("district", this.getDistrict());
		return ret;
	}
	
}
