package com.ustudy.cache;

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
public class MetaInfo {

	/*
	 * only need basic information about this question, assign mode. not sure whether other info needed.
	 */
	public QuesMeta getMetaTaskInfo(String quesid) {
		return null;
	}
	
	/*
	 * cache question meta information in memory
	 */
	public boolean cacheQuesMeta(QuesMeta qm) {
		return false;
	}
}
