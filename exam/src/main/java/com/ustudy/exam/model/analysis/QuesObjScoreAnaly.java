package com.ustudy.exam.model.analysis;

import java.io.Serializable;
import java.text.DecimalFormat;
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
	//discrimination index
	private float dist7tion = 0;
	@JsonIgnore
	private int total = 0;
	
	// format is answer, '-', count
	@JsonIgnore
	private String opts = null;
	
	// reference answer
	private String refa = null;
	
	// scoring average
	private String scor7age = null;
	
	private Map<String, String> choices = null;
	
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

	public float getDist7tion() {
		return dist7tion;
	}

	public void setDist7tion(float dist7tion) {
		this.dist7tion = dist7tion;
	}

	public String getScor7age() {
		return scor7age;
	}

	public void setScor7age(String scor7age) {
		this.scor7age = scor7age;
	}

	public Map<String, String> getChoices() {
		return choices;
	}

	public void setChoices(Map<String, String> choices) {
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
				DecimalFormat df = new DecimalFormat("##0.0%");
				for (String para : data) {
					String [] paL = para.split("-");
					if (paL != null && paL.length == 2) {
						if (this.choices == null) {
							this.choices = new HashMap<String, String>();
						}
						if (paL[0] != null && paL[0].length() > 0 && this.total != 0)
							choices.put(paL[0], df.format(Float.valueOf(paL[1])/this.total));
					}
				}
			}
		}
		
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getRefa() {
		return refa;
	}

	public void setRefa(String refa) {
		this.refa = refa;
	}

	@Override
	public String toString() {
		return "QuesObjScoreAnaly [quesno=" + quesno + ", score=" + score + ", aveScore=" + aveScore + ", dist7tion="
				+ dist7tion + ", total=" + total + ", opts=" + opts + ", refa=" + refa + ", scor7age=" + scor7age
				+ ", choices=" + choices + "]";
	}
	
}
