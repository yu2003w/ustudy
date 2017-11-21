package com.ustudy.exam.model;

import java.io.Serializable;

public class ExamResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7130294580790411728L;

	private Long id;
	private String stuname = null;
	private String stuno = null;
	
	private int score = 0;

	public ExamResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamResult(Long id, String stuname, String stuno, int score) {
		super();
		this.id = id;
		this.stuname = stuname;
		this.stuno = stuno;
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStuname() {
		return stuname;
	}

	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

	public String getStuno() {
		return stuno;
	}

	public void setStuno(String stuno) {
		this.stuno = stuno;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "ExamResult [id=" + id + ", stuname=" + stuname + ", stuno=" + stuno + ", score=" + score + "]";
	}
	
}
