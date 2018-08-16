package com.ustudy.exam.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ustudy.exam.model.score.SubChildScore;

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
	private List<SubChildScore> childScores = null;
	
	@JsonIgnore
	private String child = null;
	
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

	public List<SubChildScore> getChildScores() {
		return childScores;
	}

	public void setChildScores(List<SubChildScore> childScores) {
		this.childScores = childScores;
	}

	public String getChild() {
		return child;
	}

	// data with format as below, 
	// 物理-66.0-1,化学-42.0-28
	public void setChild(String child) {
		this.child = child;
		if (child != null && child.length() > 0) {
			this.childScores = new ArrayList<SubChildScore>();
			String[] paras = child.split(",");
			for (String data : paras) {
				String[] items = data.split("-");
				if (items != null && items.length == 3) {
					childScores.add(new SubChildScore(items[0], Float.valueOf(items[1]), 
							Integer.valueOf(items[2])));
				}
			}
		}
	}

	@Override
	public String toString() {
		return "SingleEgsScore [eeCode=" + eeCode + ", eeName=" + eeName + ", clsName=" + clsName + ", score=" + score
				+ ", rank=" + rank + ", childScores=" + childScores + ", child=" + child + "]";
	}

}
