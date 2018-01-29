package com.ustudy.exam.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ustudy.exam.model.ExcePaperSum;

public interface ExcePaperService {

	public List<ExcePaperSum> getExcePaperSum(String schid);
	
	public Collection<Map<String,Object>> getErrorPapers(Long egsId);
	
	public boolean updateErrorPaper(String paper);
	
}
