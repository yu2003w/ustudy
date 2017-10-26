package com.ustudy.exam.model;

import java.io.Serializable;

public class ExamStudent implements Serializable {

	private static final long serialVersionUID = 1545105478018201996L;
	
	private int id;
	private String examCode;
	private int examid;
	private int schid;
	private int gradeid;
	private String className;
	private String name;
	private String stuno;
	private int room;
	private String paperStatus;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getExamCode() {
		return examCode;
	}
	
	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}
	
	public int getExamid() {
		return examid;
	}
	
	public void setExamid(int examid) {
		this.examid = examid;
	}
	
	public int getSchid() {
		return schid;
	}
	
	public void setSchid(int schid) {
		this.schid = schid;
	}
	
	public int getGradeid() {
		return gradeid;
	}
	
	public void setGradeid(int gradeid) {
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
