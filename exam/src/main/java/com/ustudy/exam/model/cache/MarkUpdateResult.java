package com.ustudy.exam.model.cache;

import java.io.Serializable;

public class MarkUpdateResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2616516209679771646L;
	
	private String quesid = null;
	private String progress = null;
	private String avgScore = null;
	
	public MarkUpdateResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MarkUpdateResult(String quesid, String progress, String avgScore) {
		super();
		this.quesid = quesid;
		this.progress = progress;
		this.avgScore = avgScore;
	}

	public String getQuesid() {
		return quesid;
	}
	public void setQuesid(String quesid) {
		this.quesid = quesid;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(String avgScore) {
		this.avgScore = avgScore;
	}
	@Override
	public String toString() {
		return "MarkUpdateResult [quesid=" + quesid + ", progress=" + progress + ", avgScore=" + avgScore + "]";
	}

}
