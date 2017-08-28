package com.ustudy.infocenter.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Grade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5358347528570598398L;

	private String id = null;
	
	@JsonProperty("gradeName")
	private String name = null;
	
	// true means distinct between art and science, false means no distinct
	@JsonProperty("gradeType")
	private boolean type = false;
	
	@JsonProperty("gradeOwner")
	private TeacherBrife gradeO = null;
	
	@JsonProperty("subjects")
	private List<SubjectTeac> subs = null;
	
	private String classNum = null;
	
	@JsonProperty("classes")
	private List<ClassInfo> cInfo = null;

	public Grade() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Grade(String id, String name, boolean type, TeacherBrife gradeO, List<SubjectTeac> subs, String classNum,
			List<ClassInfo> cInfo) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.gradeO = gradeO;
		this.subs = subs;
		this.classNum = classNum;
		this.cInfo = cInfo;
	}

	public Grade(String id, String name, TeacherBrife gradeO, String classNum) {
		super();
		this.id = id;
		this.name = name;
		this.gradeO = gradeO;
		this.classNum = classNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public TeacherBrife getGradeO() {
		return gradeO;
	}

	public void setGradeO(TeacherBrife gradeO) {
		this.gradeO = gradeO;
	}

	public List<SubjectTeac> getSubs() {
		return subs;
	}

	public void setSubs(List<SubjectTeac> subs) {
		this.subs = subs;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public List<ClassInfo> getcInfo() {
		return cInfo;
	}

	public void setcInfo(List<ClassInfo> cInfo) {
		this.cInfo = cInfo;
	}

	public boolean isHigh() {
		if (this.name.compareTo("高一") == 0 ||
			this.name.compareTo("高二") == 0 ||
			this.name.startsWith("高三"))
			return true;
		return false;	
	}
	
	public boolean isJunior() {
		if (this.name.compareTo("七年级") == 0 ||
			this.name.compareTo("八年级") == 0 ||
			this.name.startsWith("九年级"))
			return true;
		return false;	
	}
	
	public boolean isPrimary() {
		if (this.name.compareTo("一年级") == 0 ||
			this.name.compareTo("二年级") == 0 ||
			this.name.compareTo("三年级") == 0 ||
			this.name.compareTo("四年级") == 0 ||
			this.name.compareTo("五年级") == 0 ||
			this.name.startsWith("六年级"))
			return true;
		return false;	
	}
	
	@Override
	public String toString() {
		return "Department [name=" + name + ", type=" + type + ", gradeO=" + gradeO + ", subs=" + subs + ", classNum="
				+ classNum + ", cInfo=" + cInfo + "]";
	}
	
}
