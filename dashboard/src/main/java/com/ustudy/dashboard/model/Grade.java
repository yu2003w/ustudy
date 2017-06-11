package com.ustudy.dashboard.model;

import java.io.Serializable;

import com.ustudy.dashboard.util.JsonAssembler;

public class Grade implements Serializable {
	
	private String name = null;
	private String [] courses = null;
	
	public Grade(String name, String[] courses) {
		super();
		this.name = name;
		this.courses = courses;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getCourses() {
		return courses;
	}
	public void setCourses(String[] courses) {
		this.courses = courses;
	}
	
	@Override
	public String toString() {
		String res = "\"name\":\"" + name + "\", \"courses\":" +
			JsonAssembler.arrayToJson("subject", courses) + 
			",\"numberOfCourses\":" + String.valueOf(courses.length);
		return res;
	}
	
}
