package com.ustudy.exam.service.impl.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ustudy.exam.model.QuesMeta;

/**
 * @author jared
 *
 * Some read frequently information should be cached in memory provided by redis.
 * Applications should retrieve the information from redis firstly, if not found, query from database
 * 
 * ??? Need to sync from redis and database (currently unimplemented)
 * 
 */
@Service
public class MetaInfo {
	
	private static final Logger logger = LogManager.getLogger(MetaInfo.class);
	
	@Autowired
	private RedisTemplate<String, Object> redisT;

	/*
	 * only need basic information about this question, assign mode. not sure whether other info needed.
	 */
	public QuesMeta getMetaTaskInfo(String quesid) {
		logger.debug("getMetaTaskInfo() hitted");
		QuesMeta qm = new QuesMeta("001", "auto");
		
		redisT.opsForValue().set("ques", qm);
		logger.debug("getMetaTaskInfo(), stored data in redis");
		return null;
	}
	
	/*
	 * cache question meta information in memory
	 */
	public boolean cacheQuesMeta(QuesMeta qm) {
		return false;
	}
}
