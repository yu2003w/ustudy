package com.ustudy.info.model;

import java.io.Serializable;

public class ExamineeSub implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3630816372446732430L;
	
	private long id = 0;
	private long eeid = 0;
	private long subid = 0;
	
	public ExamineeSub() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamineeSub(long eeid, long subid) {
		super();
		this.eeid = eeid;
		this.subid = subid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEeid() {
		return eeid;
	}

	public void setEeid(long eeid) {
		this.eeid = eeid;
	}

	public long getSubid() {
		return subid;
	}

	public void setSubid(long subid) {
		this.subid = subid;
	}

	@Override
	public String toString() {
		return "ExamineeSub [id=" + id + ", eeid=" + eeid + ", subid=" + subid + "]";
	}

}
