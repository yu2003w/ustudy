package com.ustudy.exam.model;

import java.io.Serializable;

public class MarkImage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7849305044558352623L;

	private Long paperId;
	private Long quesId;
	private Long qareaId;
	private Long pageNo;
	private String paperImg = null;
	private String markImg = null;
	private Long posX;
	private Long posY;
	private Long width;
	private Long height;
	private float score;
		
	public MarkImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MarkImage(Long paperId, Long quesId, Long qareaId, Long pageNo, String paperImg, String markImg, Long posX, Long posY, Long width, Long height, float score) {
		super();
		// TODO Auto-generated constructor stub
		this.paperId = paperId;
		this.quesId = quesId;
		this.qareaId = qareaId;
		this.pageNo = pageNo;
		this.paperImg = paperImg;
		this.markImg = markImg;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.score = score;
	}

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	public Long getQuesId() {
		return quesId;
	}

	public void setQuesId(Long quesId) {
		this.quesId = quesId;
	}

	public Long getQareaId() {
		return qareaId;
	}

	public void setQareaId(Long qareaId) {
		this.qareaId = qareaId;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public String getPaperImg() {
		String[] strArray = this.paperImg.split(",");
		return strArray[this.pageNo.intValue()];
	}

	public void setPaperImg(String paperImg) {
		this.paperImg = paperImg;
	}

	public String getMarkImg() {
		return markImg;
	}

	public void setMarkImg(String markImg) {
		this.markImg = markImg;
	}

	public Long getPosX() {
		return posX;
	}

	public void setPosX(Long posX) {
		this.posX = posX;
	}

	public Long getPosY() {
		return posY;
	}

	public void setPosY(Long posY) {
		this.posY = posY;
	}
	
	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}
	
	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}
	
	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
		
	@Override
	public String toString() {
		return "MarkImage [paperId=" + paperId + ", quesId=" + quesId + ", qareaId=" + qareaId + ", pageNo="
				+ pageNo + ", paperImg=" + paperImg + ", markImg=" + markImg + ", posX=" + posX + ", posY="
				+ posY + ", width=" + width + ", height=" + height + ", score=" + score + "]";
	}
	
}
