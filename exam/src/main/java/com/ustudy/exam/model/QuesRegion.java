package com.ustudy.exam.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	// origin images corresponding to the question block
	private String quesImg = null;
	
	// answer images corresponding to the pageno in the quesion block
	private String ansImg = null;
	
	@JsonIgnore
	private int pageno = 0;
	
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

	public QuesRegion(String quesImg, int pageno, int posx, int posy, int width, int height) {
		super();
		this.quesImg = quesImg;
		this.pageno = pageno;
		this.posx = posx;
		this.posy = posy;
		this.width = width;
		this.height = height;
	}

	public QuesRegion(int posx, int posy, int width, int height) {
		super();
		this.posx = posx;
		this.posy = posy;
		this.width = width;
		this.height = height;
	}

	public String getQuesImg() {
		return quesImg;
	}

	public void setQuesImg(String fileName) {
		this.quesImg = fileName;
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

	public String getAnsImg() {
		return ansImg;
	}

	public void setAnsImg(String ansImg) {
		this.ansImg = ansImg;
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	@Override
	public String toString() {
		return "QuesRegion [quesImg=" + quesImg + ", ansImg=" + ansImg + ", pageno=" + pageno + ", posx=" + posx
				+ ", posy=" + posy + ", width=" + width + ", height=" + height + "]";
	}

}
