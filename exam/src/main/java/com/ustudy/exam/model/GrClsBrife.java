package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ustudy.Item;

public class GrClsBrife implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 768336467862663943L;

	private int grId = -1;
	private String grName = null;
	
	@JsonProperty("class")
	private List<Item> cls = null;

	public GrClsBrife() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GrClsBrife(int grId, String grName, List<Item> cls) {
		super();
		this.grId = grId;
		this.grName = grName;
		this.cls = cls;
	}

	public GrClsBrife(int grId, String grName) {
		super();
		this.grId = grId;
		this.grName = grName;
	}

	public int getGrId() {
		return grId;
	}

	public void setGrId(int grId) {
		this.grId = grId;
	}

	public String getGrName() {
		return grName;
	}

	public void setGrName(String grName) {
		this.grName = grName;
	}

	public List<Item> getCls() {
		return cls;
	}

	public void setCls(List<Item> cls) {
		this.cls = cls;
	}

	@Override
	public String toString() {
		return "GrClassBrife [grId=" + grId + ", grName=" + grName + ", cls=" + cls + "]";
	}
	
}
