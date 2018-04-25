package com.ustudy.exam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MulScoreBox implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6117462601635214904L;
	
	class SelectedScore {
		
		private int count = 0;
		private float score = 0;
		
		public SelectedScore(int count, float score) {
			super();
			this.count = count;
			this.score = score;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public float getScore() {
			return score;
		}

		public void setScore(float score) {
			this.score = score;
		}

		@Override
		public String toString() {
			return "SelectedScore [count=" + count + ", score=" + score + "]";
		}
		
	}
	
	// total correct count
	private int size = 0;
	
	private boolean seted = false;
	
	private List<SelectedScore> scores = null;

	public MulScoreBox(int size, String aggrScores) {
		super();
		this.size = size;
		// aggrScores with format as "1-0.0,2-0.0,3-0.0"
		if (aggrScores != null && aggrScores.length() > 0) {
			String []datas = aggrScores.split(",");
			if (datas != null && datas.length > 0) {
				for (String item : datas) {
					String [] paras = item.split("-");
					if (paras != null && paras.length == 2) {
						if (this.scores == null) 
							this.scores = new ArrayList<SelectedScore>();
						this.scores.add(new SelectedScore(Integer.valueOf(paras[0]), Float.valueOf(paras[1])));
						if (Float.valueOf(paras[1]) > 0) 
							this.seted = true;
					}
				}
			}
		}
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isSeted() {
		return seted;
	}

	public void setSeted(boolean seted) {
		this.seted = seted;
	}

	public List<SelectedScore> getScores() {
		return scores;
	}

	public void setScores(List<SelectedScore> scores) {
		this.scores = scores;
	}

	@Override
	public String toString() {
		return "MulScoreBox [size=" + size + ", seted=" + seted + ", scores=" + scores + "]";
	}

}
