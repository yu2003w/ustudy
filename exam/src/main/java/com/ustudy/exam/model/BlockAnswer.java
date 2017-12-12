package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ustudy.exam.model.cache.FirstMarkRecord;


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
	
	private boolean isfinal = false;
	
	private List<SingleAnswer> steps = null;
	
	@JsonProperty("markRecords")
	private FirstMarkRecord[] markRec= null;
	
	private List<ImgRegion> regions = null;
	
	private String paperImg = null;
	
	public BlockAnswer() {
		super();
		// TODO Auto-generated constructor stub
		markRec = new FirstMarkRecord[2];
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

	public boolean isIsfinal() {
		return isfinal;
	}

	public void setIsfinal(boolean isfinal) {
		this.isfinal = isfinal;
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

	public FirstMarkRecord[] getMarkRec() {
		return markRec;
	}

	public void setMarkRec(FirstMarkRecord[] markRec) {
		this.markRec = markRec;
	}

	@Override
	public String toString() {
		return "BlockAnswer [id=" + id + ", questionName=" + questionName + ", questionType=" + questionType
				+ ", markMode=" + markMode + ", isMarked=" + isMarked + ", paperId=" + paperId + ", quesid=" + quesid
				+ ", mflag=" + mflag + ", isProblemPaper=" + isProblemPaper + ", fullscore=" + fullscore + ", score="
				+ score + ", isfinal=" + isfinal + ", steps=" + steps + ", markRec=" + Arrays.toString(markRec)
				+ ", regions=" + regions + ", paperImg=" + paperImg + "]";
	}
	
}
