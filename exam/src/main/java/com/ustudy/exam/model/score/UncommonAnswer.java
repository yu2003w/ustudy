package com.ustudy.exam.model.score;

import java.io.Serializable;

public class UncommonAnswer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6851290563699586249L;

	private Long ansId = 0L;
	private String ansMarkImg = "";
	private float score = 0;

	public UncommonAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UncommonAnswer(Long ansId, String ansMarkImg, float score) {
		super();
		this.ansId = ansId;
		this.ansMarkImg = ansMarkImg;
		this.score = score;
	}

	public Long getAnsId() {
		return ansId;
	}

	public void setAnsId(Long ansId) {
		this.ansId = ansId;
	}

	public String getAnsMarkImg() {
		return ansMarkImg;
	}

	public void setAnsMarkImg(String ansMarkImg) {
		this.ansMarkImg = ansMarkImg;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "UncommonAnswer [ansId=" + ansId + ", ansMarkImg=" + ansMarkImg + ", score=" + score
				+ "]";
	}
}
