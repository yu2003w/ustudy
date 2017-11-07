package com.ustudy.exam.model;

import java.io.Serializable;

public class Subject implements Serializable {

	private static final long serialVersionUID = -6746378698426998299L;
	
	private Long id;
	private String name = null;
	private String type = null;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
