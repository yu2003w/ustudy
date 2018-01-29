package com.ustudy.exam.model.cache;

import java.io.Serializable;

/**
 * @author jared
 * MarkTask stored in cache
 *
 */
public class MarkTaskCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -541260350542180987L;
	
	private String paperid = null;
	// paper image corresponding to the paper id
	private String img = null;
	
	private String teacid = null;
	
	/*
	 * 0 -- unassigned
	 * 1 -- marking
	 * 2 -- marked
	 */
	private int status = 0;
	
	// default value -1 indicates not marked yet
	private float score = -1;
	
	// index of papers in cache should be finally marked
	private int seq = -1;
	
	public MarkTaskCache() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MarkTaskCache(String paperid, String img) {
		super();
		this.paperid = paperid;
		this.img = img;
	}

	public MarkTaskCache(String paperid, String img, String teacid, int status, float score) {
		super();
		this.paperid = paperid;
		this.img = img;
		this.teacid = teacid;
		this.status = status;
		this.score = score;
	}

	public MarkTaskCache(String paperid, String img, String teacid, int status, float score, int seq) {
		super();
		this.paperid = paperid;
		this.img = img;
		this.teacid = teacid;
		this.status = status;
		this.score = score;
		this.seq = seq;
	}

	public MarkTaskCache(String paperid, String teacid, int status) {
		super();
		this.paperid = paperid;
		this.teacid = teacid;
		this.status = status;
	}

	public MarkTaskCache(MarkTaskCache mt) {
		this.paperid = mt.getPaperid();
		this.teacid = mt.getTeacid();
		this.status = mt.getStatus();
		this.score = mt.getScore();
		this.img = mt.getImg();
		this.seq = mt.getSeq();
	}
	
	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String answerid) {
		this.paperid = answerid;
	}

	public String getTeacid() {
		return teacid;
	}

	public void setTeacid(String teacid) {
		this.teacid = teacid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "MarkTaskCache [paperid=" + paperid + ", img=" + img + ", teacid=" + teacid + ", status=" + status
				+ ", score=" + score + ", seq=" + seq + "]";
	}

}
