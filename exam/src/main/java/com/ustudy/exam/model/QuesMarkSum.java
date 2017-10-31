package com.ustudy.exam.model;

import java.io.Serializable;


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
	private float avgScore = 0;
	private int markedNum = 0;
	
	public QuesMarkSum() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuesMarkSum(String questionName, String questionType, float avgScore, int markedNum) {
		super();
		this.questionName = questionName;
		this.questionType = questionType;
		this.avgScore = avgScore;
		this.markedNum = markedNum;
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

	public float getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(float avgScore) {
		this.avgScore = avgScore;
	}

	public int getMarkedNum() {
		return markedNum;
	}

	public void setMarkedNum(int markedNum) {
		this.markedNum = markedNum;
	}

	@Override
	public String toString() {
		return "QuesMarkSum [questionName=" + questionName + ", questionType=" + questionType + ", avgScore=" + avgScore
				+ ", markedNum=" + markedNum + "]";
	}
	
}
