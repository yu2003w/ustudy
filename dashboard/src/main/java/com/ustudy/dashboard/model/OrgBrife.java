package com.ustudy.dashboard.model;

import java.io.Serializable;

public class OrgBrife implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2062982494666671084L;

	private String orgType = null;
	private String orgName = null;
	private String orgId = null;
	
	public OrgBrife(String orgType, String orgName, String orgId) {
		super();
		this.orgType = orgType;
		this.orgName = orgName;
		this.orgId = orgId;
	}

	public OrgBrife() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Override
	public String toString() {
		return "OrgBrife [orgType=" + orgType + ", orgName=" + orgName + ", orgId=" + orgId + "]";
	}
	
}
