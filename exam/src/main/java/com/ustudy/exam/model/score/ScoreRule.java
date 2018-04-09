package com.ustudy.exam.model.score;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ustudy.exam.model.MultipleScoreSet;

/**
 * @author jared
 *
 * This class is very similar with RefAnswer, however, it doesn't equal with RefAnswer.
 * It includes information about RefAnswer, score, multiple_score_set and so on to calculate score
 * for students' object questions.
 * 
 */
public class ScoreRule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -726785212561023948L;

	private long egsId = 0;
	private long quesid = 0;
	private int quesno = 0;
	private String branch = null;
	private String answer = null;
	
	private String type = null;
	private int choiceNum = 0;
	private float score = 0;
	
	private String mulscoreset = null;
	private List<MultipleScoreSet> mulScoreSet = null;
	
	public ScoreRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getEgsId() {
		return egsId;
	}

	public void setEgsId(long egsId) {
		this.egsId = egsId;
	}

	public long getQuesid() {
		return quesid;
	}

	public void setQuesid(long quesid) {
		this.quesid = quesid;
	}

	public int getQuesno() {
		return quesno;
	}

	public void setQuesno(int quesNo) {
		this.quesno = quesNo;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getChoiceNum() {
		return choiceNum;
	}

	public void setChoiceNum(int choiceNum) {
		this.choiceNum = choiceNum;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getMulscoreset() {
		return mulscoreset;
	}

	public void setMulscoreset(String mulscoreset) {
		this.mulscoreset = mulscoreset;
		// parse multiple score set here
		if (this.mulscoreset != null && this.mulscoreset.length() > 0) {
		
			String []data = this.mulscoreset.split(",");
			for (String item: data) {
				String []paras = item.split("-");
				if (paras != null && paras.length == 3) {
					MultipleScoreSet mss = new MultipleScoreSet(Integer.valueOf(paras[0]), 
							Integer.valueOf(paras[1]), Float.valueOf(paras[2]));
					if (this.mulScoreSet == null) {
						this.mulScoreSet = new ArrayList<MultipleScoreSet>();
					}
					this.mulScoreSet.add(mss);
				}
			}
		}
		
	}

	public List<MultipleScoreSet> getMulScoreSet() {
		return mulScoreSet;
	}

	public void setMulScoreSet(List<MultipleScoreSet> mulScoreSet) {
		this.mulScoreSet = mulScoreSet;
	}

	@Override
	public String toString() {
		return "ScoreRule [egsId=" + egsId + ", quesid=" + quesid + ", quesno=" + quesno + ", branch=" + branch
				+ ", answer=" + answer + ", type=" + type + ", choiceNum=" + choiceNum + ", score=" + score
				+ ", mulscoreset=" + mulscoreset + ", mulScoreSet=" + mulScoreSet + "]";
	}
	
}
