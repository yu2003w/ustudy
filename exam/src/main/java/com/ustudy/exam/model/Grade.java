package com.ustudy.exam.model;

import java.io.Serializable;

public class Grade implements Serializable {

	private static final long serialVersionUID = 2887211776820286277L;
	
	private Long id;	
	private String gradeName;	
	private int classesNum;
	private String gradeOwner;	
	private String schid;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getGradeName() {
		return gradeName;
	}
	
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	public int getClassesNum() {
		return classesNum;
	}
	
	public void setClassesNum(int classesNum) {
		this.classesNum = classesNum;
	}
	
	public String getGradeOwner() {
		return gradeOwner;
	}
	
	public void setGradeOwner(String gradeOwner) {
		this.gradeOwner = gradeOwner;
	}
	
	public String getSchid() {
		return schid;
	}
	
	public void setSchid(String schid) {
		this.schid = schid;
	}
	
	public String getDepartment() {
		if (this.gradeName.compareTo("高一") == 0 || this.gradeName.compareTo("高二") == 0 || this.gradeName.startsWith("高三")){			
			return "高中部";
		}else if (this.gradeName.compareTo("七年级") == 0 ||
			this.gradeName.compareTo("八年级") == 0 ||
			this.gradeName.startsWith("九年级")){
			return "初中部";
		}else if (this.gradeName.compareTo("一年级") == 0 ||
			this.gradeName.compareTo("二年级") == 0 ||
			this.gradeName.compareTo("三年级") == 0 ||
			this.gradeName.compareTo("四年级") == 0 ||
			this.gradeName.compareTo("五年级") == 0 ||
			this.gradeName.startsWith("六年级")) {
			return "小学部";
		}
		return "其他";	
	}
	
}
