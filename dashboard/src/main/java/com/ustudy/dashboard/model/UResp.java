package com.ustudy.dashboard.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6010638596945748515L;

	@JsonProperty("success")
	private boolean ret = false;
	
	private Object data = null;
	
	private String message = null;

	public UResp() {
		super();
		// TODO Auto-generated constructor stub
		this.ret = false;
	}

	public UResp(boolean ret, Object data, String message) {
		super();
		this.ret = ret;
		this.data = data;
		this.message = message;
	}

	public boolean isRet() {
		return ret;
	}

	public void setRet(boolean ret) {
		this.ret = ret;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "UResp [ret=" + ret + ", data=" + data + ", message=" + message + "]";
	}
	
}
