package com.ustudy.exam.model;

import java.io.Serializable;

public class MultipleScoreSet implements Serializable {

	private static final long serialVersionUID = -443273934491200663L;
	
	private int id;
	private int examGradeSubId;
	private int correctAnswerCount;
	private int studentCorrectCount;
	private int score;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getExamGradeSubId() {
		return examGradeSubId;
	}
	
	public void setExamGradeSubId(int examGradeSubId) {
		this.examGradeSubId = examGradeSubId;
	}
	
	public int getCorrectAnswerCount() {
		return correctAnswerCount;
	}
	
	public void setCorrectAnswerCount(int correctAnswerCount) {
		this.correctAnswerCount = correctAnswerCount;
	}
	
	public int getStudentCorrectCount() {
		return studentCorrectCount;
	}
	
	public void setStudentCorrectCount(int studentCorrectCount) {
		this.studentCorrectCount = studentCorrectCount;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
}
