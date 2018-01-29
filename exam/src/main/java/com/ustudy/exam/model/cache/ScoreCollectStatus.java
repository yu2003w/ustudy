package com.ustudy.exam.model.cache;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreCollectStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -867395786579174593L;

	// information indicated whether egs specific score is calculated
	private ConcurrentHashMap<Integer, Boolean> scoreColStatus = null;

	public ScoreCollectStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScoreCollectStatus(ConcurrentHashMap<Integer, Boolean> scoreColStatus) {
		super();
		this.scoreColStatus = scoreColStatus;
	}

	public void setScoreColStatus(ConcurrentHashMap<Integer, Boolean> scoreColStatus) {
		this.scoreColStatus = scoreColStatus;
	}

	public ConcurrentHashMap<Integer, Boolean> getScoreColStatus() {
		return scoreColStatus;
	}

	@Override
	public String toString() {
		return "ScoreCache [scoreColStatus=" + scoreColStatus + "]";
	}
	
	
}
