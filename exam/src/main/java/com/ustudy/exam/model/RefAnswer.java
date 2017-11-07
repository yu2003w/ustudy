package com.ustudy.exam.model;

import java.io.Serializable;

public class RefAnswer implements Serializable {

	private static final long serialVersionUID = -7483001885279740033L;

	private Long id;
	private Long egsId;
	private Long quesid;
	private int quesno;
	private String branch;
	private String answer;
	private String type;
	private int choiceNum;

	public Long getEgsId() {
		return egsId;
	}

	public void setEgsId(Long egsId) {
		this.egsId = egsId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuesid() {
		return quesid;
	}

	public void setQuesid(Long quesid) {
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

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getChoiceNum() {
		return choiceNum;
	}

	public void setChoiceNum(int choiceNum) {
		this.choiceNum = choiceNum;
	}

}
