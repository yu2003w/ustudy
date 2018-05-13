package com.ustudy.exam.model.report;

import java.io.Serializable;

/**
 * @author jared
 * 
 * Class for only single egs score to export as excel files
 *
 */
public class SingleEgsScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7298707477022209311L;
	
	private String eeCode = null;
	private String eeName = null;
	private String clsName = null;
	
	private float score = 0;
	private int rank = 0;
	
	public SingleEgsScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SingleEgsScore(String eeCode, String eeName, String clsName, float score, int rank) {
		super();
		this.eeCode = eeCode;
		this.eeName = eeName;
		this.clsName = clsName;
		this.score = score;
		this.rank = rank;
	}

	public String getEeCode() {
		return eeCode;
	}

	public void setEeCode(String eeCode) {
		this.eeCode = eeCode;
	}

	public String getEeName() {
		return eeName;
	}

	public void setEeName(String eeName) {
		this.eeName = eeName;
	}

	public String getClsName() {
		return clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
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

	@Override
	public String toString() {
		return "SingleEgsScore [eeCode=" + eeCode + ", eeName=" + eeName + ", clsName=" + clsName + ", score=" + score
				+ ", rank=" + rank + "]";
	}	

}
