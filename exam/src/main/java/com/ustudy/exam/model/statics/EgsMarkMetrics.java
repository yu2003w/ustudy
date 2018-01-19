package com.ustudy.exam.model.statics;

import java.io.Serializable;

public class EgsMarkMetrics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2477436847230603451L;
	
	// number of examinee
	int num = 0;
	
	// all questions to be marked
	int total = 0;
	
	// number of already marked questions
	int marked = 0;

	public EgsMarkMetrics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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
		return "EgsMarkMetrics [num=" + num + ", total=" + total + ", marked=" + marked + "]";
	}

}
