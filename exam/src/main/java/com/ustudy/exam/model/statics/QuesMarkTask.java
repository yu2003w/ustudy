package com.ustudy.exam.model.statics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class QuesMarkTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6324108430363200437L;

	private int quesid = -1;
	private String teal = null;
	private String markStyle = null;
	
	private int total = -1;

	public QuesMarkTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getQuesid() {
		return quesid;
	}

	public void setQuesid(int quesid) {
		this.quesid = quesid;
	}

	public String getTeal() {
		return teal;
	}

	public void setTeal(String teal) {
		this.teal = teal;
	}

	public String getMarkStyle() {
		return markStyle;
	}

	public void setMarkStyle(String markStyle) {
		this.markStyle = markStyle;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Map<String, Integer> calAssignedAmount() {
		Map<String, Integer> dis = new HashMap<String, Integer>();
		if (teal == null || teal.isEmpty() || this.total <= 0) {
			// no teachers/tasks available for this question
			return dis;
		}
		String[] res = teal.split(",");
		if (res == null || res.length < 1)
			return dis;
		
		int factor = res.length;
		for (int i=0; i<factor; i++) {
			if (i == factor -1)
				dis.put(res[i], this.total/factor + this.total%factor);
			else
				dis.put(res[i],	this.total/factor);
		}
		return dis;
	}
	
	@Override
	public String toString() {
		return "QuesMarkTask [quesid=" + quesid + ", teal=" + teal + ", markStyle=" + markStyle + ", total=" + total
				+ "]";
	}
	
}
