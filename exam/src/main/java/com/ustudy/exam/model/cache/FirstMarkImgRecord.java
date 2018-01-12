package com.ustudy.exam.model.cache;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FirstMarkImgRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5118924491776210027L;
	
	@JsonIgnore
	private String teacid = null;
	
	@JsonIgnore
	private int pageno = -1;

	@JsonProperty("markImg")
	private String img = null;

	public FirstMarkImgRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FirstMarkImgRecord(String teacid, int pageno, String img) {
		super();
		this.teacid = teacid;
		this.pageno = pageno;
		this.img = img;
	}


	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTeacid() {
		return teacid;
	}

	public void setTeacid(String teacid) {
		this.teacid = teacid;
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	@Override
	public String toString() {
		return "FirstMarkImgRecord [teacid=" + teacid + ", pageno=" + pageno + ", img=" + img + "]";
	}
	
}
