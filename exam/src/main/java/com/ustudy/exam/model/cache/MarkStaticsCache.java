package com.ustudy.exam.model.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
	
	// key is paper id to get better performance when updating mark result
	private Map<String, MarkTaskCache> curAssign = null;
	
	// store paper ids by assigned sequences, this attribute is useful for remarking
	private List<String> pList = null;
	
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
		if (this.avescore == null) {
			// first time to calculate average score
			this.avescore = "0";
		}
		Float ts = this.completed*Float.valueOf(this.avescore) + Float.valueOf(score);
		this.completed += num;
		this.avescore = String.format("%.1f", ts/this.completed);
	}
	
	public Map<String, MarkTaskCache> getCurAssign() {
		return curAssign;
	}

	public void setCurAssign(Map<String, MarkTaskCache> curAssign) {
		this.curAssign = curAssign;
	}

	public String getAvescore() {
		return avescore;
	}

	public void setAvescore(String avescore) {
		this.avescore = avescore;
	}
	
	public List<String> getpList() {
		return pList;
	}

	public void setpList(List<String> pList) {
		this.pList = pList;
	}

	@Override
	public String toString() {
		return "MarkStaticsCache [total=" + total + ", completed=" + completed + ", avescore=" + avescore
				+ ", curAssign=" + curAssign + ", pList=" + pList + "]";
	}
	
}
