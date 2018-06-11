package com.ustudy.exam.model.analysis;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class QuesSubScoreAnaly implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 938939937800010344L;
	
	private String quesname = null;
	private float score = 0;
	private float aveScore = 0;
	//discrimination index
	private float dist7tion = 0;
	
	@JsonIgnore
	private String aggrscore = null;
	
	private Map<Float, Integer> details = null;

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

	public Map<Float, Integer> getDetails() {
		return details;
	}

	public void setDetails(Map<Float, Integer> details) {
		this.details = details;
	}

	public float getDist7tion() {
		return dist7tion;
	}

	public void setDist7tion(float dist7tion) {
		this.dist7tion = dist7tion;
	}

	public String getAggrscore() {
		return aggrscore;
	}

	public void setAggrscore(String aggrscore) {
		this.aggrscore = aggrscore;
		if (this.aggrscore != null && this.aggrscore.length() > 0) {
			String []data = this.aggrscore.split(",");
			if (data != null && data.length >  0) {
				for (String item: data) {
					String [] pair = item.split("-");
					if (pair != null && pair.length == 2) {
						if (this.details == null) 
							this.details = new HashMap<Float, Integer>();
						this.details.put(Float.valueOf(pair[0]), Integer.valueOf(pair[1]));
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		return "QuesSubScoreAnaly [quesname=" + quesname + ", score=" + score + ", aveScore=" + aveScore
				+ ", dist7tion=" + dist7tion + ", aggrscore=" + aggrscore + ", details=" + details + "]";
	}

}
