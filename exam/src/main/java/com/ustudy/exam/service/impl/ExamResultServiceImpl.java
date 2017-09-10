package com.ustudy.exam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.ExamResultMapper;
import com.ustudy.exam.model.ExamResult;
import com.ustudy.exam.service.ExamResultService;

@Service
public class ExamResultServiceImpl implements ExamResultService {

	@Autowired
	private ExamResultMapper erM;
	
	
	public ExamResultMapper getErM() {
		return erM;
	}

	public void setErM(ExamResultMapper erM) {
		this.erM = erM;
	}

	@Override
	public List<ExamResult> getAllResult() {
		
		return this.erM.getAllResult();
	}

}
