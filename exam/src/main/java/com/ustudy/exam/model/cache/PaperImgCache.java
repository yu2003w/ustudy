package com.ustudy.exam.model.cache;

import java.io.Serializable;

public class PaperImgCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4173741982271005028L;
	
	private String paperid = null;
	
	private String img = null;
	
	// for final check, need to get score and teacid of first marks
	private float score = -1;
	private String teacid = null;

	public PaperImgCache() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaperImgCache(String paperid, String img) {
		super();
		this.paperid = paperid;
		this.img = img;
	}

	// for final marks, only score and teacid needed
	public PaperImgCache(String paperid, float score, String teacid) {
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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
		return "PaperImgCache [paperid=" + paperid + ", img=" + img + ", score=" + score + ", teacid=" + teacid + "]";
	}

}
