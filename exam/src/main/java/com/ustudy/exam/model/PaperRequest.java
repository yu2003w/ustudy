package com.ustudy.exam.model;

import java.io.Serializable;

public class PaperRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7131360495022080218L;
	
	private String qid = null;
	private String assmode = null;
	
	public PaperRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaperRequest(String qid, String assmode) {
		super();
		this.qid = qid;
		this.assmode = assmode;
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

	@Override
	public String toString() {
		return "PaperRequest [qid=" + qid + ", assmode=" + assmode + "]";
	}

}
