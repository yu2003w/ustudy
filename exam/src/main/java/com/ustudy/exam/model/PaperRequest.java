package com.ustudy.exam.model;

import java.io.Serializable;

public class PaperRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7131360495022080218L;
	
	private String qid = null;
	private String assmode = null;
	private String markmode = null;
	private float scorediff = 0;
	
	// parameters to retrieve already marked papers
	private int startSeq = -1;
	private int endSeq = -1;
	
	public PaperRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaperRequest(String qid, String assmode, String markmode, float scorediff, int startSeq, int endSeq) {
		super();
		this.qid = qid;
		this.assmode = assmode;
		this.markmode = markmode;
		this.scorediff = scorediff;
		this.startSeq = startSeq;
		this.endSeq = endSeq;
	}


	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getAssmode() {
		return assmode;
	}

	public void setAssmode(String assmode) {
		this.assmode = assmode;
	}

	public String getMarkmode() {
		return markmode;
	}

	public void setMarkmode(String markmode) {
		this.markmode = markmode;
	}

	public int getStartSeq() {
		return startSeq;
	}

	public void setStartSeq(int startSeq) {
		this.startSeq = startSeq;
	}

	public int getEndSeq() {
		return endSeq;
	}

	public void setEndSeq(int endSeq) {
		this.endSeq = endSeq;
	}

	public float getScorediff() {
		return scorediff;
	}

	public void setScorediff(float scorediff) {
		this.scorediff = scorediff;
	}

	@Override
	public String toString() {
		return "PaperRequest [qid=" + qid + ", assmode=" + assmode + ", markmode=" + markmode + ", scorediff="
				+ scorediff + ", startSeq=" + startSeq + ", endSeq=" + endSeq + "]";
	}

}
