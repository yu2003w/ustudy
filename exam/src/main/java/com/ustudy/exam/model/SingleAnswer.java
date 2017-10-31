package com.ustudy.exam.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author jared
 * 
 * Answer for single question not question block
 *
 */
public class SingleAnswer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5202041799741836582L;

	// question number for single question
	@JsonProperty("name")
	private String quesno = null;
	private int fullscore = 0;
	private int score = 0;
	
	public SingleAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SingleAnswer(String qno, int fullscore, int score) {
		super();
		this.quesno = qno;
		this.fullscore = fullscore;
		this.score = score;
	}

	public SingleAnswer(String quesno, int fullscore) {
		super();
		this.quesno = quesno;
		this.fullscore = fullscore;
	}

	public String getQuesno() {
		return quesno;
	}

	public void setQuesno(String quesno) {
		this.quesno = quesno;
	}

	public int getFullscore() {
		return fullscore;
	}

	public void setFullscore(int fullscore) {
		this.fullscore = fullscore;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "SingleAnswer [quesno=" + quesno + ", fullscore=" + fullscore + ", score=" + score + "]";
	}
	
}
