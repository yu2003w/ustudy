package com.ustudy.exam.model;

import java.io.Serializable;

public class MarkAnsImg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2018947918329941273L;

	private int regionId = -1;
	private String markImg = null;
	private String ansMarkImg = null;
	
	public MarkAnsImg() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MarkAnsImg(int regionId, String markImg, String ansMarkImg) {
		super();
		this.regionId = regionId;
		this.markImg = markImg;
		this.ansMarkImg = ansMarkImg;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getMarkImg() {
		return markImg;
	}

	public void setMarkImg(String markImg) {
		this.markImg = markImg;
	}

	public String getAnsMarkImg() {
		return ansMarkImg;
	}

	public void setAnsMarkImg(String ansMarkImg) {
		this.ansMarkImg = ansMarkImg;
	}

	@Override
	public String toString() {
		return "MarkAnsImg [regionId=" + regionId + ", markImg=" + markImg + ", ansMarkImg=" + ansMarkImg + "]";
	}
	
}
