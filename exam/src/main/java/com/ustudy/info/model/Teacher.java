package com.ustudy.info.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Teacher implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6133372584686820580L;

	private int id = -1;

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

	private List<UElem> roles = null;
	private List<UElem> subjects = null;
	private List<UElem> grades = null;
	private List<UElem> classes = null;
	private List<UElem> addiPerms = null;

	@JsonIgnore
	private String orgtype = null;
	
	@JsonIgnore
	private String orgid = null;
	
	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Teacher(int id, String teacId, String teacName, String passwd, String cTime, String llTime,
			List<UElem> roles, List<UElem> subjects, List<UElem> grades, List<UElem> classes, List<UElem> addiPerms) {
		super();
		this.id = id;
		this.teacId = teacId;
		this.teacName = teacName;
		this.passwd = passwd;
		this.cTime = cTime;
		this.llTime = llTime;
		this.roles = roles;
		this.subjects = subjects;
		this.grades = grades;
		this.classes = classes;
		this.addiPerms = addiPerms;
	}

	public Teacher(int id, String teacId, String teacName, String passwd, String cTime, String llTime,
			String orgtype, String orgid) {
		super();
		this.id = id;
		this.teacId = teacId;
		this.teacName = teacName;
		this.passwd = passwd;
		this.cTime = cTime;
		this.llTime = llTime;
		this.orgtype = orgtype;
		this.orgid = orgid;
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

	public List<UElem> getRoles() {
		return roles;
	}

	public void setRoles(List<UElem> roles) {
		this.roles = roles;
	}

	public List<UElem> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<UElem> subjects) {
		this.subjects = subjects;
	}

	public List<UElem> getGrades() {
		return grades;
	}

	public void setGrades(List<UElem> grades) {
		this.grades = grades;
	}

	public List<UElem> getClasses() {
		return classes;
	}

	public void setClasses(List<UElem> classes) {
		this.classes = classes;
	}

	public List<UElem> getAddiPerms() {
		return addiPerms;
	}

	public void setAddiPerms(List<UElem> addiPerms) {
		this.addiPerms = addiPerms;
	}

	public String getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", teacId=" + teacId + ", teacName=" + teacName + ", passwd=" + passwd + ", cTime="
				+ cTime + ", llTime=" + llTime + ", roles=" + roles + ", subjects=" + subjects + ", grades=" + grades
				+ ", classes=" + classes + ", addiPerms=" + addiPerms + ", orgtype=" + orgtype + ", orgid=" + orgid
				+ "]";
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
