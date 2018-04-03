package com.ustudy.exam.model.score;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailedSubScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4655835902259079695L;

	@JsonIgnore
	private int egsId = 0;

	private String subName = null;
	private float score = 0;
	private int rank = 0;
	private float subscore = 0;
	private float objscore = 0;
	// mark image for whole subject
	private String markImg = null;
	
	@JsonProperty("subQuesScore")
	private List<SubjectQuesScore> subQuesL = null;
	@JsonProperty("objQuesScore")
	private List<ObjQuesScore> objQuesL = null;
	
	public DetailedSubScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DetailedSubScore(int egsId, String subName, float score, int rank, float subscore, 
			float objscore, String markImg) {
		super();
		this.egsId = egsId;
		this.subName = subName;
		this.score = score;
		this.rank = rank;
		this.subscore = subscore;
		this.objscore = objscore;
		this.markImg = markImg;
	}

	public int getEgsId() {
		return egsId;
	}

	public void setEgsId(int egsId) {
		this.egsId = egsId;
	}

	public String getMarkImg() {
		return markImg;
	}

	public void setMarkImg(String markImg) {
		this.markImg = markImg;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public float getSubscore() {
		return subscore;
	}

	public void setSubscore(float subscore) {
		this.subscore = subscore;
	}

	public float getObjscore() {
		return objscore;
	}

	public void setObjscore(float objscore) {
		this.objscore = objscore;
	}

	public List<SubjectQuesScore> getSubQuesL() {
		return subQuesL;
	}

	public void setSubQuesL(List<SubjectQuesScore> subQuesL) {
		this.subQuesL = subQuesL;
	}

	public List<ObjQuesScore> getObjQuesL() {
		return objQuesL;
	}

	public void setObjQuesL(List<ObjQuesScore> objQuesL) {
		this.objQuesL = objQuesL;
	}

	@Override
	public String toString() {
		return "DetailedSubScore [subName=" + subName + ", score=" + score + ", rank=" + rank + ", subscore=" + subscore
				+ ", objscore=" + objscore + ", markImg=" + markImg + ", subQuesL=" + subQuesL + ", objQuesL="
				+ objQuesL + "]";
	}
	
}
