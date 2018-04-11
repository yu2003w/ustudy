package com.ustudy.exam.model.score;

import java.io.Serializable;

/**
 * @author jared
 * 
 * When retrieving object score from database, firstly need to catalog them for child subjects if there is
 * child subjects
 *
 */
public class ChildObjScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 811617805170595867L;

	private long eid = 0;
	private String branch = null;
	private float score = 0;
	
	public ChildObjScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getEid() {
		return eid;
	}

	public void setEid(long eid) {
		this.eid = eid;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "StuChildObjScore [eid=" + eid + ", branch=" + branch + ", score=" + score + "]";
	}
	
}
