package com.ustudy.exam.model;

import java.io.Serializable;

public class StudentObjectAnswer implements Serializable {
	
	private static final long serialVersionUID = -6960129453974134171L;
	
	private Long id;
	private Long paperid;
	private int quesno;
	private int score;
	private String answer;
	private int answerHas;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPaperid() {
		return paperid;
	}
	
	public void setPaperid(Long paperid) {
		this.paperid = paperid;
	}
	
	public int getQuesno() {
		return quesno;
	}
	
	public void setQuesno(int quesno) {
		this.quesno = quesno;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public int getAnswerHas() {
		return answerHas;
	}
	
	public void setAnswerHas(int answerHas) {
		this.answerHas = answerHas;
	}
	
}
