package com.ustudy.info.model;

import java.io.Serializable;
import java.util.Arrays;

public class GradeSubs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4297031307353180461L;

	private int id = 0;
	
	private int[] subjectIds = null;

	public GradeSubs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GradeSubs(int id, int[] subjectIds) {
		super();
		this.id = id;
		this.subjectIds = subjectIds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int[] getSubjectIds() {
		return subjectIds;
	}

	public void setSubjectIds(int[] subjectIds) {
		this.subjectIds = subjectIds;
	}

	@Override
	public String toString() {
		return "GradeSubs [id=" + id + ", subjectIds=" + Arrays.toString(subjectIds) + "]";
	}
	
}
