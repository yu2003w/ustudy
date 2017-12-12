package com.ustudy.exam.model.cache;

import java.io.Serializable;

public class FirstMarkRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 822633517547191680L;
	
	private String teacherId = null;
	private String teacherName = null;
	private String score = null;
	
	public FirstMarkRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FirstMarkRecord(String teacherId, String teacherName, String score) {
		super();
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.score = score;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "FirstMarkRecord [teacherId=" + teacherId + ", teacherName=" + teacherName + ", score=" + score + "]";
	}
	
}
