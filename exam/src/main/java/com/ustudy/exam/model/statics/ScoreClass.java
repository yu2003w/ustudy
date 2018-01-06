package com.ustudy.exam.model.statics;

import java.io.Serializable;
import java.util.List;

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
	
	private int clsId = 0;
	
	private List<ScoreSubjectCls> subScore = null;

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

	@Override
	public String toString() {
		return "ScoreClass [aveScore=" + aveScore + ", rank=" + rank + ", clsName=" + clsName + ", clsId=" + clsId
				+ ", subScore=" + subScore + "]";
	}

}
