package com.ustudy.exam.model.cache;

import java.io.Serializable;
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
	private Map<String, PaperImgCache> curAssign = null;
	
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

	// noted: function name should not be get/set for non class members
	public float calProgress() {
		if (this.total != 0) {
			return (float) this.completed/this.total;
		}
		else
			return 0;
	}
	
	public void incrCompleted(int num) {
		this.completed += num;
	}
	
	public Map<String, PaperImgCache> getCurAssign() {
		return curAssign;
	}

	public void setCurAssign(Map<String, PaperImgCache> curAssign) {
		this.curAssign = curAssign;
	}

	@Override
	public String toString() {
		return "MarkStaticsCache [total=" + total + ", completed=" + completed + ", curAssign=" + curAssign + "]";
	}
	
}
