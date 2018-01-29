package com.ustudy.exam.service.impl.cache;

import java.io.Serializable;

public class TeaMarkInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1878572067059983680L;

	private int seq = -1;
	private int num = -1;
	
	public TeaMarkInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeaMarkInfo(int seq, int num) {
		super();
		this.seq = seq;
		this.num = num;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "TeaMarkInfo [seq=" + seq + ", num=" + num + "]";
	}
	
}
