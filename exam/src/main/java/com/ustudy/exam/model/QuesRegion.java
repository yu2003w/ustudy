package com.ustudy.exam.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jared
 * 
 * Only basic information needed
 * 
 */
public class QuesRegion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4273191630745702664L;

	private String fileName = null;
	
	@JsonProperty("x")
	private int posx = -1;
	@JsonProperty("y")
	private int posy = -1;
	@JsonProperty("w")
	private int width = -1;
	@JsonProperty("h")
	private int height = -1;
	
	public QuesRegion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuesRegion(String fileName, int posx, int posy, int width, int height) {
		super();
		this.fileName = fileName;
		this.posx = posx;
		this.posy = posy;
		this.width = width;
		this.height = height;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
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

	@Override
	public String toString() {
		return "QuesRegion [fileName=" + fileName + ", posx=" + posx + ", posy=" + posy + ", width=" + width
				+ ", height=" + height + "]";
	}
	
	
}
