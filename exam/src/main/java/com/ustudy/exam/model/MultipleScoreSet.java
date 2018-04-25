package com.ustudy.exam.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MultipleScoreSet implements Serializable {

	private static final long serialVersionUID = -443273934491200663L;

	private Long id;
	private Long egsId;
	private int total;
	private int selected;
	private float score;
	
	// format is as "1-0.0,2-0.0,3-0.0"
	@JsonIgnore
	private String aggScore = null;

	public MultipleScoreSet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MultipleScoreSet(int total, int selected, float score) {
		super();
		this.total = total;
		this.selected = selected;
		this.score = score;
	}

	public MultipleScoreSet(Long id, Long egsId, int total, int selected, float score) {
		super();
		this.id = id;
		this.egsId = egsId;
		this.total = total;
		this.selected = selected;
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEgsId() {
		return egsId;
	}

	public void setEgsId(Long egsId) {
		this.egsId = egsId;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getAggScore() {
		return aggScore;
	}

	public void setAggScore(String aggScore) {
		this.aggScore = aggScore;
	}

	@Override
	public String toString() {
		return "MultipleScoreSet [id=" + id + ", egsId=" + egsId + ", total=" + total + ", selected=" + selected
				+ ", score=" + score + ", aggScore=" + aggScore + "]";
	}

}
