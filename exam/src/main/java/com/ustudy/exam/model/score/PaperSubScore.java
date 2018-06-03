package com.ustudy.exam.model.score;

import java.io.Serializable;

public class PaperSubScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6851290563699586237L;

	private float score = 0;
	private float objScore = 0;
	
	public PaperSubScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PaperSubScore(float score, float objScore) {
		super();
		this.score = score;
		this.objScore = objScore;
	}


	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public float getObjScore() {
		return objScore;
	}

	public void setObjScore(float objScore) {
		this.objScore = objScore;
	}

	@Override
	public String toString() {
		return "PaperSubScore [score=" + score + ", objScore=" + objScore + "]";
	}
	
}
