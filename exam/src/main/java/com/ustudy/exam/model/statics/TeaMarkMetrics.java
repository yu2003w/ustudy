package com.ustudy.exam.model.statics;

import java.io.Serializable;

public class TeaMarkMetrics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5397760860152394076L;

	private int quesId = -1;
	
	private String quesName = null;
	
	private String markStyle = null;
	
	private int marked = 0;
	
	private int total = 0;
	
	public TeaMarkMetrics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeaMarkMetrics(String metrics) {
		// metrics information concat with format as quesid-startno-endno-marktype-marked
		// need to parse this string
		String[] data = metrics.split("-");
		if (data == null || data.length != 5) {
			throw new RuntimeException("TeaMarkMetrics(), failed to construct metrics with->" + metrics);
		}
		this.quesId = Integer.valueOf(data[0]);
		if (data[1].equals(data[2])) {
			quesName = data[1];
		}
		else
			quesName = data[1] + "-" + data[2];
		this.markStyle = data[3];
		this.marked = Integer.valueOf(data[4]);
	}
	
	public int getQuesId() {
		return quesId;
	}

	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}

	public String getQuesName() {
		return quesName;
	}

	public void setQuesName(String quesName) {
		this.quesName = quesName;
	}

	public int getMarked() {
		return marked;
	}

	public void setMarked(int marked) {
		this.marked = marked;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getMarkStyle() {
		return markStyle;
	}

	public void setMarkStyle(String markStyle) {
		this.markStyle = markStyle;
	}

	@Override
	public String toString() {
		return "TeaMarkMetrics [quesId=" + quesId + ", quesName=" + quesName + ", markStyle=" + markStyle + ", marked="
				+ marked + ", total=" + total + "]";
	}
	
}
