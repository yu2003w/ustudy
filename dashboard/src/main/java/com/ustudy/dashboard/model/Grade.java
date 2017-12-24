package com.ustudy.dashboard.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public boolean equals(Grade g) {
		// compare meta info firstly
		if (this.id != g.id || this.gradeName.compareTo(g.getGradeName()) != 0 || this.num != g.getNum())
			return false;
		
		// compare subjects
		List<Subject> oSub = g.getSubjects();
		if ((oSub == null && this.subjects != null) || (oSub != null && this.subjects == null) || 
				(oSub != null && this.subjects != null && (oSub.size() != this.subjects.size()))) {
			return false;
		}
		else if (oSub == null && this.subjects == null) {
			return true;
		}
		
		// origin grade and updated grade has same number of subjects
		Map<String, Subject> sMap = new HashMap<String, Subject>();
		if (oSub != null) {
			for (Subject s: oSub) {
				sMap.put(s.getCourseName(), s);
			}
		}
		
		for (Subject s: this.subjects) {
			if (!sMap.containsKey(s.getCourseName())) {
				return true;
			}
		}
		
		return true;
	}
	
}
