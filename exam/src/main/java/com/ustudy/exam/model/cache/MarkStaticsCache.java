package com.ustudy.exam.model.cache;

import java.io.Serializable;

/**
 * @author jared
 * 
 * Per question ids, need statics data about marking progress and so on
 *
 */
public class MarkStaticsCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8678834633832041638L;

	private int total = 0;
	private int completed = 0;
	
	public MarkStaticsCache() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MarkStaticsCache(int total, int completed) {
		super();
		this.total = total;
		this.completed = completed;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCompleted() {
		return completed;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}

	public float getProgress() {
		if (this.total != 0) {
			return (float) this.completed/this.total;
		}
		else
			return 0;
	}
	
	public void incrCompleted() {
		this.completed++;
	}
	
	@Override
	public String toString() {
		return "MarkStatics [total=" + total + ", completed=" + completed + "]";
	}
	
}
