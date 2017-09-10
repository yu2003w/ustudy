package com.ustudy.infocenter.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author jared
 * This class only includes information of teacher id and teacher name.
 */
public class TeacherBrife implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2662606860245557360L;

	@JsonProperty("id")
	private String teacid = null;
	
	@JsonProperty("n")
	private String teacname = null;

	public TeacherBrife(String teacid, String teacname) {
		super();
		this.teacid = teacid;
		this.teacname = teacname;
	}

	public TeacherBrife() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTeacid() {
		return teacid;
	}

	public void setTeacid(String teacid) {
		this.teacid = teacid;
	}

	public String getTeacname() {
		return teacname;
	}

	public void setTeacname(String teacname) {
		this.teacname = teacname;
	}

	@Override
	public String toString() {
		return "TeacherBrife [teacid=" + teacid + ", teacname=" + teacname + "]";
	}
	
}
