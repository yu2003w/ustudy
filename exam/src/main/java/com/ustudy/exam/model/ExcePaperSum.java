package com.ustudy.exam.model;

import java.io.Serializable;

public class ExcePaperSum implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3406442453199765302L;
	
	private String examName = null;
	
	private String gradeName = null;
	
	private String subName = null;
	
	private int egsId = -1;
	
	private int num = -1;

	public ExcePaperSum() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExcePaperSum(String examName, String gradeName, String subName, int egsId, int num) {
		super();
		this.examName = examName;
		this.gradeName = gradeName;
		this.subName = subName;
		this.egsId = egsId;
		this.num = num;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getEgsId() {
		return egsId;
	}

	public void setEgsId(int egsId) {
		this.egsId = egsId;
	}

	@Override
	public String toString() {
		return "ExcePaperSum [examName=" + examName + ", gradeName=" + gradeName + ", subName=" + subName + ", egsId="
				+ egsId + ", num=" + num + "]";
	}

}
