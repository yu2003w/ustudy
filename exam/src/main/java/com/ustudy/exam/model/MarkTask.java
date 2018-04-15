package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.ArrayList;
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
	
	// this value is meaningful for double mark
	private float scorediff = 0;
	
	// teachers includes both first/second round mark with format 'markType'-'teacid'
	// need to parse contents when setting this field
	@JsonIgnore
	private String teachers = null;
	
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
	
	public String getTeachers() {
		return teachers;
	}

	public void setTeachers(String teachers) {
		this.teachers = teachers;
		
		// need to parse teachers here
		String[] data = this.teachers.split(",");
		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				String [] elem = data[i].split("-");
				if (elem != null && elem.length == 2) {
					if (elem[0].compareTo("初评") == 0 || elem[0].compareTo("标准") == 0) {
						if (teachersIds == null) {
							teachersIds = new ArrayList<String>();
						}
						teachersIds.add(elem[1]);
					}
					else if (elem[0].compareTo("终评") == 0) {
						if (finalMarkTeachersIds == null) {
							finalMarkTeachersIds = new ArrayList<String>();
						}
						finalMarkTeachersIds.add(elem[1]);
					}
				}
			}
		}
		
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

	public float getScorediff() {
		return scorediff;
	}

	public void setScorediff(float scorediff) {
		this.scorediff = scorediff;
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
	
	/**
	 * check whether parameter is valid, for example, quesid is valid or not, 
	 * teachers set or not
	 * @return
	 * 
	 */
	public boolean isValid() {
		if (this.questionId == null || this.questionId.isEmpty() || 
				(this.teachersIds == null || this.teachersIds.isEmpty())) {
			return false;
		}
		if (this.markMode.compareTo("双评") == 0 && (this.finalMarkTeachersIds == null ||
				this.finalMarkTeachersIds.isEmpty()) && this.scorediff <= 0) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return "MarkTask [examId=" + examId + ", gradeId=" + gradeId + ", subjectId=" + subjectId + ", questionId="
				+ questionId + ", ownerId=" + ownerId + ", teachersIds=" + teachersIds + ", finalMarkTeachersIds="
				+ finalMarkTeachersIds + ", type=" + type + ", timeLimit=" + timeLimit + ", scorediff=" + scorediff
				+ ", teachers=" + teachers + ", markMode=" + markMode + "]";
	}
	
}
