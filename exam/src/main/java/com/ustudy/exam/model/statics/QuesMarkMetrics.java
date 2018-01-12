package com.ustudy.exam.model.statics;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuesMarkMetrics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2350384160946919211L;
	
	private int quesid = 0;
	private int startno = 0;
	private int endno = 0;
	private int total = 0;
	private int marked = 0;
	
	private String markStyle = null;
	
	@JsonProperty("teacherList")
	private String teaL = null;
	
	public QuesMarkMetrics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getQuesid() {
		return quesid;
	}

	public void setQuesid(int quesid) {
		this.quesid = quesid;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getMarked() {
		return marked;
	}

	public void setMarked(int marked) {
		this.marked = marked;
	}

	public int getStartno() {
		return startno;
	}

	public void setStartno(int startno) {
		this.startno = startno;
	}

	public int getEndno() {
		return endno;
	}

	public void setEndno(int endno) {
		this.endno = endno;
	}

	public String getMarkStyle() {
		return markStyle;
	}

	public void setMarkStyle(String markStyle) {
		this.markStyle = markStyle;
	}

	public String getTeaL() {
		return teaL;
	}

	public void setTeaL(String teaL) {
		this.teaL = teaL;
	}

	@Override
	public String toString() {
		return "QuesMarkMetrics [quesid=" + quesid + ", startno=" + startno + ", endno=" + endno + ", total=" + total
				+ ", marked=" + marked + ", markStyle=" + markStyle + ", teaL=" + teaL + "]";
	}

}
