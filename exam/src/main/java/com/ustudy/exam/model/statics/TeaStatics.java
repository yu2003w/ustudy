package com.ustudy.exam.model.statics;

import java.io.Serializable;

public class TeaStatics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1512988241523162416L;
	
    // score for all marked papers	
	private String score = null;
	
	private int marked = 0;
	
	private String quesid = null;

	public TeaStatics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeaStatics(String score, int marked, String quesid) {
		super();
		this.score = score;
		this.marked = marked;
		this.quesid = quesid;
	}

	public int getMarked() {
		return marked;
	}

	public void setMarked(int marked) {
		this.marked = marked;
	}

	public String getQuesid() {
		return quesid;
	}

	public void setQuesid(String quesid) {
		this.quesid = quesid;
	}

	public String calAverageS() {
		Float ret = Float.valueOf(score)/Integer.valueOf(marked);
		return String.format("%.1f", ret);
	}

	@Override
	public String toString() {
		return "TeaStatics [score=" + score + ", marked=" + marked + ", quesid=" + quesid + "]";
	}

}
