package com.ustudy.info.model;

import java.io.Serializable;
import java.util.List;

public class TeacherSub implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5766199963895084326L;

	private String teacherName = null;
	private String teacherId = null;
	private List<UElem> subjects = null;
	
	public TeacherSub() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherSub(String teacherName, String teacherId, List<UElem> subjects) {
		super();
		this.teacherName = teacherName;
		this.teacherId = teacherId;
		this.subjects = subjects;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public List<UElem> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<UElem> subjects) {
		this.subjects = subjects;
	}

	@Override
	public String toString() {
		return "TeacherSub [teacherName=" + teacherName + ", teacherId=" + teacherId + ", subjects=" + subjects + "]";
	}	

}
