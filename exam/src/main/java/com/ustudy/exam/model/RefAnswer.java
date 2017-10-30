package com.ustudy.exam.model;

import java.io.Serializable;

public class RefAnswer implements Serializable {

	private static final long serialVersionUID = -7483001885279740033L;

	private int id;
	private int quesid;
	private int quesno;
	private String answer;
	private int egsId;

	public int getEgsId() {
		return egsId;
	}

	public void setEgsId(int egsId) {
		this.egsId = egsId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuesid() {
		return quesid;
	}

	public void setQuesid(int quesid) {
		this.quesid = quesid;
	}

	public int getQuesno() {
		return quesno;
	}

	public void setQuesno(int quesno) {
		this.quesno = quesno;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
