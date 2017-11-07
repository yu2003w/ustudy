package com.ustudy.exam.model;

import java.io.Serializable;

public class QuesAnswer implements Serializable {

	private static final long serialVersionUID = -8233762600675469685L;

	private int id;
	private int quesno;
	private int startno;
	private int endno;
	private String type;
	private String branch;
	private int choiceNum;
	private int score;
	private String assignMode;
	private String scoreMode;
	private String teacOwner;
	private int examGradeSubId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuesno() {
		return quesno;
	}

	public void setQuesno(int quesno) {
		this.quesno = quesno;
	}

	public int getStartno() {
		return startno;
	}

	public void setStartno(int startno) {
		this.startno = startno;
	}

	public int getEndno() {
		return endno;
	}

	public void setEndno(int endno) {
		this.endno = endno;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public int getChoiceNum() {
		return choiceNum;
	}

	public void setChoiceNum(int choiceNum) {
		this.choiceNum = choiceNum;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getAssignMode() {
		return assignMode;
	}

	public void setAssignMode(String assignMode) {
		this.assignMode = assignMode;
	}

	public String getScoreMode() {
		return scoreMode;
	}

	public void setScoreMode(String scoreMode) {
		this.scoreMode = scoreMode;
	}

	public String getTeacOwner() {
		return teacOwner;
	}

	public void setTeacOwner(String teacOwner) {
		this.teacOwner = teacOwner;
	}

	public int getExamGradeSubId() {
		return examGradeSubId;
	}

	public void setExamGradeSubId(int examGradeSubId) {
		this.examGradeSubId = examGradeSubId;
	}

}
