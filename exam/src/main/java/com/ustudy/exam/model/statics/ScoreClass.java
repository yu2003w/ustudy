package com.ustudy.exam.model.statics;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author jared
 * 
 * Class Score object
 *
 */
public class ScoreClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -686311844729437128L;
	
	private float aveScore = 0;
	
	private int rank = 0;
	
	private String clsName = null;
	
	@JsonIgnore
	private int clsId = 0;
	
	private List<ScoreSubjectCls> subScore = null;
	
	@JsonIgnore
	private int examId = 0;

	public ScoreClass() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScoreClass(int aveScore, int rank, String clsName, int clsId) {
		super();
		this.aveScore = aveScore;
		this.rank = rank;
		this.clsName = clsName;
		this.clsId = clsId;
	}

	public ScoreClass(int clsId, int examId) {
		super();
		this.clsId = clsId;
		this.examId = examId;
	}

	public float getAveScore() {
		return aveScore;
	}

	public void setAveScore(float aveScore) {
		this.aveScore = aveScore;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public List<ScoreSubjectCls> getSubScore() {
		return subScore;
	}

	public void setSubScore(List<ScoreSubjectCls> subScore) {
		this.subScore = subScore;
	}

	public String getClsName() {
		return clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
	}

	public int getClsId() {
		return clsId;
	}

	public void setClsId(int clsId) {
		this.clsId = clsId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	@Override
	public String toString() {
		return "ScoreClass [aveScore=" + aveScore + ", rank=" + rank + ", clsName=" + clsName + ", clsId=" + clsId
				+ ", subScore=" + subScore + ", examId=" + examId + "]";
	}

}
