package com.ustudy.exam.model.analysis;

import java.io.Serializable;
import java.util.Map;

public class QuesObjScoreAnaly implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3012298228524325871L;

	private String quesno = null;
	private float fullScore = 0;
	private float aveScore = 0;
	private float levelOfDiff = 0;
	
	// scoring average
	private String scor7age = null;
	private Map<String, Float> choices = null;
	
	public QuesObjScoreAnaly() {
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

	public String getScor7age() {
		return scor7age;
	}

	public void setScor7age(String scor7age) {
		this.scor7age = scor7age;
	}

	public Map<String, Float> getChoices() {
		return choices;
	}

	public void setChoices(Map<String, Float> choices) {
		this.choices = choices;
	}

	@Override
	public String toString() {
		return "QuesScoreAnaly [quesno=" + quesno + ", fullScore=" + fullScore + ", aveScore=" + aveScore
				+ ", levelOfDiff=" + levelOfDiff + ", scor7age=" + scor7age + ", choices=" + choices + "]";
	}
	
}
