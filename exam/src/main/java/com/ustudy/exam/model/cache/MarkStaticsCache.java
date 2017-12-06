package com.ustudy.exam.model.cache;

import java.io.Serializable;
import java.util.List;

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
	private String avescore = null;
	private List<MarkTaskCache> curAssign = null;
	
	public MarkStaticsCache() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MarkStaticsCache(int total, int completed) {
		super();
		this.total = total;
		this.completed = completed;
	}

	public MarkStaticsCache(int completed, String avescore) {
		super();
		this.completed = completed;
		this.avescore = avescore;
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

	// noted: function name should not be get/set for non class members
	public String calProgress() {
		return completed + "/" + total;
	}
	
	public void incrCompleted(int num, String score) {
		Float ts = this.completed*Float.valueOf(this.avescore) + Float.valueOf(score);
		this.completed += num;
		this.avescore = String.format("%.1f", ts/this.completed);
	}
	
	public List<MarkTaskCache> getCurAssign() {
		return curAssign;
	}

	public void setCurAssign(List<MarkTaskCache> curAssign) {
		this.curAssign = curAssign;
	}

	public String getAvescore() {
		return avescore;
	}

	public void setAvescore(String avescore) {
		this.avescore = avescore;
	}
	
	@Override
	public String toString() {
		return "MarkStaticsCache [total=" + total + ", completed=" + completed + ", avescore=" + avescore
				+ ", curAssign=" + curAssign + "]";
	}
	
}
