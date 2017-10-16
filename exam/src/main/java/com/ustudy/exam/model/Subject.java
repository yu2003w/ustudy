package com.ustudy.exam.model;

import java.io.Serializable;

public class Subject implements Serializable {

	private static final long serialVersionUID = -6746378698426998299L;
	
	private int id;
	private String name = null;
	private String type = null;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
