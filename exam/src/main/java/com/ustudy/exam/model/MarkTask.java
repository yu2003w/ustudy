package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MarkTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7849305044558352622L;

	private String examId = null;
	private String gradeId = null;
	private String subjectId = null;
	private String questionId = null;
	private String ownerId = null;
	private List<String> teachersIds = null;
	private List<String> finalMarkTeachersIds = null;
	
	// mark task dispatch method, average or dynamic
	private String type = null;
	
	// duration for specific question
	private int timeLimit = 0;
	
	// if mark mode is single screen, no need to query for final mark teachers
	@JsonIgnore
	private String markMode = null;
	
	public MarkTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MarkTask(String ownerId, String type, int timeLimit, String mode) {
		super();
		this.ownerId = ownerId;
		this.type = type;
		this.timeLimit = timeLimit;
		this.markMode = mode;
	}

	public void setMetaInfo(String quesid, ExamGradeSub egs) {
		this.questionId = quesid;
		this.examId = egs.getExamId();
		this.gradeId = egs.getGradeId();
		this.subjectId = egs.getSubjectId();
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

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public List<String> getTeachersIds() {
		return teachersIds;
	}

	public void setTeachersIds(List<String> teachersIds) {
		this.teachersIds = teachersIds;
	}

	public List<String> getFinalMarkTeachersIds() {
		return finalMarkTeachersIds;
	}

	public void setFinalMarkTeachersIds(List<String> finalMarkTeachersIds) {
		this.finalMarkTeachersIds = finalMarkTeachersIds;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getMarkMode() {
		return markMode;
	}

	public void setMarkMode(String markMode) {
		this.markMode = markMode;
	}

	@Override
	public String toString() {
		return "MarkTask [examId=" + examId + ", gradeId=" + gradeId + ", subjectId=" + subjectId + ", questionId="
				+ questionId + ", ownerId=" + ownerId + ", teachersIds=" + teachersIds + ", finalMarkTeachersIds="
				+ finalMarkTeachersIds + ", type=" + type + ", timeLimit=" + timeLimit + ", markMode=" + markMode + "]";
	}
	
}
