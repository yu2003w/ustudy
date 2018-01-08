package com.ustudy.exam.model.statics;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ScoreSubjectCls implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1369388311080844442L;

	private String subjectName = null;
	
	private float aveScore = 0;
	
	@JsonIgnore
	private int clsId = 0;
	
	@JsonIgnore
	private int egsId = 0;
	
	private int rank = 0;
	
	public ScoreSubjectCls() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSubjecName() {
		return subjectName;
	}

	public void setSubjecName(String subjecName) {
		this.subjectName = subjecName;
	}

	public float getAveScore() {
		return aveScore;
	}

	public void setAveScore(float aveScore) {
		this.aveScore = aveScore;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getClsId() {
		return clsId;
	}

	public void setClsId(int clsId) {
		this.clsId = clsId;
	}

	public int getEgsId() {
		return egsId;
	}

	public void setEgsId(int egsId) {
		this.egsId = egsId;
	}

	@Override
	public String toString() {
		return "ScoreSubjectCls [subjecName=" + subjectName + ", aveScore=" + aveScore + ", clsId=" + clsId + ", egsId="
				+ egsId + ", rank=" + rank + "]";
	}
	
}
