package com.ustudy.exam.model;

import java.io.Serializable;

public class QuesAnswerDiv implements Serializable {

	private static final long serialVersionUID = 6622342777771917094L;

	private int id;
	private String quesno;
	private String branch;
	private int score;
	private int quesid;
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

	public String getQuesno() {
		return quesno;
	}

	public void setQuesno(String quesno) {
		this.quesno = quesno;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getQuesid() {
		return quesid;
	}

	public void setQuesid(int quesid) {
		this.quesid = quesid;
	}

}
