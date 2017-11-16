package com.ustudy.exam.model;

import java.io.Serializable;

public class ExamStudent implements Serializable {

	private static final long serialVersionUID = 1545105478018201996L;
	
	private Long id;
	private String examCode;
	private Long examid;
	private Long schid;
	private Long gradeid;
	private String className;
	private String name;
	private String stuno;
	private int room;
	private String paperStatus;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getExamCode() {
		return examCode;
	}
	
	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}
	
	public Long getExamid() {
		return examid;
	}
	
	public void setExamid(Long examid) {
		this.examid = examid;
	}
	
	public Long getSchid() {
		return schid;
	}
	
	public void setSchid(Long schid) {
		this.schid = schid;
	}
	
	public Long getGradeid() {
		return gradeid;
	}
	
	public void setGradeid(Long gradeid) {
		this.gradeid = gradeid;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStuno() {
		return stuno;
	}
	
	public void setStuno(String stuno) {
		this.stuno = stuno;
	}
	
	public int getRoom() {
		return room;
	}
	
	public void setRoom(int room) {
		this.room = room;
	}
	
	public String getQaperStatus() {
		return paperStatus;
	}
	
	public void setQaperStatus(String paperStatus) {
		this.paperStatus = paperStatus;
	}
	
}
