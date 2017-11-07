package com.ustudy.exam.model;

import java.io.Serializable;

public class Quesarea implements Serializable {

	private static final long serialVersionUID = 6563687105648046566L;
	
	private Long id ;
	private int pageno ;
	private String fileName ;
	private int areaId ;
	private int posx ;
	private int posy ;
	private int width ;
	private int height ;
	private int bottom ;
	private int right ;
	private String questionType ;
	private int startQuestionNo ;
	private int endQuestionNo ;
	private int quesid ;
	private String answer ;
	
	public Quesarea(){}
	
	public Quesarea(int pageno, String fileName, int areaId, int posx, int posy, int width, int height, int bottom, int right, String questionType, int startQuestionNo, int endQuestionNo){
		this.pageno = pageno;
		this.fileName = fileName;
		this.areaId = areaId;
		this.posx = posx;
		this.posy = posy;
		this.width = width;
		this.height = height;
		this.bottom = bottom;
		this.right = right;
		this.questionType = questionType;
		this.startQuestionNo = startQuestionNo;
		this.endQuestionNo = endQuestionNo;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getQuesid() {
		return quesid;
	}
	
	public void setQuesid(int quesid) {
		this.quesid = quesid;
	}
	
	public int getPageno() {
		return pageno;
	}
	
	public void setPageno(int pageno) {
		this.pageno = pageno;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getAreaId() {
		return areaId;
	}
	
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	
	public int getPosx() {
		return posx;
	}
	
	public void setPosx(int posx) {
		this.posx = posx;
	}
	
	public int getPosy() {
		return posy;
	}
	
	public void setPosy(int posy) {
		this.posy = posy;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getBottom() {
		return bottom;
	}
	
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}
	
	public int getRight() {
		return right;
	}
	
	public void setRight(int right) {
		this.right = right;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getQuestionType() {
		return questionType;
	}
	
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	
	public int getStartQuestionNo() {
		return startQuestionNo;
	}
	
	public void setStartQuestionNo(int startQuestionNo) {
		this.startQuestionNo = startQuestionNo;
	}
	
	public int getEndQuestionNo() {
		return endQuestionNo;
	}
	
	public void setEndQuestionNo(int endQuestionNo) {
		this.endQuestionNo = endQuestionNo;
	}
	
}
