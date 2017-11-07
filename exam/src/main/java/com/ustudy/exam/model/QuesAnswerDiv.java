package com.ustudy.exam.model;

import java.io.Serializable;

public class QuesAnswerDiv implements Serializable {

	private static final long serialVersionUID = 6622342777771917094L;

	private int id;
	private String seqname;
	private String branch;
	private int score;
	private int quesanswerid;
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

	public String getSeqname() {
		return seqname;
	}

	public void setSeqname(String seqname) {
		this.seqname = seqname;
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

	public int getQuesanswerid() {
		return quesanswerid;
	}

	public void setQuesanswerid(int quesanswerid) {
		this.quesanswerid = quesanswerid;
	}

}
