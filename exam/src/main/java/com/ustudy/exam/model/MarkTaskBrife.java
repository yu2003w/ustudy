package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author jared
 *
 * MarkTaskBrife only include basic information and question summary info. 
 *
 */
public class MarkTaskBrife implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7026386226024739317L;
	
	private String id = null;
	private String examId = null;
	private String examName = null;
	private String teacherId = null;
	private String teacherName = null;
	private String subject = null;
	private String grade = null;
	@JsonProperty("markType")
	private String scoreType = null;
	private String progress = null;
	@JsonIgnore
	private String quesno = null;
	@JsonIgnore
	private String startno = null;
	@JsonIgnore
	private String endno = null;
	@JsonIgnore
	private String quesType = null;
	
	private List<QuesMarkSum> summary = null;
	
	@JsonProperty("groups")
	private List<QuestionPaper> papers = null;

	public MarkTaskBrife() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MarkTaskBrife(String examId, String examName, String teacherId, String teacherName,
			String subject, String grade, String scoreType, String quesno, String startno, String endno) {
		super();
		this.examId = examId;
		this.examName = examName;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.subject = subject;
		this.grade = grade;
		this.scoreType = scoreType;
		this.quesno = quesno;
		this.startno = startno;
		this.endno = endno;
	}

	public MarkTaskBrife(String examId, String examName, String teacherId, String teacherName,
			String subject, String grade, String scoreType, String progress) {
		super();
		this.examId = examId;
		this.examName = examName;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.subject = subject;
		this.grade = grade;
		this.scoreType = scoreType;
		this.progress = progress;
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

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public List<QuesMarkSum> getSummary() {
		return summary;
	}

	public void setSummary(List<QuesMarkSum> summary) {
		this.summary = summary;
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
	
	public String getQuesType() {
		return quesType;
	}

	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}
	
	public List<QuestionPaper> getPapers() {
		return papers;
	}

	public void setPapers(List<QuestionPaper> papers) {
		this.papers = papers;
	}

	@Override
	public String toString() {
		return "MarkTaskBrife [id=" + id + ", examId=" + examId + ", examName=" + examName + ", teacherId=" + teacherId
				+ ", teacherName=" + teacherName + ", subject=" + subject + ", grade=" + grade + ", scoreType="
				+ scoreType + ", progress=" + progress + ", quesno=" + quesno + ", startno=" + startno + ", endno="
				+ endno + ", quesType=" + quesType + ", summary=" + summary + ", papers=" + papers + "]";
	}
	

}
