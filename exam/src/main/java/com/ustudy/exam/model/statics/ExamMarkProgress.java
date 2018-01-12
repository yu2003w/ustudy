package com.ustudy.exam.model.statics;

import java.io.Serializable;
import java.util.List;

public class ExamMarkProgress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2152649918868691953L;
	
	private int examId = 0;
	private String examName = null;
	
	private int schoolId = 0;
	private String schoolName = null;
	
	private List<EgsMarkProgress> egs = null;

	public ExamMarkProgress() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public ExamMarkProgress(int examId, String examName, int schoolId, String schoolName) {
		super();
		this.examId = examId;
		this.examName = examName;
		this.schoolId = schoolId;
		this.schoolName = schoolName;
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

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public List<EgsMarkProgress> getEgs() {
		return egs;
	}

	public void setEgs(List<EgsMarkProgress> egs) {
		this.egs = egs;
	}

	@Override
	public String toString() {
		return "ExamMarkProgress [examId=" + examId + ", examName=" + examName + ", schoolId=" + schoolId
				+ ", schoolName=" + schoolName + ", egs=" + egs + "]";
	}

}
