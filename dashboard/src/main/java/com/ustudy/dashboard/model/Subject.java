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

	
	public Subject() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Subject(String courseName) {
		super();
		this.courseName = courseName;
	}


	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}


	@Override
	public String toString() {
		return "Subject [subject = " + courseName + "]";
	}

	
}
