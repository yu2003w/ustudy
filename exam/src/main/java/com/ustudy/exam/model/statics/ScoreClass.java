package com.ustudy.exam.model.statics;

import java.io.Serializable;
import java.util.List;

/**
 * @author jared
 * 
 * Class Score object
 *
 */
public class ScoreClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -686311844729437128L;
	
	private int aveScore = 0;
	private int rank = 0;
	
	private List<ScoreSubjectCls> subScore = null;

}
