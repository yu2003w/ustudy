package com.ustudy.exam.model.score;

import java.io.Serializable;

/**
 * @author jared
 *
 * Class defined for child score of subject questions in a subject which has multiple subjects
 */
public class ChildSubScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1171587440098680802L;
	
	private long eid = 0;
	// score with format 'isfinal'-'score' to group values of double mark mode
	private String score = null;
	
	// score for this question after parsing member "this.score"
	private float realScore = 0;
	private String markmode = null;
	private String branch = null;
	
	public ChildSubScore() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public long getEid() {
		return eid;
	}

	public void setEid(long eid) {
		this.eid = eid;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
		if (score != null && !score.isEmpty()) {
			String [] sL = this.score.split(",");
			if (sL != null) {
				if (sL.length == 1) {
					// score for single mark
					String [] paras = sL[0].split("-");
					if (paras != null && paras.length == 2)
						this.realScore = Float.valueOf(paras[1]);
				}
				else if (sL.length == 2) {
					// score for double mark but without final mark
					for (String sco : sL) {
						String [] paras = sco.split("-");
						if (paras != null && paras.length == 2)
							this.realScore += Float.valueOf(paras[1]);
					}
					this.realScore /= 2;
					this.realScore = (int)this.realScore;
				}
				else if (sL.length == 3) {
					// score for double mark with final mark
					for (String sco :sL) {
						String []paras = sco.split("-");
						if (paras != null && paras.length == 2) {
							if (Integer.valueOf(paras[0]) == 1) {
								this.realScore += Float.valueOf(paras[1]);
								break;
							}
						}
					}
				}
			}
		}
	}

	public float getRealScore() {
		return realScore;
	}

	public void setRealScore(float realScore) {
		this.realScore = realScore;
	}

	public String getMarkmode() {
		return markmode;
	}

	public void setMarkmode(String markmode) {
		this.markmode = markmode;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Override
	public String toString() {
		return "ChildSubScore [eid=" + eid + ", score=" + score + ", realScore=" + realScore + ", markmode=" + markmode
				+ ", branch=" + branch + "]";
	}	
	
}
