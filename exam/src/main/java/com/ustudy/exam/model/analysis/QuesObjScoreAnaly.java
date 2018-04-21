package com.ustudy.exam.model.analysis;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class QuesObjScoreAnaly implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3012298228524325871L;

	private String quesno = null;
	private float score = 0;
	private float aveScore = 0;
	private float levelOfDiff = 0;
	@JsonIgnore
	private int total = 0;
	
	// format is answer, '-', count
	@JsonIgnore
	private String opts = null;
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

	public String getOpts() {
		return opts;
	}

	public void setOpts(String opts) {
		this.opts = opts;
		if (opts != null && !opts.isEmpty()) {
			String []data = opts.split(",");
			if (data != null && data.length > 0) {
				for (String para : data) {
					String [] paL = para.split("-");
					if (paL != null && paL.length == 2) {
						if (this.choices == null) {
							this.choices = new HashMap<String, Float>();
						}
						if (paL[0] != null && paL.length > 0 && this.total != 0)
							choices.put(paL[0], Float.valueOf(paL[1])/this.total);
					}
				}
			}
		}
		
	}

	@Override
	public String toString() {
		return "QuesScoreAnaly [quesno=" + quesno + ", fullScore=" + score + ", aveScore=" + aveScore
				+ ", levelOfDiff=" + levelOfDiff + ", scor7age=" + scor7age + ", choices=" + choices + "]";
	}
	
}
