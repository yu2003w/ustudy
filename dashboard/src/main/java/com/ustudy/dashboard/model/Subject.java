package com.ustudy.dashboard.model;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Subject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -375085025372724305L;
	
	@JsonProperty("subject")
	private String subName = null;
	
    @JsonProperty("id")
	private String subId = null;

    // contains child subject information with format {"ids":[7,8,9]}
    @JsonIgnore
    private String child = null;
    
    private Map<String, String> childSubs = null;
    
	public Subject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Subject(String courseName, String subId) {
		super();
		this.subName = courseName;
		this.subId = subId;
	}


	public String getSubName() {
		return subName;
	}

	public void setSubName(String courseName) {
		this.subName = courseName;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public Map<String, String> getChildSubs() {
		return childSubs;
	}

	public void setChildSubs(Map<String, String> childSubs) {
		this.childSubs = childSubs;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	@Override
	public String toString() {
		return "Subject [subName=" + subName + ", subId=" + subId + ", child=" + child + ", childSubs=" + childSubs
				+ "]";
	}

	
}