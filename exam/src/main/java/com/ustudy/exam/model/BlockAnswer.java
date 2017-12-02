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

	@JsonIgnore
	private int id;
	
	private String questionName = null;
	private String questionType = null;
	
	@JsonProperty("markStyle")
	private String markMode = null;
	@JsonProperty("isMarked")
	private boolean isMarked = false;
	
	private String paperId = null;
	private String quesid = null;
	
	@JsonProperty("answerType")
	private String mflag = "NONE";
	
	private boolean isProblemPaper = false;
	
	private String fullscore = "";
	// real score for this question block
	private String score = "";
	private List<SingleAnswer> steps = null;
	
	private List <ImgRegion> regions = null;
	
	private String paperImg = null;
	
	@JsonIgnore
	private int score1 = 0;
	@JsonIgnore
	private int score2 = 0;
	@JsonIgnore
	private int score3 = 0;

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
			String paperId, String mflag, String fullscore, String score) {
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

	public BlockAnswer(boolean isMarked, String mflag, String paperImg) {
		super();
		this.isMarked = isMarked;
		this.mflag = mflag;
		this.paperImg = paperImg;
	}

	public void setMetaInfo(String questionName, String questionType, String markMode, String score) {
		this.questionName = questionName;
		this.questionType = questionType;
		this.markMode = markMode;
		this.fullscore = score;
	}

	public void setBasicInfo(String paperid, String quesid, String paperimg) {
		this.paperId = paperid;
		this.quesid = quesid;
		this.paperImg = paperimg;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isProblemPaper() {
		return isProblemPaper;
	}

	public void setProblemPaper(boolean isProblemPaper) {
		this.isProblemPaper = isProblemPaper;
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

	public String getFullscore() {
		return fullscore;
	}

	public void setFullscore(String fullscore) {
		this.fullscore = fullscore;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
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

	public List<ImgRegion> getRegions() {
		return regions;
	}

	public void setRegions(List<ImgRegion> regions) {
		this.regions = regions;
	}

	public String getPaperImg() {
		return paperImg;
	}

	public void setPaperImg(String paperImg) {
		this.paperImg = paperImg;
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
		return "BlockAnswer [id=" + id + ", questionName=" + questionName + ", questionType=" + questionType
				+ ", markMode=" + markMode + ", isMarked=" + isMarked + ", paperId=" + paperId + ", quesid=" + quesid
				+ ", mflag=" + mflag + ", isProblemPaper=" + isProblemPaper + ", fullscore=" + fullscore + ", score="
				+ score + ", steps=" + steps + ", regions=" + regions + ", paperImg=" + paperImg + ", score1=" + score1
				+ ", score2=" + score2 + ", score3=" + score3 + ", teacid1=" + teacid1 + ", teacid2=" + teacid2
				+ ", teacid3=" + teacid3 + "]";
	}
	
}
