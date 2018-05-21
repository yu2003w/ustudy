package com.ustudy.exam.model.anssheet;

import java.io.Serializable;

/**
 * @author jared
 * 
 * paper list and corresponding images for specified question id
 *
 */
public class AnsPaper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8064062106123629598L;
	
	private String eeCode = null;
	private String eeName = null;
	private String clsName = null;
	
	// full paper contains both answer and mark images
	private String fullPaper = null;
	// answer and mark images for specified questions
	private String markAnsPaper = null;
	
	// maybe teacher want to comments the answer again, so paperid needed to update
	private long paperid = 0;
	
	private float score = 0;
	
	private float fscore = 0;

	public AnsPaper() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEeCode() {
		return eeCode;
	}

	public void setEeCode(String eeCode) {
		this.eeCode = eeCode;
	}

	public String getEeName() {
		return eeName;
	}

	public void setEeName(String eeName) {
		this.eeName = eeName;
	}

	public String getClsName() {
		return clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
	}

	public String getFullPaper() {
		return fullPaper;
	}

	public void setFullPaper(String fullPaper) {
		this.fullPaper = fullPaper;
	}

	public String getMarkAnsPaper() {
		return markAnsPaper;
	}

	public void setMarkAnsPaper(String markAnsPaper) {
		this.markAnsPaper = markAnsPaper;
	}

	public long getPaperid() {
		return paperid;
	}

	public void setPaperid(long paperid) {
		this.paperid = paperid;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public float getFscore() {
		return fscore;
	}

	public void setFscore(float fscore) {
		this.fscore = fscore;
	}

	@Override
	public String toString() {
		return "AnsPaper [eeCode=" + eeCode + ", eeName=" + eeName + ", clsName=" + clsName + ", fullPaper=" + fullPaper
				+ ", markAnsPaper=" + markAnsPaper + ", paperid=" + paperid + ", score=" + score + ", fscore=" + fscore
				+ "]";
	}

}
