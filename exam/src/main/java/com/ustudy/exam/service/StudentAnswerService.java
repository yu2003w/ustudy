package com.ustudy.exam.service;

import net.sf.json.JSONObject;

public interface StudentAnswerService {
	
	boolean saveStudentsAnswers(Long egId, Long csId, JSONObject data);
	
	boolean deletePapers(Long csId, Integer batchNum);
	
}
