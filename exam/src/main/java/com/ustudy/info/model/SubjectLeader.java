package com.ustudy.info.model;

import java.io.Serializable;
import java.util.List;

public class SubjectLeader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4371180423044421571L;

	private String subject = null;
	
	private List<TeacherBrife> owners = null;

	public SubjectLeader() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectLeader(String subject, List<TeacherBrife> owners) {
		super();
		this.subject = subject;
		this.owners = owners;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<TeacherBrife> getOwners() {
		return owners;
	}

	public void setOwners(List<TeacherBrife> owners) {
		this.owners = owners;
	}

	@Override
	public String toString() {
		return "SubjectOwner [subject=" + subject + ", owners=" + owners + "]";
	}
	
}
