package com.ustudy.exam.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ustudy.exam.model.ExcePaperSum;

public interface ExcePaperService {

	List<ExcePaperSum> getExcePaperSum(String schid);
	
	Collection<Map<String,Object>> getErrorPapers(Long egsId);
	
	boolean updateErrorPaper(String paper);
}
