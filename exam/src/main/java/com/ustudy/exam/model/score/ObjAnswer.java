package com.ustudy.exam.model.score;

import java.io.Serializable;

public class ObjAnswer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6851290563699586235L;

	private int quesno = 0;
	private float score = 0;
	private String answer = null;
	private String type = null;
	private int pageno = 0;
	private int x = 0;
	private int y = 0;
	private int w = 0;
	private int h = 0;
	
	public ObjAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ObjAnswer(int quesno, float score, String answer, String type, int pageno, int x, int y, int w, int h) {
		super();
		this.quesno = quesno;
		this.score = score;
		this.answer = answer;
		this.type = type;
		this.pageno = pageno;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public int getQuesno() {
		return quesno;
	}

	public void setQuesno(int quesno) {
		this.quesno = quesno;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	@Override
	public String toString() {
		return "ObjAnswer [quesno=" + quesno + ", score=" + score + ", answer=" + answer + ", type=" + type + ", pageno=" + pageno + ", x=" + x + ", y=" + y + ", w=" + w + ", h=" + h
				+ "]";
	}
	
}
