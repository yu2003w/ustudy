package com.ustudy.exam.model;

import java.io.Serializable;


/**
 * @author jared
 *
 * Construct request for retrieving all assigned tasks for certain subject in an exam.
 *
 */
public class ExamGradeSub implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1314036554328168589L;

	private String examId = null;
	private String gradeId = null;
	private String subjectId = null;
	
	public ExamGradeSub() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamGradeSub(String examId, String gradeId, String subjectId) {
		super();
		this.examId = examId;
		this.gradeId = gradeId;
		this.subjectId = subjectId;
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

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	@Override
	public String toString() {
		return "ExamGradeSub [examId=" + examId + ", gradeId=" + gradeId + ", subjectId=" + subjectId + "]";
	}
	
}
