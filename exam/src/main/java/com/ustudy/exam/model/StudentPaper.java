package com.ustudy.exam.model;

import java.io.Serializable;

public class StudentPaper implements Serializable {

	private static final long serialVersionUID = -1972317428254370128L;
	
	private int id;
	private int stuNo;
	private int stuExamNo;
	private int examId;
	private int gradeId;
	private int subjectId;
	private int egsId;
	private int batchNum;
	private String paperImg;
	private String paperStatus;
	private String errorStatus;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getStuNo() {
		return stuNo;
	}
	
	public void setStuNo(int stuNo) {
		this.stuNo = stuNo;
	}
	
	public int getStuExamNo() {
		return stuExamNo;
	}
	
	public void setStuExamNo(int stuExamNo) {
		this.stuExamNo = stuExamNo;
	}
	
	public int getExamId() {
		return examId;
	}
	
	public void setExamId(int examId) {
		this.examId = examId;
	}
	
	public int getGradeId() {
		return gradeId;
	}
	
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	
	public int getSubjectId() {
		return subjectId;
	}
	
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	
	public int getEgsId() {
		return egsId;
	}
	
	public void setEgsId(int egsId) {
		this.egsId = egsId;
	}
	
	public int getBatchNum() {
		return batchNum;
	}
	
	public void setBatchNum(int batchNum) {
		this.batchNum = batchNum;
	}
	
	public String getPaperImg() {
		return paperImg;
	}
	
	public void setPaperImg(String paperImg) {
		this.paperImg = paperImg;
	}
	
	public String getPaperStatus() {
		return paperStatus;
	}
	
	public void setPaperStatus(String paperStatus) {
		this.paperStatus = paperStatus;
	}
	
	public String getErrorStatus() {
		return errorStatus;
	}
	
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}
	
}
