package com.ustudy.infocenter.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3286058535369633296L;

	private String id = null;
	
	private String className = null;
	
	private String classType = null;
	
	@JsonProperty("classOwner")
	private TeacherBrife claOwner = null;
	
	@JsonProperty("subjects")
	private List<SubjectTeac> subs = null;

	public ClassInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassInfo(String id, String className, String classType, TeacherBrife claOwner, List<SubjectTeac> subs) {
		super();
		this.id = id;
		this.className = className;
		this.classType = classType;
		this.claOwner = claOwner;
		this.subs = subs;
	}

	
	public ClassInfo(String id, String className, String classType, String teacid, String teacname) {
		super();
		this.id = id;
		this.className = className;
		this.classType = classType;
		this.claOwner = new TeacherBrife(teacid, teacname);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public TeacherBrife getClaOwner() {
		return claOwner;
	}

	public void setClaOwner(TeacherBrife claOwner) {
		this.claOwner = claOwner;
	}

	public List<SubjectTeac> getSubs() {
		return subs;
	}

	public void setSubs(List<SubjectTeac> subs) {
		this.subs = subs;
	}

	@Override
	public String toString() {
		return "ClassInfo [id=" + id + ", className=" + className + ", classType=" + classType + ", claOwner="
				+ claOwner + ", subs=" + subs + "]";
	}
	
}