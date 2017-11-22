package com.ustudy.exam.model;

import java.io.Serializable;

public class QuesMeta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6854089374216014088L;

	private String quesid = null;
	private String assignmode = "平均";
	
	public QuesMeta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuesMeta(String quesid, String assignmode) {
		super();
		this.quesid = quesid;
		this.assignmode = assignmode;
	}

	@Override
	public String toString() {
		return "QuesMeta [quesid=" + quesid + ", assignmode=" + assignmode + "]";
	}
	
}
