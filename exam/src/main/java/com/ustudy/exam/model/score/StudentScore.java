package com.ustudy.exam.model.score;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2054059124472157556L;
	
	private int exameeId = 0;
	private String exameeNO = null;
	private int examId = 0;
	private String exameeName = null;
	private String schName = null;
	private String className = null;
	private float score = 0;
	private int rank = 0;
	
	// subject scores aggregated with format 'subject name' + '-' + 'score' + '-' + 'rank'
	@JsonIgnore
	private String aggrscore = null;
	
	@JsonProperty("scores")
	private List<SubScore> subScores = null;

	public StudentScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getExameeId() {
		return exameeId;
	}

	public void setExameeId(int exameeId) {
		this.exameeId = exameeId;
	}

	public String getExameeNO() {
		return exameeNO;
	}

	public void setExameeNO(String exameeNO) {
		this.exameeNO = exameeNO;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public String getExameeName() {
		return exameeName;
	}

	public void setExameeName(String exameeName) {
		this.exameeName = exameeName;
	}

	public String getSchName() {
		return schName;
	}

	public void setSchName(String schName) {
		this.schName = schName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public List<SubScore> getSubScores() {
		return subScores;
	}

	public void setSubScores(List<SubScore> subScores) {
		this.subScores = subScores;
	}

	public String getAggrscore() {
		return aggrscore;
	}

	public void setAggrscore(String aggrscore) {
		this.aggrscore = aggrscore;
		//TODO: need to parse aggrescore here
		if (this.aggrscore != null && !this.aggrscore.isEmpty()) {
			//splist subscores firstly
			String []scL = this.aggrscore.split(",");
			for (String sc:scL) {
				String []props = sc.split("-");
				if (props != null && props.length == 3) {
					SubScore ssc = new SubScore(props[0], Float.valueOf(props[1]), Integer.valueOf(props[2]));
					if (this.subScores == null) {
						this.subScores = new ArrayList<SubScore>();
					}
					this.subScores.add(ssc);
				}
			}
		}
		
	}

	@Override
	public String toString() {
		return "StudentScore [exameeId=" + exameeId + ", exameeNO=" + exameeNO + ", examId=" + examId + ", exameeName="
				+ exameeName + ", schName=" + schName + ", className=" + className + ", score=" + score + ", rank="
				+ rank + ", subScores=" + subScores + "]";
	}

}
