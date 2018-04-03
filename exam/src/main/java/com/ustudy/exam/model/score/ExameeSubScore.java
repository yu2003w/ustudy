package com.ustudy.exam.model.score;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author jared
 * 
 * This class is almost the same as StudentScore, However, there is some differences.
 *
 */
public class ExameeSubScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5812989883709669198L;
	
	private int exameeId = 0;
	private String exameeNO = null;
	private String exameeName = null;
	private String clsName = null;
	
	// aggregated subject scores with format 'subname'-'score'-'rank'-'objscore'-'subscore'
	@JsonIgnore
	private String aggrscores = null;
	
	private List<DetailedSubScore> subScores = null;

	public ExameeSubScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExameeSubScore(int exameeId, String exameeNO, String exameeName, String clsName, String aggrscores) {
		super();
		this.exameeId = exameeId;
		this.exameeNO = exameeNO;
		this.exameeName = exameeName;
		this.clsName = clsName;
		this.aggrscores = aggrscores;
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

	public String getExameeName() {
		return exameeName;
	}

	public void setExameeName(String exameeName) {
		this.exameeName = exameeName;
	}

	public String getClsName() {
		return clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
	}

	public String getAggrscores() {
		return aggrscores;
	}

	/**
	 * need to parse and construct objects subScores
	 * @param aggrscores
	 */
	public void setAggrscores(String aggrscores) {
		this.aggrscores = aggrscores;
		if (this.aggrscores != null && !this.aggrscores.isEmpty()) {
			String []ssL = this.aggrscores.split(",");
			for (String sc: ssL) {
				String [] paras = sc.split("-");
				if (paras != null && paras.length == 7) {
					if (this.subScores == null) {
						this.subScores = new ArrayList<DetailedSubScore>();
					}
					subScores.add(new DetailedSubScore(Integer.valueOf(paras[0]), paras[1],  
							paras[2].compareTo("NULL") == 0? -1: Float.valueOf(paras[2]), 
							paras[3].compareTo("NULL") == 0? -1: Integer.valueOf(paras[3]),
							paras[4].compareTo("NULL") == 0? -1: Float.valueOf(paras[4]),
							paras[5].compareTo("NULL") == 0? -1: Float.valueOf(paras[5]),
							paras[6]));

				}
			}
		}
	}

	public List<DetailedSubScore> getSubScores() {
		return subScores;
	}

	public void setSubScores(List<DetailedSubScore> subScores) {
		this.subScores = subScores;
	}

	@Override
	public String toString() {
		return "ExameeSubScore [exameeId=" + exameeId + ", exameeNO=" + exameeNO + ", exameeName=" + exameeName
				+ ", clsName=" + clsName + ", aggrscores=" + aggrscores + ", subScores=" + subScores + "]";
	}

}
