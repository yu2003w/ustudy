package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.ExcePaperSum;

public interface ExcePaperService {

	List<ExcePaperSum> getExcePaperSum(String schid);
	
	List<ExcePaperSum> getErrorPapers(Long subId);
	
	boolean updateErrorPaper(String paper);
}
