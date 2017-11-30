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
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	private String teacid = null;
	
	/*
	 * 0 -- unassigned
	 * 1 -- marking
	 * 2 -- marked
	 */
	private int status = 0;
	
	public MarkTaskCache() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MarkTaskCache(String paperid, String img) {
		super();
		this.paperid = paperid;
		this.img = img;
	}

	public MarkTaskCache(String paperid, String teacid, int status) {
		super();
		this.paperid = paperid;
		this.teacid = teacid;
		this.status = status;
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

	@Override
	public String toString() {
		return "MarkTaskCache [paperid=" + paperid + ", img=" + img + ", teacid=" + teacid + ", status=" + status + "]";
	}

}
