package com.ustudy.mmadapter.model;

import java.io.Serializable;

public class School implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4511306701212989036L;
	
	private String schoolName = null;
	
	private String schoolId = null;

	public School() {
		super();
		// TODO Auto-generated constructor stub
	}

	public School(String schoolName, String schoolId) {
		super();
		this.schoolName = schoolName;
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	@Override
	public String toString() {
		return "School [schoolName=" + schoolName + ", schoolId=" + schoolId + "]";
	}

}
