package com.ustudy.exam.model.cache;

import java.io.Serializable;

public class PaperImgCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4173741982271005028L;
	
	private String paperid = null;
	
	private String img = null;

	public PaperImgCache() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaperImgCache(String paperid, String img) {
		super();
		this.paperid = paperid;
		this.img = img;
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

	@Override
	public String toString() {
		return "PaperImgCache [paperid=" + paperid + ", img=" + img + "]";
	}

}
