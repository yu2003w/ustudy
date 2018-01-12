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
	private String fullscore = "";
	private String score = "";
	
	public SingleAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SingleAnswer(String qno, String fullscore, String score) {
		super();
		this.quesno = qno;
		this.fullscore = fullscore;
		this.score = score;
	}

	public SingleAnswer(String quesno, String fullscore) {
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

	public String getFullscore() {
		return fullscore;
	}

	public void setFullscore(String fullscore) {
		this.fullscore = fullscore;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "SingleAnswer [quesno=" + quesno + ", fullscore=" + fullscore + ", score=" + score + "]";
	}
	
}
