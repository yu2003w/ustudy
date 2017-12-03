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
	
	public PaperRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaperRequest(String qid, String assmode, String markmode) {
		super();
		this.qid = qid;
		this.assmode = assmode;
		this.markmode = markmode;
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

	@Override
	public String toString() {
		return "PaperRequest [qid=" + qid + ", assmode=" + assmode + ", markmode=" + markmode + "]";
	}

}
