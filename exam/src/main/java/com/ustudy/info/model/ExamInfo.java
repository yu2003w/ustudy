package com.ustudy.info.model;

import java.io.Serializable;
import java.util.List;

public class ExamInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6579974261478931647L;

	private int id = -1;
	private String examName = null;
	private String examDate = null;
	private String type = "校考";
	private String status = null;
	private List<String> schIds = null;
	
	private List<GradeSubs> grades = null;

	public ExamInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamInfo(int id, String examName, String examDate, String type, List<String> schIds,
			List<GradeSubs> grades) {
		super();
		this.id = id;
		this.examName = examName;
		this.examDate = examDate;
		this.type = type;
		this.schIds = schIds;
		this.grades = grades;
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

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getSchIds() {
		return schIds;
	}

	public void setSchIds(List<String> schIds) {
		this.schIds = schIds;
	}

	public List<GradeSubs> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeSubs> grades) {
		this.grades = grades;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ExamInfo [id=" + id + ", examName=" + examName + ", examDate=" + examDate + ", type=" + type
				+ ", status=" + status + ", schIds=" + schIds + ", grades=" + grades + "]";
	}
	
}
