package com.ustudy.exam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.ExamResultMapper;
import com.ustudy.exam.model.ExamResult;
import com.ustudy.exam.service.ExamResultService;

@Service
public class ExamResultServiceImpl implements ExamResultService {

	@Autowired
	private ExamResultMapper erM;
	
	@Autowired
	private RedisTemplate<String, ExamResult> examRT;

	public ExamResultMapper getErM() {
		return erM;
	}

	public void setErM(ExamResultMapper erM) {
		this.erM = erM;
	}

	@Override
	public List<ExamResult> getAllResult() {
		List<ExamResult> res = this.erM.getAllResult();
		ValueOperations<String, ExamResult> op = examRT.opsForValue();
		
		for (ExamResult item:res) {
			op.set("hello", item);
			if (!examRT.hasKey(String.valueOf(item.getId()))) {
				op.set(String.valueOf(item.getId()), item);
			}
		}
		return res;
	}

	
}
