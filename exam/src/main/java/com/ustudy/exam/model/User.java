package com.ustudy.exam.model;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6091110002226750766L;
	
	private String uid = null;
	private String uname = null;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String uid, String uname) {
		super();
		this.uid = uid;
		this.uname = uname;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", uname=" + uname + "]";
	}

}
