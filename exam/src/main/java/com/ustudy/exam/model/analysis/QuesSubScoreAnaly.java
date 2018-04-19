package com.ustudy.exam.model.analysis;

import java.io.Serializable;
import java.util.Map;

public class QuesSubScoreAnaly implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 938939937800010344L;
	
	private String quesno = null;
	private float fullScore = 0;
	private float aveScore = 0;
	private float levelOfDiff = 0;
	
	private Map<Integer, String> details = null;

	public QuesSubScoreAnaly() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getQuesno() {
		return quesno;
	}

	public void setQuesno(String quesno) {
		this.quesno = quesno;
	}

	public float getFullScore() {
		return fullScore;
	}

	public void setFullScore(float fullScore) {
		this.fullScore = fullScore;
	}

	public float getAveScore() {
		return aveScore;
	}

	public void setAveScore(float aveScore) {
		this.aveScore = aveScore;
	}

	public float getLevelOfDiff() {
		return levelOfDiff;
	}

	public void setLevelOfDiff(float levelOfDiff) {
		this.levelOfDiff = levelOfDiff;
	}

	public Map<Integer, String> getDetails() {
		return details;
	}

	public void setDetails(Map<Integer, String> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "QuesSubScoreAnaly [quesno=" + quesno + ", fullScore=" + fullScore + ", aveScore=" + aveScore
				+ ", levelOfDiff=" + levelOfDiff + ", details=" + details + "]";
	}

}
