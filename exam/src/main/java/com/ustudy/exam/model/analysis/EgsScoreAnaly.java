package com.ustudy.exam.model.analysis;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EgsScoreAnaly implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5705340768071152294L;
	
	private int exCount = 0;
	private float maxScore = 0;
	private float minScore = 0;
	private float aveScore = 0;
	private float midScore = 0;
	private int passCount = 0;
	private float levelOfDiff = 0;
	// discrimination
	private float dis7tion = 0;
	// standard deviation
	private float stdDevia = 0;
	
	@JsonIgnore
	private String aggrscore = null;
	
	// score placement, need to sort by keys, so use TreeMap, although TreeMap has some performance limitations
	@JsonProperty("scoreplacement")
	private TreeMap<Float, Integer> splace = null;
	
	public EgsScoreAnaly() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EgsScoreAnaly(int exCount, float maxScore, float minScore, float aveScore, float midScore, int passCount,
			float levelOfDiff, float dis7tion, float stdDevia) {
		super();
		this.exCount = exCount;
		this.maxScore = maxScore;
		this.minScore = minScore;
		this.aveScore = aveScore;
		this.midScore = midScore;
		this.passCount = passCount;
		this.levelOfDiff = levelOfDiff;
		this.dis7tion = dis7tion;
		this.stdDevia = stdDevia;
	}

	public int getExCount() {
		return exCount;
	}

	public void setExCount(int exCount) {
		this.exCount = exCount;
	}

	public float getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(float maxScore) {
		this.maxScore = maxScore;
	}

	public float getMinScore() {
		return minScore;
	}

	public void setMinScore(float minScore) {
		this.minScore = minScore;
	}

	public float getAveScore() {
		return aveScore;
	}

	public void setAveScore(float aveScore) {
		this.aveScore = aveScore;
	}

	public float getMidScore() {
		return midScore;
	}

	public void setMidScore(float midScore) {
		this.midScore = midScore;
	}

	public int getPassCount() {
		return passCount;
	}

	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}

	public float getLevelOfDiff() {
		return levelOfDiff;
	}

	public void setLevelOfDiff(float levelOfDiff) {
		this.levelOfDiff = levelOfDiff;
	}

	public float getDis7tion() {
		return dis7tion;
	}

	public void setDis7tion(float dis7tion) {
		this.dis7tion = dis7tion;
	}

	public float getStdDevia() {
		return stdDevia;
	}

	public void setStdDevia(float stdDevia) {
		this.stdDevia = stdDevia;
	}

	public String getAggrscore() {
		return aggrscore;
	}

	public void setAggrscore(String aggrscore) {
		this.aggrscore = aggrscore;
		if (aggrscore != null && aggrscore.length() > 0) {
			String []places = this.aggrscore.split(",");
			if (places != null && places.length > 0) {
				for (String pl : places) {
					String []datas = pl.split("-");
					if (datas != null && datas.length == 2) {
						if (this.splace == null) {
							this.splace = new TreeMap<Float, Integer>();
						}
						this.splace.put(Float.valueOf(datas[1]), Integer.valueOf(datas[0]));
					}
				}
			}
		}
	}

	public Map<Float, Integer> getSplace() {
		return splace;
	}

	public void setSplace(TreeMap<Float, Integer> splace) {
		this.splace = splace;
	}

	@Override
	public String toString() {
		return "EgsScoreAnaly [exCount=" + exCount + ", maxScore=" + maxScore + ", minScore=" + minScore + ", aveScore="
				+ aveScore + ", midScore=" + midScore + ", passCount=" + passCount + ", levelOfDiff=" + levelOfDiff
				+ ", dis7tion=" + dis7tion + ", stdDevia=" + stdDevia + ", aggrscore=" + aggrscore + ", splace="
				+ splace + "]";
	}
	
}
