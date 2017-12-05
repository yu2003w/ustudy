package com.ustudy.exam.service;

import net.sf.json.JSONObject;

public interface StudentAnswerService {
	
	boolean saveStudentsAnswers(Long examId, Long egsId, JSONObject data);
	
	boolean deletePapers(Long egsId, Integer batchNum);
	
}
