package com.ustudy.info.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UElem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1170078618245612481L;

	@JsonProperty("n")
	private String value = null;

	public UElem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UElem(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "[n=" + value + "]";
	}
	
}
