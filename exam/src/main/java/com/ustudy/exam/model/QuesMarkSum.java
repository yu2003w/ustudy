package com.ustudy.exam.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author jared
 *
 * Brife summary for question mark result
 */
public class QuesMarkSum implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6303860000664776641L;

	private String questionName = null;
	private String questionType = null;
	private String avgScore = "";
	private String markedNum = null;
	private String quesid = null;
	
	private String progress = null;
	
	/*
	 *  for question has no subs, if score not less than 20, mark this field as true
	 *  for question has subs, if score of one sub not less than 20, mark this field as true
	 *  This field determines whether this question could be composed with others for marking
	 */
	private boolean composable = false;
	
	@JsonIgnore
	private String quesno = null;
	@JsonIgnore
	private String startno = null;
	@JsonIgnore
	private String endno = null;
	@JsonIgnore
	private String assignMode = null;
	@JsonIgnore
	private String markMode = null;
	@JsonIgnore
	private String fullscore = "";
	
	public QuesMarkSum() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public QuesMarkSum(String questionName, String questionType, String avgScore, String markedNum, String quesid) {
		super();
		this.questionName = questionName;
		this.questionType = questionType;
		this.avgScore = avgScore;
		this.markedNum = markedNum;
		this.quesid = quesid;
	}

	public QuesMarkSum(String quesid, String quesno, String startno, String endno, String questionType, 
			String assign, String markMode, String score) {
		super();
		this.questionType = questionType;
		this.quesid = quesid;
		this.quesno = quesno;
		this.startno = startno;
		this.endno = endno;
		this.markMode = markMode;
		this.assignMode = assign;
		this.fullscore = score;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(String avgScore) {
		this.avgScore = avgScore;
	}

	public String getMarkedNum() {
		return markedNum;
	}

	public void setMarkedNum(String markedNum) {
		this.markedNum = markedNum;
	}
	
	public String getQuesid() {
		return quesid;
	}

	public void setQuesid(String quesid) {
		this.quesid = quesid;
	}

	public String getQuesno() {
		return quesno;
	}

	public void setQuesno(String quesno) {
		this.quesno = quesno;
	}

	public String getStartno() {
		return startno;
	}

	public void setStartno(String startno) {
		this.startno = startno;
	}

	public String getEndno() {
		return endno;
	}

	public void setEndno(String endno) {
		this.endno = endno;
	}

	public String getAssignMode() {
		return assignMode;
	}

	public void setAssignMode(String assignMode) {
		this.assignMode = assignMode;
	}

	public String getMarkMode() {
		return markMode;
	}

	public void setMarkMode(String markMode) {
		this.markMode = markMode;
	}

	public String getFullscore() {
		return fullscore;
	}

	public void setFullscore(String fullscore) {
		this.fullscore = fullscore;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public boolean isComposable() {
		return composable;
	}

	public void setComposable(boolean highScore) {
		this.composable = highScore;
	}

	@Override
	public String toString() {
		return "QuesMarkSum [questionName=" + questionName + ", questionType=" + questionType + ", avgScore=" + avgScore
				+ ", markedNum=" + markedNum + ", quesid=" + quesid + ", progress=" + progress + ", composable="
				+ composable + ", quesno=" + quesno + ", startno=" + startno + ", endno=" + endno + ", assignMode="
				+ assignMode + ", markMode=" + markMode + ", fullscore=" + fullscore + "]";
	}
	
}
