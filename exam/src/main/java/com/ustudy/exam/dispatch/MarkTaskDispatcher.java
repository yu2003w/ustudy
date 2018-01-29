package com.ustudy.exam.dispatch;

import com.ustudy.exam.model.QuesMarkSum;

/**
 * @author jared
 * 
 */
public interface MarkTaskDispatcher {
	
	/**
	 * student papers assigned to login user for specific question
	 * task dispatch should be aware of dispatch mode, average, dynamic or ratio
	 * 
	 * @param quesid
	 * @param batchNum
	 *     -- number of papers required
	 * @return
	 */
	public QuesMarkSum retrieveQuesMarkSum(String quesid, int batchNum);
	
	
}
