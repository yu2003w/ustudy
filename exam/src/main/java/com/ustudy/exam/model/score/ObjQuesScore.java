package com.ustudy.exam.model.score;

import java.io.Serializable;

public class ObjQuesScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6851290563699586234L;

	private String quesno = null;
	private float score = 0;
	private String answer = null;
	
	public ObjQuesScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ObjQuesScore(String quesno, float score, String answer) {
		super();
		this.quesno = quesno;
		this.score = score;
		this.answer = answer;
	}

	public String getQuesno() {
		return quesno;
	}

	public void setQuesno(String quesno) {
		this.quesno = quesno;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "QuesScore [quesno=" + quesno + ", score=" + score + ", answer=" + answer + "]";
	}
	
}
