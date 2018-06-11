package com.ustudy.info.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3286058535369633296L;

	private long id = 0;
	
	private String className = null;
	
	private String classType = null;
	
	@JsonIgnore
	private long gradeId = 0;
	
	@JsonProperty("classOwner")
	private TeacherBrife claOwner = null;
	
	@JsonProperty("subjects")
	private List<SubjectTeac> subs = null;

	public ClassInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassInfo(long id, String className, String classType, TeacherBrife claOwner, List<SubjectTeac> subs) {
		super();
		this.id = id;
		this.className = className;
		this.classType = classType;
		this.claOwner = claOwner;
		this.subs = subs;
	}

	
	public ClassInfo(long id, String className, String classType, String teacid, String teacname) {
		super();
		this.id = id;
		this.className = className;
		this.classType = classType;
		this.claOwner = new TeacherBrife(teacid, teacname);
	}

	public ClassInfo(String className, long gradeId) {
		super();
		this.className = className;
		this.gradeId = gradeId;
	}

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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public TeacherBrife getClaOwner() {
		return claOwner;
	}

	public void setClaOwner(TeacherBrife claOwner) {
		this.claOwner = claOwner;
	}

	public List<SubjectTeac> getSubs() {
		return subs;
	}

	public void setSubs(List<SubjectTeac> subs) {
		this.subs = subs;
	}	

	private boolean equals(ClassInfo ci) {
		if (this == ci)
			return true;
		if (this.getClassName() != null && ci.getClassName() != null && 
				this.getClassName().equals(ci.getClassName()) && 
				this.getGradeId() > 0 && ci.getGradeId() > 0 && this.getGradeId() == ci.getGradeId()) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ClassInfo) {
			return this.equals((ClassInfo)obj);
		}else
			return false;
	}

	@Override
	public int hashCode() {
		// class info could be determined by class name and grade id
		return Objects.hash(this.className, this.gradeId);

	}

	@Override
	public String toString() {
		return "ClassInfo [id=" + id + ", className=" + className + ", classType=" + classType + ", gradeId=" + gradeId
				+ ", claOwner=" + claOwner + ", subs=" + subs + "]";
	}
	
}
