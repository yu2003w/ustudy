package com.ustudy.exam.model.statics;

import java.io.Serializable;

public class EgsMeta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8401924375760055089L;

	private int examId = 0;
	private String examName = null;
	
	private String schoolId = null;
	private String schoolName = null;
	
	private int egsId = 0;
	private int gradeId = 0;
	private String gradeName = null;
	
	private int subId = 0;
	private String subName = null;
	
	public EgsMeta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getEgsId() {
		return egsId;
	}

	public void setEgsId(int egsId) {
		this.egsId = egsId;
	}

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

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	@Override
	public String toString() {
		return "EgsMeta [examId=" + examId + ", examName=" + examName + ", schoolId=" + schoolId + ", schoolName="
				+ schoolName + ", egsId=" + egsId + ", gradeId=" + gradeId + ", gradeName=" + gradeName + ", subId="
				+ subId + ", subName=" + subName + "]";
	}
	
}
