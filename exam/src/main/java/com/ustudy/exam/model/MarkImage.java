package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
		
	public MarkImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MarkImage(Long paperId, Long quesId, Long qareaId, Long pageNo, String paperImg, String markImg, Long posX, Long posY) {
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
	
	
	@Override
	public String toString() {
		return "MarkImage [paperId=" + paperId + ", quesId=" + quesId + ", qareaId=" + qareaId + ", pageNo="
				+ pageNo + ", paperImg=" + paperImg + ", markImg=" + markImg + ", posX=" + posX + ", posY="
				+ posY + "]";
	}
	
}
