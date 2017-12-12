package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ustudy.exam.model.cache.FirstMarkImgRecord;

/**
 * @author jared
 * 
 * Front end need information about question area and corresponding answer images to compose final pictures.
 * So position information is retrieved from ques_area and mark/answer image information from answer_img.
 * 
 */
public class ImgRegion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4273191630745702664L;

	// origin images corresponding to the question block
	private String quesImg = null;
	
	// answer images corresponding to the pageno in the quesion block
	private String ansImg = null;
	
	private int pageno = 0;
	
	@JsonProperty("x")
	private int posx = -1;
	@JsonProperty("y")
	private int posy = -1;
	@JsonProperty("w")
	private int width = -1;
	@JsonProperty("h")
	private int height = -1;

	// following data only available in marked result
	private String ansMarkImg = null;
	private String ansMarkImgData = null;
	private String markImg = null;
	private String markImgData = null;
	
	@JsonProperty("markImgRecords")
	private FirstMarkImgRecord[] firstMarkImgs = null;
	
	public ImgRegion() {
		super();
		// TODO Auto-generated constructor stub
		firstMarkImgs = new FirstMarkImgRecord[2];
	}

	public ImgRegion(String quesImg, int pageno, int posx, int posy, int width, int height) {
		super();
		this.quesImg = quesImg;
		this.pageno = pageno;
		this.posx = posx;
		this.posy = posy;
		this.width = width;
		this.height = height;
	}

	public ImgRegion(String quesImg, String ansImg, int pageno, int posx, int posy, int width, int height,
			String ansMarkImg, String ansMarkImgData, String markImg, String markImgData) {
		super();
		this.quesImg = quesImg;
		this.ansImg = ansImg;
		this.pageno = pageno;
		this.posx = posx;
		this.posy = posy;
		this.width = width;
		this.height = height;
		this.ansMarkImg = ansMarkImg;
		this.ansMarkImgData = ansMarkImgData;
		this.markImg = markImg;
		this.markImgData = markImgData;
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

	public String getAnsMarkImg() {
		return ansMarkImg;
	}

	public void setAnsMarkImg(String ansMarkImg) {
		this.ansMarkImg = ansMarkImg;
	}

	public String getAnsMarkImgData() {
		return ansMarkImgData;
	}

	public void setAnsMarkImgData(String ansMarkImgData) {
		this.ansMarkImgData = ansMarkImgData;
	}

	public String getMarkImg() {
		return markImg;
	}

	public void setMarkImg(String markImg) {
		this.markImg = markImg;
	}

	public String getMarkImgData() {
		return markImgData;
	}

	public void setMarkImgData(String markImgData) {
		this.markImgData = markImgData;
	}

	public FirstMarkImgRecord[] getFirstMarkImgs() {
		return firstMarkImgs;
	}

	public void setFirstMarkImgs(FirstMarkImgRecord[] firstMarkImgs) {
		this.firstMarkImgs = firstMarkImgs;
	}

	@Override
	public String toString() {
		return "ImgRegion [quesImg=" + quesImg + ", ansImg=" + ansImg + ", pageno=" + pageno + ", posx=" + posx
				+ ", posy=" + posy + ", width=" + width + ", height=" + height + ", ansMarkImg=" + ansMarkImg
				+ ", ansMarkImgData=" + ansMarkImgData + ", markImg=" + markImg + ", markImgData=" + markImgData
				+ ", firstMarkImgs=" + Arrays.toString(firstMarkImgs) + "]";
	}

}
