package com.ustudy.exam.model.statics;

import java.io.Serializable;

public class TeaStatics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1512988241523162416L;
	
    // score for all marked papers	
	private String score = null;
	
	private String marked = null;
	
	private String quesid = null;

	public TeaStatics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeaStatics(String score, String marked, String quesid) {
		super();
		this.score = score;
		this.marked = marked;
		this.quesid = quesid;
	}

	public String getMarked() {
		return marked;
	}

	public void setMarked(String marked) {
		this.marked = marked;
	}

	public String getQuesid() {
		return quesid;
	}

	public void setQuesid(String quesid) {
		this.quesid = quesid;
	}

	public String calAverageS() {
		return String.valueOf(Float.valueOf(score)/Integer.valueOf(marked));
	}

	@Override
	public String toString() {
		return "TeaStatics [score=" + score + ", marked=" + marked + ", quesid=" + quesid + "]";
	}

}
