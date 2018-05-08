package com.ustudy.info.model;

import java.io.Serializable;
import java.util.List;

public class Examinee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1646823396136227256L;

	private long id = 0;

	private String stuName = null;
	private String stuId = null;
	private String stuExamId = null;

	private String examId = null;
	private String schId = null;
	private String gradeId = null;

	private String classId = null;
	private String className = null;
	
	// sub ids selected for the examinee
	private List<Long> subs = null;

	public Examinee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Examinee(String stuName, String stuId, String stuExamId, String examId, String gradeId, 
			String classId, List<Long> subs) {
		super();
		this.stuName = stuName;
		this.stuId = stuId;
		this.stuExamId = stuExamId;
		this.examId = examId;
		this.gradeId = gradeId;
		this.classId = classId;
		this.subs = subs;
	}

	public Examinee(String stuName, String stuId, String stuExamId, String examId, String schId, 
			String gradeId,	String classId, String className) {
		super();
		this.stuName = stuName;
		this.stuId = stuId;
		this.stuExamId = stuExamId;
		this.examId = examId;
		this.schId = schId;
		this.gradeId = gradeId;
		this.classId = classId;
		this.className = className;
	}

	public Examinee(int id, String stuName, String stuId, String stuExamId, String examId, String schId,
			String gradeId,	String classId, String className) {
		super();
		this.id = id;
		this.stuName = stuName;
		this.stuId = stuId;
		this.stuExamId = stuExamId;
		this.examId = examId;
		this.schId = schId;
		this.gradeId = gradeId;
		this.classId = classId;
		this.className = className;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getStuExamId() {
		return stuExamId;
	}

	public void setStuExamId(String stuExamId) {
		this.stuExamId = stuExamId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getSchId() {
		return schId;
	}

	public void setSchId(String schId) {
		this.schId = schId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public List<Long> getSubs() {
		return subs;
	}

	public void setSubs(List<Long> subs) {
		this.subs = subs;
	}

	@Override
	public String toString() {
		return "Examinee [id=" + id + ", stuName=" + stuName + ", stuId=" + stuId + ", stuExamId=" + stuExamId
				+ ", examId=" + examId + ", schId=" + schId + ", gradeId=" + gradeId + ", classId=" + classId
				+ ", className=" + className + ", subs=" + subs + "]";
	}

}
