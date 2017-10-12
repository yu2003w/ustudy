package com.ustudy.exam.model;

import java.io.Serializable;

public class ExamGrade implements Serializable {

	private static final long serialVersionUID = -3726305286199495819L;
	
	private int gradeId;
	private String gradeName = null;
	private String gradeClassName = null;
	
	public int getGradeId() {
		return gradeId;
	}
	
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	
	public String getGradeName() {
		return gradeName;
	}
	
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	public String getGradeClassName() {
		return gradeClassName;
	}
	
	public void setGradeClassName(String gradeClassName) {
		this.gradeClassName = gradeClassName;
	}
	
}
