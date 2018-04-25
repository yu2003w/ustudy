package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.List;

public class AnswerSet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8565239437741005056L;

	private List<RefAnswer> refAnswers = null;
	private List<QuesAnswer> quesAnswers = null;
	private List<MulScoreBox> checkBoxScores = null;
	
	public AnswerSet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<RefAnswer> getRefAnswers() {
		return refAnswers;
	}

	public void setRefAnswers(List<RefAnswer> refAnswers) {
		this.refAnswers = refAnswers;
	}

	public List<QuesAnswer> getQuesAnswers() {
		return quesAnswers;
	}

	public void setQuesAnswers(List<QuesAnswer> quesAnswers) {
		this.quesAnswers = quesAnswers;
	}

	public List<MulScoreBox> getCheckBoxScores() {
		return checkBoxScores;
	}

	public void setCheckBoxScores(List<MulScoreBox> checkBoxScores) {
		this.checkBoxScores = checkBoxScores;
	}

	@Override
	public String toString() {
		return "AnswerSet [refAnswers=" + refAnswers + ", quesAnswers=" + quesAnswers + ", checkBoxScores="
				+ checkBoxScores + "]";
	}
	
}
