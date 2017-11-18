package com.ustudy.exam.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeacRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2343859386761478650L;

	@JsonProperty("userName")
	private String teacName = null;
	
	@JsonProperty("role")
	private String priRole = null;
	
	private String orgType = null;
	private String orgId = null;
	

	public TeacRole() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacRole(String teacName, String priRole, String orgType, String orgId) {
		super();
		this.teacName = teacName;
		this.priRole = priRole;
		this.orgType = orgType;
		this.orgId = orgId;
	}

	public String getTeacName() {
		return teacName;
	}

	public void setTeacName(String teacName) {
		this.teacName = teacName;
	}

	public String getPriRole() {
		return priRole;
	}

	public void setPriRole(String priRole) {
		this.priRole = priRole;
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

	@Override
	public String toString() {
		return "TeacRole [teacName=" + teacName + ", priRole=" + priRole + ", orgType=" + orgType + ", orgId=" + orgId
				+ "]";
	}
	
}
