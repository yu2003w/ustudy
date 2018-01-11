package com.ustudy.exam.model.statics;

import java.io.Serializable;
import java.util.List;

public class EgsMarkProgress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7608341289276312820L;
	
	private int egsId = 0;
	private int gradeId = 0;
	private String gradeName = null;
	
	private int subId = 0;
	private String subName = null;
	
	private List<QuesMarkMetrics> metrics = null;

	public EgsMarkProgress() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EgsMarkProgress(int egsId, int gradeId, String gradeName, int subId, String subName) {
		super();
		this.egsId = egsId;
		this.gradeId = gradeId;
		this.gradeName = gradeName;
		this.subId = subId;
		this.subName = subName;
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

	public List<QuesMarkMetrics> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<QuesMarkMetrics> metrics) {
		this.metrics = metrics;
	}

	@Override
	public String toString() {
		return "EgsMarkProgress [egsId=" + egsId + ", gradeId=" + gradeId + ", gradeName=" + gradeName + ", subId="
				+ subId + ", subName=" + subName + ", metrics=" + metrics + "]";
	}	

}
