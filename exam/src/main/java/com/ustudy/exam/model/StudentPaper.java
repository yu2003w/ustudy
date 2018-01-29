package com.ustudy.exam.model;

import java.io.Serializable;

public class StudentPaper implements Serializable {

	private static final long serialVersionUID = -1972317428254370128L;
	
	private Long id;
	private String stuNo;
	private String stuExamNo;
	private Long examId;
	private Long gradeId;
	private Long subjectId;
	private Long egsId;
	private int batchNum;
	private String paperImg;
	private String paperStatus;
	private String errorStatus;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStuNo() {
		return stuNo;
	}
	
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	
	public String getStuExamNo() {
		return stuExamNo;
	}
	
	public void setStuExamNo(String stuExamNo) {
		this.stuExamNo = stuExamNo;
	}
	
	public Long getExamId() {
		return examId;
	}
	
	public void setExamId(Long examId) {
		this.examId = examId;
	}
	
	public Long getGradeId() {
		return gradeId;
	}
	
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	
	public Long getSubjectId() {
		return subjectId;
	}
	
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	
	public Long getEgsId() {
		return egsId;
	}
	
	public void setEgsId(Long egsId) {
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

	@Override
	public String toString() {
		return "StudentPaper [id=" + id + ", stuNo=" + stuNo + ", stuExamNo=" + stuExamNo + ", examId=" + examId
				+ ", gradeId=" + gradeId + ", subjectId=" + subjectId + ", egsId=" + egsId + ", batchNum=" + batchNum
				+ ", paperImg=" + paperImg + ", paperStatus=" + paperStatus + ", errorStatus=" + errorStatus + "]";
	}
	
}
