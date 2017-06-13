package com.ustudy.dashboard.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Grade {

	@JsonProperty("grade")
	private String name = null;
	private List<Subject> subjects = null;
	@JsonProperty("numOfClasses")
	private int num = 0;
	
	public Grade(String name, List<Subject> cs) {
		this.name = name;
		this.subjects = cs;
		this.setNum(cs.size());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> courses) {
		this.subjects = courses;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}	
	
	
}
