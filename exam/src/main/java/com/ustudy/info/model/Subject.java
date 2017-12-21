package com.ustudy.info.model;

import java.io.Serializable;

public class Subject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 440554790707109172L;
	
	private String subId = null;
	
	private String subName = null;

	public Subject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Subject(String subId, String subName) {
		super();
		this.subId = subId;
		this.subName = subName;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	@Override
	public String toString() {
		return "Subject [subId=" + subId + ", subName=" + subName + "]";
	}

}
