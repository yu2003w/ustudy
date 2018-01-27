package com.ustudy.exam.service.impl.cache;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ustudy.exam.model.cache.ScoreCollectStatus;

@Service
public class ScoreCache {

	private static final Logger logger = LogManager.getLogger(ScoreCache.class);
	
	private static final String COL_STATUS = "colstatus";
	
	/**
	 * information as <egsid, boolean> to indicate whether score collect for specific egs finished
	 */
	@Autowired
	private RedisTemplate<String, ScoreCollectStatus> colStatus;
	
	public boolean getScoreColStatus(int egsid) {
		ScoreCollectStatus scs = colStatus.opsForValue().get(COL_STATUS);
		if (scs != null) {
			if (scs.getScoreColStatus() != null && 
				scs.getScoreColStatus().containsKey(Integer.valueOf(egsid))) {
				boolean ret = scs.getScoreColStatus().get(Integer.valueOf(egsid));
				logger.debug("getScoreColStatus(), score for egsid " + egsid + 
						(ret == false ? " is still running":" is collected"));
				return ret;
			}
		}
		return false;
		
	}
	
	public void setScoreColStatus(int egsid, boolean status) {
		ScoreCollectStatus scs = colStatus.opsForValue().get(COL_STATUS);
		if (scs == null) {
			scs = new ScoreCollectStatus(new ConcurrentHashMap<Integer, Boolean>());
			scs.getScoreColStatus().put(egsid, status);
		}
		else {
			if (scs.getScoreColStatus() == null) {
				scs.setScoreColStatus(new ConcurrentHashMap<Integer, Boolean>());
			}
			scs.getScoreColStatus().put(egsid, status);
		}
		colStatus.opsForValue().set(COL_STATUS, scs);
		logger.debug("setScoreColStatus(), score collected for egs->" + egsid + " finished");
		
	}
	
	
}
