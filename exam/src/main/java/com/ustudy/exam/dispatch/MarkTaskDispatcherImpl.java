package com.ustudy.exam.dispatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ustudy.exam.model.QuesMarkSum;

public class MarkTaskDispatcherImpl implements MarkTaskDispatcher {

	private static final Logger logger = LogManager.getLogger(MarkTaskDispatcherImpl.class);
	
	@Override
	public QuesMarkSum retrieveQuesMarkSum(String quesid, int batchNum) {
	
		logger.debug("retrieveQuesMarkSum(), prepare to dispatch " + batchNum + 
				" of student papers for " + quesid);
		
		return null;
	}
	
}
