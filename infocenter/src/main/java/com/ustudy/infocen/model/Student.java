package com.ustudy.infocen.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9068319473746369154L;
	
	// auto generated id
	private String id = null;
	
	@JsonProperty("studentName")
	private String stuName = null;
	
	@JsonProperty("studentId")
	private String stuId = null;
	
	@JsonProperty("grade")
	private String stuGrade = null;
	
	@JsonProperty("class")
	private String stuClass = null;
	
	@JsonProperty("type")
	private String stuCate = null;
	
	@JsonProperty("isTemp")
	private boolean transi = false;
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Student(String id, String stuName, String stuId, String stuGrade, String stuClass,
			String stuCate,	boolean isTransient) {
		super();
		this.id = id;
		this.stuName = stuName;
		this.stuId = stuId;
		this.stuGrade = stuGrade;
		this.stuClass = stuClass;
		this.stuCate = stuCate;
		this.transi = isTransient;
	}

	public String getStuName() {
		return stuName;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public String getStuGrade() {
		return stuGrade;
	}
	public void setStuGrade(String stuGrade) {
		this.stuGrade = stuGrade;
	}
	public String getStuClass() {
		return stuClass;
	}
	public void setStuClass(String stuClass) {
		this.stuClass = stuClass;
	}
	public String getStuCate() {
		return stuCate;
	}
	public void setStuCate(String stuCate) {
		this.stuCate = stuCate;
	}

	public boolean isTransi() {
		return transi;
	}

	public void setTransi(boolean transi) {
		this.transi = transi;
	}


	@Override
	public String toString() {
		return "Student [id=" + id + ", stuName=" + stuName + ", stuId=" + stuId +
			", stuGrade=" + stuGrade + ", stuClass=" + stuClass + ", stuCate=" +
			stuCate + ", isTransient=" + isTransi() + "]";
	}

}
