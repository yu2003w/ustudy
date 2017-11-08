package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	
	@JsonProperty("answerType")
	private String mflag = null;
	
	private int fullscore = 0;
	// real score for this question block
	private int score = 0;
	List<SingleAnswer> steps = null;
	
	private String paperImg = null;
	
	private String answerImg = null;
	private String answerImgData = null;
	
	private String markImg = null;
	private String markImgData = null;
	
	@JsonIgnore
	private int score1 = 0;
	@JsonIgnore
	private int score2 = 0;
	@JsonIgnore
	private int score3 = 0;
	
	@JsonIgnore
	private String answerImg1 = null;
	@JsonIgnore
	private String markImg1 = null;
	@JsonIgnore
	private String answerImg2 = null;
	@JsonIgnore
	private String markImg2 = null;
	@JsonIgnore
	private String answerImg3 = null;
	@JsonIgnore
	private String markImg3 = null;

	@JsonIgnore
	private String teacid1 = null;
	@JsonIgnore
	private String teacid2 = null;
	@JsonIgnore
	private String teacid3 = null;
	
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

	public BlockAnswer(boolean isMarked, String mflag, String paperImg, String answerImg, String answerImgData, String markImg,
			String markImgData, String answerImg1, String markImg1, String answerImg2, String markImg2, String answerImg3,
			String markImg3) {
		super();
		this.isMarked = isMarked;
		this.mflag = mflag;
		this.paperImg = paperImg;
		this.answerImg = answerImg;
		this.answerImgData = answerImgData;
		this.markImgData = markImgData;
		this.markImg = markImg;
		this.answerImg1 = answerImg1;
		this.markImg1 = markImg1;
		this.answerImg2 = answerImg2;
		this.markImg2 = markImg2;
		this.answerImg3 = answerImg3;
		this.markImg3 = markImg3;
		this.answerImg = this.answerImg1;
		this.markImg = this.markImg1;
	}

	public BlockAnswer(boolean isMarked, String mflag, String paperImg, String answerImg, String answerImgData, String markImg, String markImgData) {
		super();
		this.isMarked = isMarked;
		this.mflag = mflag;
		this.paperImg = paperImg;
		this.answerImg = answerImg;
		this.answerImgData = answerImgData;
		this.markImg = markImg;
		this.markImgData = markImgData;
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

	public String getAnswerImg() {
		return answerImg;
	}

	public void setAnswerImg(String answerImg) {
		this.answerImg = answerImg;
	}

	public String getAnswerImgData() {
		return answerImgData;
	}

	public void setAnswerImgData(String answerImgData) {
		this.answerImgData = answerImgData;
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

	public String getPaperImg() {
		return paperImg;
	}

	public void setPaperImg(String paperImg) {
		this.paperImg = paperImg;
	}

	public String getAnswerImg1() {
		return answerImg1;
	}

	public void setAnswerImg1(String answerImg1) {
		this.answerImg1 = answerImg1;
	}

	public String getMarkImg1() {
		return markImg1;
	}

	public void setMarkImg1(String markImg1) {
		this.markImg1 = markImg1;
	}

	public String getAnswerImg2() {
		return answerImg2;
	}

	public void setAnswerImg2(String answerImg2) {
		this.answerImg2 = answerImg2;
	}

	public String getMarkImg2() {
		return markImg2;
	}

	public void setMarkImg2(String markImg2) {
		this.markImg2 = markImg2;
	}

	public String getAnswerImg3() {
		return answerImg3;
	}

	public void setAnswerImg3(String answerImg3) {
		this.answerImg3 = answerImg3;
	}

	public String getMarkImg3() {
		return markImg3;
	}

	public void setMarkImg3(String markImg3) {
		this.markImg3 = markImg3;
	}

	public int getScore1() {
		return score1;
	}

	public void setScore1(int score1) {
		this.score1 = score1;
	}

	public int getScore2() {
		return score2;
	}

	public void setScore2(int score2) {
		this.score2 = score2;
	}

	public int getScore3() {
		return score3;
	}

	public void setScore3(int score3) {
		this.score3 = score3;
	}

	public String getTeacid1() {
		return teacid1;
	}

	public void setTeacid1(String teacid1) {
		this.teacid1 = teacid1;
	}

	public String getTeacid2() {
		return teacid2;
	}

	public void setTeacid2(String teacid2) {
		this.teacid2 = teacid2;
	}

	public String getTeacid3() {
		return teacid3;
	}

	public void setTeacid3(String teacid3) {
		this.teacid3 = teacid3;
	}

	@Override
	public String toString() {
		return "BlockAnswer [questionName=" + questionName + ", questionType=" + questionType + ", markMode=" + markMode
				+ ", isMarked=" + isMarked + ", paperId=" + paperId + ", quesid=" + quesid + ", mflag=" + mflag
				+ ", fullscore=" + fullscore + ", score=" + score + ", steps=" + steps + ", paperImg=" + paperImg
				+ ", answerImg=" + answerImg + ", markImg=" + markImg + ", score1=" + score1 + ", score2=" + score2
				+ ", score3=" + score3 + ", answerImg1=" + answerImg1 + ", markImg1=" + markImg1 + ", answerImg2="
				+ answerImg2 + ", markImg2=" + markImg2 + ", answerImg3=" + answerImg3 + ", markImg3=" + markImg3
				+ ", teacid1=" + teacid1 + ", teacid2=" + teacid2 + ", teacid3=" + teacid3 + "]";
	}
	
}
