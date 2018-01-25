package com.ustudy.mmadapter.model;

import java.io.Serializable;

public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 444537846674164352L;

	private String examNo = null;
	
	private String stuNo = null;
	
	private String schName = null;
	
	private String stuName = null;

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(String examNo, String stuNo, String schName, String stuName) {
		super();
		this.examNo = examNo;
		this.stuNo = stuNo;
		this.schName = schName;
		this.stuName = stuName;
	}

	public String getExamNo() {
		return examNo;
	}

	public void setExamNo(String examNo) {
		this.examNo = examNo;
	}

	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	public String getSchName() {
		return schName;
	}

	public void setSchName(String schName) {
		this.schName = schName;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	@Override
	public String toString() {
		return "Student [examNo=" + examNo + ", stuNo=" + stuNo + ", schName=" + schName + ", stuName=" + stuName + "]";
	}
	
}
