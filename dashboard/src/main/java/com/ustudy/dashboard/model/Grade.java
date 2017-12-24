package com.ustudy.dashboard.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Grade implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3092091154717959822L;
	
	private int id = -1;
	
	@JsonProperty("grade")
	private String gradeName = null;
	
	private List<Subject> subjects = null;
	
	@JsonProperty("numOfClasses")
	private int num = 0;
	
	public Grade() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Grade(int id, String gradeName, int num) {
		super();
		this.id = id;
		this.gradeName = gradeName;
		this.num = num;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
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

	@Override
	public String toString() {
		return "Grade [id=" + id + ", gradeName=" + gradeName + ", subjects=" + subjects + ", num=" + num + "]";
	}
	
}
