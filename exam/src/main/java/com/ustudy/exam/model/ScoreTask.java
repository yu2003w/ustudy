package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScoreTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7849305044558352622L;

	private String id = null;
	private String examId = null;
	private String examName = null;
	private String teacherId = null;
	private String teacherName = null;
	private String subject = null;
	private String grade = null;
	@JsonProperty("markType")
	private String scoreType = null;
	@JsonProperty("markStyle")
	private String scoreMode = null;
	private String progress = null;
	
	// attributes for question related
	private String questionType = null;
	private String questionNum = null;
	private String startNum = null;
	private String endNum = null;
	private List<QuestionPaper> papers = null;
	
	public ScoreTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public ScoreTask(String examId, String examName, String subject, String grade) {
		super();
		this.examId = examId;
		this.examName = examName;
		this.subject = subject;
		this.grade = grade;
	}

	public ScoreTask(String id, String examId, String examName, String teacherId, String teacherName, String subject,
			String grade, String scoreType, String scoreMode, String progress, String questionType, String questionNum,
			String startNum, String endNum, List<QuestionPaper> papers) {
		super();
		this.id = id;
		this.examId = examId;
		this.examName = examName;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.subject = subject;
		this.grade = grade;
		this.scoreType = scoreType;
		this.scoreMode = scoreMode;
		this.progress = progress;
		this.questionType = questionType;
		this.questionNum = questionNum;
		this.startNum = startNum;
		this.endNum = endNum;
		this.papers = papers;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public String getScoreMode() {
		return scoreMode;
	}

	public void setScoreMode(String scoreMode) {
		this.scoreMode = scoreMode;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(String questionNum) {
		this.questionNum = questionNum;
	}

	public String getStartNum() {
		return startNum;
	}

	public void setStartNum(String startNum) {
		this.startNum = startNum;
	}

	public String getEndNum() {
		return endNum;
	}

	public void setEndNum(String endNum) {
		this.endNum = endNum;
	}

	public List<QuestionPaper> getPapers() {
		return papers;
	}

	public void setPapers(List<QuestionPaper> papers) {
		this.papers = papers;
	}

	@Override
	public String toString() {
		return "ScoreTask [id=" + id + ", examId=" + examId + ", examName=" + examName + ", teacherId=" + teacherId
				+ ", teacherName=" + teacherName + ", subject=" + subject + ", grade=" + grade + ", scoreType="
				+ scoreType + ", scoreMode=" + scoreMode + ", progress=" + progress + ", questionType=" + questionType
				+ ", questionNum=" + questionNum + ", startNum=" + startNum + ", endNum=" + endNum + ", papers="
				+ papers + "]";
	}
	
}
