package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author jared
 * 
 * This class is combination of questions which are sent by client to retrieve corresponding student papers.
 */
public class QuesComb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4000839750587564458L;

	@JsonProperty("questions")
	private List<QuesId> quesids = null;
	private int startSeq = -1;
	private int endSeq = -1;
	
	public QuesComb() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuesComb(List<QuesId> quesids, int startSeq, int endSeq) {
		super();
		this.quesids = quesids;
		this.startSeq = startSeq;
		this.endSeq = endSeq;
	}

	public void setQuesids(List<QuesId> quesids) {
		this.quesids = quesids;
	}

	public List<QuesId> getQuesids() {
		return quesids;
	}

	public int getStartSeq() {
		return startSeq;
	}

	public void setStartSeq(int startSeq) {
		this.startSeq = startSeq;
	}

	public int getEndSeq() {
		return endSeq;
	}

	public void setEndSeq(int endSeq) {
		this.endSeq = endSeq;
	}

	@Override
	public String toString() {
		return "QuesComb [quesids=" + quesids + ", startSeq=" + startSeq + ", endSeq=" + endSeq + "]";
	}
	
}
