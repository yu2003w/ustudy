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

	public TeacRole() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacRole(String teacName, String priRole) {
		super();
		this.teacName = teacName;
		this.priRole = priRole;
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

	@Override
	public String toString() {
		return "TeacRole [teacName=" + teacName + ", priRole=" + priRole + "]";
	}
	
}
