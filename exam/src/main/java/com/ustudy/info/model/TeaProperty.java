package com.ustudy.info.model;

import java.io.Serializable;
import java.util.List;

public class TeaProperty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6130797006194364826L;

	private List<Item> grades = null;
	private List<Item> subjects = null;
	
	// highest role
	private String role = null;

	public TeaProperty() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeaProperty(List<Item> grades, List<Item> subjects, String role) {
		super();
		this.grades = grades;
		this.subjects = subjects;
		this.role = role;
	}

	public List<Item> getGrades() {
		return grades;
	}

	public void setGrades(List<Item> grades) {
		this.grades = grades;
	}

	public List<Item> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Item> subjects) {
		this.subjects = subjects;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "TeaProperty [grades=" + grades + ", subjects=" + subjects + ", role=" + role + "]";
	}
	
}
