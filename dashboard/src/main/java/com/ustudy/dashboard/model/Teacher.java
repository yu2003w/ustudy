package com.ustudy.dashboard.model;

import java.io.Serializable;

public class Teacher implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8384160649426123525L;
	
	private int id = -1;
	
	private String teacId = null;
	
	private String teacName = null;
	
	private String passwd = null;
	
	private String createTime = null;
	
	private String llTime = null;
	
	private String orgType = null;
	
	private String orgId = null;

	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Teacher(int id, String teacId, String teacName, String passwd, String createTime, String llTime,
			String orgType, String orgId) {
		super();
		this.id = id;
		this.teacId = teacId;
		this.teacName = teacName;
		this.passwd = passwd;
		this.createTime = createTime;
		this.llTime = llTime;
		this.orgType = orgType;
		this.orgId = orgId;
	}

	public Teacher(String teacId, String teacName, String orgType, String orgId) {
		super();
		this.teacId = teacId;
		this.teacName = teacName;
		this.orgType = orgType;
		this.orgId = orgId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTeacId() {
		return teacId;
	}

	public void setTeacId(String teacId) {
		this.teacId = teacId;
	}

	public String getTeacName() {
		return teacName;
	}

	public void setTeacName(String teacName) {
		this.teacName = teacName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLlTime() {
		return llTime;
	}

	public void setLlTime(String llTime) {
		this.llTime = llTime;
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
		return "Teacher [id=" + id + ", teacId=" + teacId + ", teacName=" + teacName + ", passwd=" + passwd
				+ ", createTime=" + createTime + ", llTime=" + llTime + ", orgType=" + orgType + ", orgId=" + orgId
				+ "]";
	}

}
