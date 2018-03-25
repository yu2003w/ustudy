package com.ustudy.mmadapter;

import java.io.Serializable;

public class UResp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8517022413659530073L;
	
	private boolean status = false;
	
	private String msg = null;
	
	private Object data = null;

	public UResp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UResp(boolean status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "UResp [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}

}
