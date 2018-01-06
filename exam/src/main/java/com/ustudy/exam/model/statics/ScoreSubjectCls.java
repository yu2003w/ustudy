package com.ustudy.exam.model.statics;

import java.io.Serializable;

public class ScoreSubjectCls implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1369388311080844442L;

	private String subjecName = null;
	
	private float aveScore = 0;
	
	private int rank = 0;
	
	public ScoreSubjectCls() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScoreSubjectCls(String subjecName, float aveScore, int rank) {
		super();
		this.subjecName = subjecName;
		this.aveScore = aveScore;
		this.rank = rank;
	}

	public String getSubjecName() {
		return subjecName;
	}

	public void setSubjecName(String subjecName) {
		this.subjecName = subjecName;
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

	@Override
	public String toString() {
		return "ScoreSubjectCls [subjecName=" + subjecName + ", aveScore=" + aveScore + ", rank=" + rank + "]";
	}
	
}
