package com.ustudy.exam.model;

import java.io.Serializable;

/**
 * @author jared
 * 
 * This class is to construct basic information for scoring task.
 * The client needs more information based on the meta info.
 *
 */
public class MetaScoreTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7849305044558352622L;
	
	private String id = null;
	private String teacid = null;
	private String quesid = null;
	private int numOfScoredPapers = 0;
	private String scoreType = null;
	
	public MetaScoreTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MetaScoreTask(String id, String teacid, String quesid, int numOfScoredPapers, String scoreType) {
		super();
		this.id = id;
		this.teacid = teacid;
		this.quesid = quesid;
		this.numOfScoredPapers = numOfScoredPapers;
		this.scoreType = scoreType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeacid() {
		return teacid;
	}

	public void setTeacid(String teacid) {
		this.teacid = teacid;
	}

	public String getQuesid() {
		return quesid;
	}

	public void setQuesid(String quesid) {
		this.quesid = quesid;
	}

	public int getNumOfScoredPapers() {
		return numOfScoredPapers;
	}

	public void setNumOfScoredPapers(int numOfScoredPapers) {
		this.numOfScoredPapers = numOfScoredPapers;
	}

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	@Override
	public String toString() {
		return "MetaScoreTask [id=" + id + ", teacid=" + teacid + ", quesid=" + quesid + ", numOfScoredPapers="
				+ numOfScoredPapers + ", scoreType=" + scoreType + "]";
	}

}
