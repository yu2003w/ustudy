package com.ustudy.exam.model.template;

import java.io.Serializable;

public class QuesRegion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -146944077866773466L;

	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;
	private int bottom = 0;
	private int right = 0;
	
	public QuesRegion() {
		super();
		// TODO Auto-generated constructor stub
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "QuesRegion [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", bottom=" + bottom
				+ ", right=" + right + "]";
	}
	
}
