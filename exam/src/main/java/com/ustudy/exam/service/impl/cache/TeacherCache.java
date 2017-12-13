package com.ustudy.exam.service.impl.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.TeacherMapper;

@Service
public class TeacherCache {

	private final static Logger logger = LogManager.getLogger(TeacherCache.class);
	
	@Autowired
	private TeacherMapper teaM;
	
	@Autowired
	private RedisTemplate<String, String> teaNameC;
	
	public String getTeaName(String tid) {
		String teaN = teaNameC.opsForValue().get(tid);
		
		if (teaN == null) {
			synchronized (tid) {
				teaN = teaNameC.opsForValue().get(tid);
				if (teaN == null) {
					// need to retrieve from database and cache in memory
					teaN = teaM.findNameById(tid);
					if (teaN == null || teaN.isEmpty()) {
						logger.error("getTeaName(), failed to get teacher name for " + tid);
						throw new RuntimeException("getTeaName(), failed to get teacher name for " + tid);
					}
					teaNameC.opsForValue().set(tid, teaN);
				}
			}
		}
		
		return teaN;
	}
}
