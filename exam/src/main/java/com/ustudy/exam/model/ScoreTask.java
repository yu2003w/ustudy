package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	
	// attributes for question block, this may contain one or multiple questions
	// including question type, question no, startno, endno, position, height, length
	private String questionType = null;
	
	// question name should be a range for gap filling questions
	private String questionName = null;
	@JsonIgnore
	private String quesno = null;
	@JsonIgnore
	private String startno = null;
	@JsonIgnore
	private String endno = null;
	// following attributes used for cutting pictures from student papers
	@JsonIgnore
	private int posx = 0;
	@JsonIgnore
	private int posy = 0;
	@JsonIgnore
	private int height = 0;
	@JsonIgnore
	private int length = 0;
	
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
		this.quesno = questionNum;
		this.startno = startNum;
		this.endno = endNum;
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
	
	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getQuesno() {
		return quesno;
	}

	public void setQuesno(String quesno) {
		this.quesno = quesno;
	}

	public String getStartno() {
		return startno;
	}
	
	public void setStartno(String startno) {
		this.startno = startno;
	}
	
	public String getEndno() {
		return endno;
	}

	public void setEndno(String endno) {
		this.endno = endno;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
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
				+ ", questionName=" + questionName + ", quesno=" + quesno + ", startno=" + startno + ", endno=" + endno
				+ ", posx=" + posx + ", posy=" + posy + ", height=" + height + ", length=" + length + ", papers="
				+ papers + "]";
	}
	
}
