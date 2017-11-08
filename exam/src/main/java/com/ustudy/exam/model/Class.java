package com.ustudy.exam.model;

import java.io.Serializable;

public class Class implements Serializable {
    
	private static final long serialVersionUID = 6076593693938028994L;
	
	private Long id;
    private Long gradeId;
    private String clsName;
    private String clsType;
    private String clsOwner;
    
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getGradeId() {
		return gradeId;
	}
	
	public void setGradeId(long gradeId) {
		this.gradeId = gradeId;
	}
	
	public String getClsName() {
		return clsName;
	}
	
	public void setClsName(String clsName) {
		this.clsName = clsName;
	}
	
	public String getClsType() {
		return clsType;
	}
	
	public void setClsType(String clsType) {
		this.clsType = clsType;
	}
	
	public String getClsOwner() {
		return clsOwner;
	}
	
	public void setClsOwner(String clsOwner) {
		this.clsOwner = clsOwner;
	}
    
}
