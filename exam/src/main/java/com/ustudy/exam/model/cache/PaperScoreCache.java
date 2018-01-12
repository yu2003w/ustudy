package com.ustudy.exam.model.cache;

import java.io.Serializable;

public class PaperScoreCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4878136675316730121L;
	
	private String paperid = null;
	
	private float score = 0;
	
	private String teacid = null;

	public PaperScoreCache() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaperScoreCache(String paperid, float score, String teacid) {
		super();
		this.paperid = paperid;
		this.score = score;
		this.teacid = teacid;
	}

	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getTeacid() {
		return teacid;
	}

	public void setTeacid(String teacid) {
		this.teacid = teacid;
	}

	@Override
	public String toString() {
		return "PaperScoreCache [paperid=" + paperid + ", score=" + score + ", teacid=" + teacid + "]";
	}	

}
