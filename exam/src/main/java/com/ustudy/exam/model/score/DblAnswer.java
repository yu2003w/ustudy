package com.ustudy.exam.model.score;

import java.io.Serializable;

public class DblAnswer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6851290563699586239L;

	private int quesId = 0;
	private float score = 0;
	private String teacName = null;
	private String markMode = null;
	private float scoreDiff  = 0;
	private boolean isFinal = false;
	private int pageno = 0;
	private int x = 0;
	private int y = 0;
	private int w = 0;
	private int h = 0;
	
	public DblAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DblAnswer(int quesId, float score, String teacName, String markMode, float scoreDiff, boolean isFinal, int pageno, int x, int y, int w, int h) {
		super();
		this.quesId = quesId;
		this.score = score;
		this.teacName = teacName;
		this.markMode = markMode;
		this.scoreDiff = scoreDiff;
		this.isFinal = isFinal;
		this.pageno = pageno;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public int getQuesId() {
		return quesId;
	}

	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getTeacName() {
		return teacName;
	}

	public void setTeacName(String teacName) {
		this.teacName = teacName;
	}

	public String getMarkMode() {
		return markMode;
	}

	public void setMarkMode(String markMode) {
		this.markMode = markMode;
	}

	public float getScoreDiff() {
		return scoreDiff;
	}

	public void setScoreDiff(float scoreDiff) {
		this.scoreDiff = scoreDiff;
	}

	public boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(boolean isFinal) {
		this.isFinal = isFinal;
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
		return "DblAnswer [quesId=" + quesId + ", score=" + score + ", teacName=" + teacName + ", markMode=" + markMode + ", scoreDiff=" + scoreDiff + ", isFinal=" + isFinal + ", pageno=" + pageno + ", x=" + x + ", y=" + y + ", w=" + w + ", h=" + h
				+ "]";
	}
	
}
