package com.ustudy.exam.model.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jared
 * 
 * Stored Teacher related information in memory cache
 *
 */
public class TeaCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2175206179893767045L;
	
	private Map<String, MarkStaticsCache> quesMarkStatus = null;

	public TeaCache() {
		super();
		// TODO Auto-generated constructor stub
		quesMarkStatus = new HashMap<String, MarkStaticsCache>();
	}

	public TeaCache(Map<String, MarkStaticsCache> quesMarkStatus) {
		super();
		this.quesMarkStatus = quesMarkStatus;
	}

	public Map<String, MarkStaticsCache> getQuesMarkStatus() {
		return quesMarkStatus;
	}

	public void setQuesMarkStatus(Map<String, MarkStaticsCache> quesMarkStatus) {
		this.quesMarkStatus = quesMarkStatus;
	}

	@Override
	public String toString() {
		return "TeaCache [quesMarkStatus=" + quesMarkStatus + "]";
	}
	
}
