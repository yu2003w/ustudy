package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author jared
 * 
 * Student answer for question blocks
 *
 */
public class BlockAnswer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7140959037803707897L;

	private String questionName = null;
	private String questionType = null;
	
	@JsonProperty("markStyle")
	private String markMode = null;
	@JsonProperty("isMarked")
	private boolean isMarked = false;
	
	private String paperId = null;
	private String quesid = null;
	
	@JsonProperty("markFlag")
	private String mflag = null;
	
	@JsonProperty("isProblemPaper")
	private boolean isProbP = false;
	
	private int fullscore = 0;
	// real score for this question block
	private int score = 0;
	List<SingleAnswer> steps = null;
	
	@JsonProperty("paperImg")
	private String answerImg = null;
	
	private String markImg = null;
	

	public BlockAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BlockAnswer(String questionName, String questionType, String quesid, String markMode, boolean isMarked,
			String paperId, String mflag, int fullscore, int score) {
		super();
		this.questionName = questionName;
		this.questionType = questionType;
		this.quesid = quesid;
		this.markMode = markMode;
		this.isMarked = isMarked;
		this.paperId = paperId;
		this.mflag = mflag;
		this.fullscore = fullscore;
		this.score = score;
	}

	public BlockAnswer(boolean isMarked, String mflag, boolean isProbP, String answerImg, String markImg) {
		super();
		this.isMarked = isMarked;
		this.mflag = mflag;
		this.isProbP = isProbP;
		this.answerImg = answerImg;
		this.markImg = markImg;
	}

	public void setMetaInfo(String questionName, String questionType, String markMode, int score) {
		this.questionName = questionName;
		this.questionType = questionType;
		this.markMode = markMode;
		this.fullscore = score;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuesid() {
		return quesid;
	}

	public void setQuesid(String quesid) {
		this.quesid = quesid;
	}

	public String getMarkMode() {
		return markMode;
	}

	public void setMarkMode(String markMode) {
		this.markMode = markMode;
	}

	public boolean isMarked() {
		return isMarked;
	}

	public void setMarked(boolean isMarked) {
		this.isMarked = isMarked;
	}

	public String getMflag() {
		return mflag;
	}

	public void setMflag(String mflag) {
		this.mflag = mflag;
	}

	public int getFullscore() {
		return fullscore;
	}

	public void setFullscore(int fullscore) {
		this.fullscore = fullscore;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<SingleAnswer> getSteps() {
		return steps;
	}

	public void setSteps(List<SingleAnswer> steps) {
		this.steps = steps;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public boolean isProbP() {
		return isProbP;
	}

	public void setProbP(boolean isProbP) {
		this.isProbP = isProbP;
	}

	public String getAnswerImg() {
		return answerImg;
	}

	public void setAnswerImg(String answerImg) {
		this.answerImg = answerImg;
	}

	public String getMarkImg() {
		return markImg;
	}

	public void setMarkImg(String markImg) {
		this.markImg = markImg;
	}

	@Override
	public String toString() {
		return "BlockAnswer [questionName=" + questionName + ", questionType=" + questionType + ", markMode=" + markMode
				+ ", isMarked=" + isMarked + ", paperId=" + paperId + ", quesid=" + quesid + ", mflag=" + mflag
				+ ", isProbP=" + isProbP + ", fullscore=" + fullscore + ", score=" + score + ", steps=" + steps
				+ ", answerImg=" + answerImg + ", markImg=" + markImg + "]";
	}
	
}
