package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author updated by jared on March 17, 2018.
 * 
 * Question and related settings including score, scorediff, duration and so on.
 *
 */
public class QuesAnswer implements Serializable {

	private static final long serialVersionUID = -8233762600675469685L;

	private Long id;
	private int quesno;
	private int startno;
	private int endno;
	private String type;
	private String branch;
	private int choiceNum;
	// score should be type of float, ie. 0.5, 1.5 and so on
	private float score;
	private String assignMode;
	private String scoreMode;
	
	private String teacOwner;
	private Long examGradeSubId;
	
	private List<QuesAnswerDiv> child;
	
	/**
	 * Added by Jared
	 * data member to hold question_step information with following format to make query more easily
	 * 'quesno'-'score'
	 * 
	 * in method for setting this field, need to parse this field to generate 'child'
	 */
	@JsonIgnore
	private String steps = null;	
	
	public QuesAnswer(){}
	
	public QuesAnswer(int quesno,int startno,int endno,String type,Long examGradeSubId) {
		this.quesno = quesno;
		this.startno = startno;
		this.endno = endno;
		this.type = type;
		this.examGradeSubId = examGradeSubId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuesno() {
		return quesno;
	}

	public void setQuesno(int quesno) {
		this.quesno = quesno;
	}

	public int getStartno() {
		return startno;
	}

	public void setStartno(int startno) {
		this.startno = startno;
	}

	public int getEndno() {
		return endno;
	}

	public void setEndno(int endno) {
		this.endno = endno;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
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

	public String getAssignMode() {
		return assignMode;
	}

	public void setAssignMode(String assignMode) {
		this.assignMode = assignMode;
	}

	public String getScoreMode() {
		return scoreMode;
	}

	public void setScoreMode(String scoreMode) {
		this.scoreMode = scoreMode;
	}

	public String getTeacOwner() {
		return teacOwner;
	}

	public void setTeacOwner(String teacOwner) {
		this.teacOwner = teacOwner;
	}

	public Long getExamGradeSubId() {
		return examGradeSubId;
	}

	public void setExamGradeSubId(Long examGradeSubId) {
		this.examGradeSubId = examGradeSubId;
	}

	public List<QuesAnswerDiv> getChild() {
		return child;
	}

	public void setChild(List<QuesAnswerDiv> child) {
		this.child = child;
	}

	public String getSteps() {
		return steps;
	}

	/**
	 * Steps is composed with the format 'quesno'-'score'
	 * @param steps
	 */
	public void setSteps(String steps) {
		this.steps = steps;
	}

	/**
	 * Need to check whether this answer setting for this question is completed or not
	 * 
	 * @return
	 */
	public boolean isValid() {
		boolean ret = true;
		if ((this.type == null || this.type.isEmpty()) || (this.branch == null || this.branch.isEmpty()) 
				|| this.score == 0 || 
				((this.type.compareTo("单选题") == 0 || this.type.compareTo("多选题") == 0) && this.choiceNum <= 0)) {
			ret = false;
		}
		
		// need to check steps to make sure sum of step scores equals to that of question score
		if (steps != null && !steps.isEmpty()) {
			float scoSum = 0;
			String[] sts = steps.split(",");
			for (String st: sts) {
				if (st != null && !st.isEmpty()) {
					String[] data = st.split("-");
					if (data.length == 2)
						scoSum += Float.valueOf(data[1]);
				}
			}
			if (scoSum != this.score) {
				ret = false;
			}
		}
		return ret;
	}
	
	@Override
	public String toString() {
		return "QuesAnswer [id=" + id + ", quesno=" + quesno + ", startno=" + startno + ", endno=" + endno + ", type="
				+ type + ", branch=" + branch + ", choiceNum=" + choiceNum + ", score=" + score + ", assignMode="
				+ assignMode + ", scoreMode=" + scoreMode + ", teacOwner=" + teacOwner + ", examGradeSubId="
				+ examGradeSubId + ", child=" + child + ", steps=" + steps + "]";
	}
	
}
