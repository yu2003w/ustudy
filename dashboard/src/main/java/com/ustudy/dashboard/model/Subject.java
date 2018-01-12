package com.ustudy.dashboard.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Subject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -375085025372724305L;
	
	@JsonProperty("subject")
	private String courseName = null;
	
    @JsonProperty("id")
	private String subId = null;

	
	public Subject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Subject(String courseName, String subId) {
		super();
		this.courseName = courseName;
		this.subId = subId;
	}


	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	@Override
	public String toString() {
		return "Subject [courseName=" + courseName + ", subId=" + subId + "]";
	}

	
}