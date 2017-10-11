package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.Date;

public class Exam implements Serializable {

	private static final long serialVersionUID = -6746378698426998299L;
	
	private int id;
	private String examName = null;
	private Date examDate = null;
	private String type = null;
	
	public Exam() {
		super();
	}

	public Exam(int id, String examName, Date examDate, String type) {
		super();
		this.id = id;
		this.examName = examName;
		this.examDate = examDate;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ExamResult [id=" + id + ", examName=" + examName + ", examDate=" + examDate + ", type=" + type + "]";
	}
	
}
