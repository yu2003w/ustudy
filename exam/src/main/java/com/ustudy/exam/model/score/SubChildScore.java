package com.ustudy.exam.model.score;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SubChildScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6590093362364537127L;
	
	@JsonIgnore
	private long subId = 0;
	private String subName = null;
	private float score = 0;
	private int rank = 0;
	
	public SubChildScore() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SubChildScore(long subId, String subName, float score, int rank) {
		super();
		this.subId = subId;
		this.subName = subName;
		this.score = score;
		this.rank = rank;
	}

	public SubChildScore(String subName, float score, int rank) {
		super();
		this.subName = subName;
		this.score = score;
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public float getScore() {
		return score;
	}
	
	public void setScore(float score) {
		this.score = score;
	}
	
	public long getSubId() {
		return subId;
	}
	
	public void setSubId(long subId) {
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
		return "SubChildScore [subId=" + subId + ", subName=" + subName + ", score=" + score + ", rank=" + rank + "]";
	}

}
