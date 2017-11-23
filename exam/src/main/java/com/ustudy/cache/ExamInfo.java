package com.ustudy.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class ExamInfo {
	
	/**
	 * cache papers in memory
	 * Maybe not all parameters are needed, only cache basic info here
	 * @return
	 */
	public boolean cachePapers() {
		return false;
	}
}
