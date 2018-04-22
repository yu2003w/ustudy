package com.ustudy.exam.model.analysis;

import java.io.Serializable;
import java.util.Map;

public class QuesSubScoreAnaly implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 938939937800010344L;
	
	private String quesname = null;
	private float score = 0;
	private float aveScore = 0;
	private float levelOfDiff = 0;
	
	private Map<Integer, String> details = null;

	public QuesSubScoreAnaly() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getQuesname() {
		return quesname;
	}

	public void setQuesname(String quesno) {
		this.quesname = quesno;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float fullScore) {
		this.score = fullScore;
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
		return "QuesSubScoreAnaly [quesno=" + quesname + ", fullScore=" + score + ", aveScore=" + aveScore
				+ ", levelOfDiff=" + levelOfDiff + ", details=" + details + "]";
	}

}
