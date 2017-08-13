package com.ustudy.infocen.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Teacher implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6133372584686820580L;

	private String id = null;
	
	@JsonProperty("teacherId")
	private String teacId = null;
	
	@JsonProperty("teacherName")
	private String teacName = null;
	
	@JsonProperty("password")
	private String passwd = null;
	
	@JsonProperty("creationTime")
	private String cTime = null;
	
	@JsonProperty("lastLoginTime")
	private String llTime = null;

	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Teacher(String id, String teacId, String teacName, String passwd, String cTime,
			String llTime) {
		super();
		this.id = id;
		this.teacId = teacId;
		this.teacName = teacName;
		this.passwd = passwd;
		this.cTime = cTime;
		this.llTime = llTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getcTime() {
		return cTime;
	}

	public void setcTime(String cTime) {
		this.cTime = cTime;
	}

	public String getLlTime() {
		return llTime;
	}

	public void setLlTime(String llTime) {
		this.llTime = llTime;
	}

	@Override
	public String toString() {
		return "teacher [id=" + id + ", teacId=" + teacId + ", teacName=" + teacName + ", passwd=" + passwd
				+ ", cTime=" + cTime + ", llTime=" + llTime + "]";
	}
	
	public Map<String, String> compare(Teacher item) {
		if (item == this)
			return null;
		HashMap<String, String> ret = new HashMap<String, String>();
		if (this.getTeacId().compareTo(item.getTeacId()) != 0)
			ret.put("teacid", this.getTeacId());
		if (this.getTeacName().compareTo(item.getTeacName()) != 0) 
			ret.put("loginname", this.getTeacName());
		if (this.getPasswd().compareTo(item.getPasswd()) != 0)
			ret.put("passwd", this.getPasswd());
		return ret;
	}
	
}
