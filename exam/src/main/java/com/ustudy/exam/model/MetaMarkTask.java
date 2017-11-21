package com.ustudy.exam.model;

import java.io.Serializable;

/**
 * @author jared
 * 
 * This class is to construct basic information for scoring task.
 * The client needs more information based on the meta info.
 *
 */
public class MetaMarkTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7849305044558352622L;
	
	private String id = null;
	private String teacid = null;
	private String quesid = null;
	private int threshold = 0;
	private String markType = null;
	private String markrole = null;
	private int numOfMarkedPapers = 0;
	
	public MetaMarkTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MetaMarkTask(String id, String teacid, String quesid, int numOfScoredPapers, String scoreType) {
		super();
		this.id = id;
		this.teacid = teacid;
		this.quesid = quesid;
		this.numOfMarkedPapers = numOfScoredPapers;
		this.markType = scoreType;
		this.markrole = "初评";
		this.threshold = 0;
	}

	public MetaMarkTask(String teacid, String quesid, int threshold, String scoreType, String markrole) {
		super();
		this.teacid = teacid;
		this.quesid = quesid;
		this.threshold = threshold;
		this.markType = scoreType;
		this.markrole = markrole;
		this.numOfMarkedPapers = 0;
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

	public int getNumOfMarkedPapers() {
		return numOfMarkedPapers;
	}

	public void setNumOfMarkedPapers(int numOfScoredPapers) {
		this.numOfMarkedPapers = numOfScoredPapers;
	}
	
	public String getMarkType() {
		return markType;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public String getMarkrole() {
		return markrole;
	}

	public void setMarkrole(String markrole) {
		this.markrole = markrole;
	}

	@Override
	public String toString() {
		return "MetaMarkTask [id=" + id + ", teacid=" + teacid + ", quesid=" + quesid + ", threshold=" + threshold
				+ ", markType=" + markType + ", markrole=" + markrole + ", numOfMarkedPapers=" + numOfMarkedPapers
				+ "]";
	}

}
