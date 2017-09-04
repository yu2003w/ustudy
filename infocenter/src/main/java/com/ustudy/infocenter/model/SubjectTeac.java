package com.ustudy.infocenter.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubjectTeac implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4764186251051462382L;

	@JsonProperty("subject")
	private String sub = null;
	
	@JsonProperty("teacher")
	private TeacherBrife teac = null;

	public SubjectTeac() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectTeac(String sub, TeacherBrife planner) {
		super();
		this.sub = sub;
		this.teac = planner;
	}
	
	public SubjectTeac(String sub, String teacid, String teacname) {
		super();
		this.sub = sub;
		this.teac = new TeacherBrife(teacid, teacname);
	}
	
	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public TeacherBrife getTeac() {
		return teac;
	}

	public void setTeac(TeacherBrife teac) {
		this.teac = teac;
	}

	@Override
	public String toString() {
		return "SubjectTeac [sub=" + sub + ", teac=" + teac + "]";
	}
	
}
