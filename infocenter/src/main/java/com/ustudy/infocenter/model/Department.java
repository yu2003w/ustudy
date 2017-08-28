package com.ustudy.infocenter.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Department implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5358347528570598398L;

	@JsonProperty("departmentName")
	private String depName = null;
	
	@JsonProperty("grades")
	private List<Grade> grL = null;

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Department(String depName, List<Grade> grL) {
		super();
		this.depName = depName;
		this.grL = grL;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public List<Grade> getGrL() {
		return grL;
	}

	public void setGrL(List<Grade> grL) {
		this.grL = grL;
	}

	@Override
	public String toString() {
		return "Department [depName=" + depName + ", grL=" + grL + "]";
	}
	
}
