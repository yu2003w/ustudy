package com.ustudy.exam.model.score;

import java.io.Serializable;

public class UncommonAnswer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6851290563699586249L;

	private Long quesId = 0L;
	private int pageno = 0;
	private int x = 0;
	private int y = 0;
	private int w = 0;
	private int h = 0;
	private int rank = 0;
	private String ansMarkImg = "";

	public UncommonAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UncommonAnswer(Long quesId, int pageno, int x, int y, int w, int h, int rank, String ansMarkImg) {
		super();
		this.quesId = quesId;
		this.pageno = pageno;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rank = rank;
		this.ansMarkImg = ansMarkImg;
	}

	public Long getQuesId() {
		return quesId;
	}

	public void setQuesId(Long quesId) {
		this.quesId = quesId;
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getAnsMarkImg() {
		return ansMarkImg;
	}

	public void setAnsMarkImg(String ansMarkImg) {
		this.ansMarkImg = ansMarkImg;
	}

	@Override
	public String toString() {
		return "DblAnswer [quesId=" + quesId + ", pageno=" + pageno + ", x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + ", rank=" + rank + ", ansMarkImg=" + ansMarkImg
				+ "]";
	}
}
