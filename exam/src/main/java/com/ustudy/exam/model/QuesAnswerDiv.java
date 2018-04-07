package com.ustudy.exam.model;

import java.io.Serializable;

public class QuesAnswerDiv implements Serializable {

	private static final long serialVersionUID = 6622342777771917094L;

	private Long id;
	private String quesno;
	private String type;
	private String branch;
	private float score;
	private Long quesid;
	private Long egsId;

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

    public String getQuesno() {
        return quesno;
    }

    public void setQuesno(String quesno) {
        this.quesno = quesno;
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

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public Long getQuesid() {
		return quesid;
	}

	public void setQuesid(Long quesid) {
		this.quesid = quesid;
	}

	@Override
	public String toString() {
		return "QuesAnswerDiv [id=" + id + ", quesno=" + quesno + ", type=" + type + ", branch=" + branch + ", score="
				+ score + ", quesid=" + quesid + ", egsId=" + egsId + "]";
	}

}
