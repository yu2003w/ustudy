package com.ustudy.exam.model;

import java.io.Serializable;

public class QuesId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4494451623011951333L;
	
	private String id = null;

	public QuesId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuesId(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "QuesId [id=" + id + "]";
	}

}
