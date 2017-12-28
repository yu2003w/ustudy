package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.ExcePaperSum;

public interface ExcePaperService {

	public List<ExcePaperSum> getExcePaperSum(String schid);
	
}
