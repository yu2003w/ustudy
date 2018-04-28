package com.ustudy.exam.model.score;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author jared
 * 
 * For subject question, it's a little different from object question as it may involve double mark.
 *
 */
public class SubjectQuesScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7948346670967746019L;
	
	private String quesno = null;
	private float score = 0;
	
	@JsonIgnore
	private String aggrscore = null;

	public SubjectQuesScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectQuesScore(String quesno, String aggrscore) {
		super();
		this.quesno = quesno;
		this.aggrscore = aggrscore;
	}

	public String getQuesno() {
		return quesno;
	}

	public void setQuesno(String quesno) {
		this.quesno = quesno;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getAggrscore() {
		return aggrscore;
	}

	// calculate score for subject question, maybe it involve double mark
	public void setAggrscore(String aggrscore) {
		this.aggrscore = aggrscore;
		if (this.aggrscore != null && this.aggrscore.length() > 0) {
			String [] datas = this.aggrscore.split(",");
			if (datas != null) {
				if (datas.length == 1) {
					String []ss = datas[0].split("-");
					if (ss != null && ss.length == 2)
						this.score = Float.valueOf(ss[0]);
				}
				else if (datas.length == 2) {
					String []ss = datas[0].split("-");
					if (ss != null && ss.length == 2)
						this.score = Float.valueOf(ss[0]);
					ss = datas[1].split("-");
					if (ss != null && ss.length == 2)
						this.score += Float.valueOf(ss[0]);
					this.score /= 2;
				}
				else if (datas.length == 3) {
					for (String data: datas) {
						String []ss = data.split("-");
						if (ss != null && ss.length == 2 && Integer.valueOf(ss[1]) == 1)
							this.score = Float.valueOf(ss[0]);
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		return "SubjectQuesScore [quesno=" + quesno + ", score=" + score + ", aggrscore=" + aggrscore + "]";
	}

}
