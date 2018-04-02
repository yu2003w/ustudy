package com.ustudy.exam.model.score;

import java.io.Serializable;

public class DetailedSubScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4655835902259079695L;

	private String subName = null;
	private float score = 0;
	private int rank = 0;
	private float subscore = 0;
	private float objscore = 0;
	
	public DetailedSubScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DetailedSubScore(String subName, float score, int rank, float subscore, float objscore) {
		super();
		this.subName = subName;
		this.score = score;
		this.rank = rank;
		this.subscore = subscore;
		this.objscore = objscore;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public float getSubscore() {
		return subscore;
	}

	public void setSubscore(float subscore) {
		this.subscore = subscore;
	}

	public float getObjscore() {
		return objscore;
	}

	public void setObjscore(float objscore) {
		this.objscore = objscore;
	}

	@Override
	public String toString() {
		return "DetailedSubScore [subName=" + subName + ", score=" + score + ", rank=" + rank + ", subscore=" + subscore
				+ ", objscore=" + objscore + "]";
	}
	
}
