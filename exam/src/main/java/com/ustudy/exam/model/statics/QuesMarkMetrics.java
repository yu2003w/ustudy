package com.ustudy.exam.model.statics;

import java.io.Serializable;

public class QuesMarkMetrics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2350384160946919211L;
	
	private int quesid = 0;
	private int total = 0;
	private int marked = 0;
	
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

	@Override
	public String toString() {
		return "QuesMetrics [quesid=" + quesid + ", total=" + total + ", marked=" + marked + "]";
	}

}
