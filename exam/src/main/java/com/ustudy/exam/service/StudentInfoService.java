package com.ustudy.exam.service;

import java.util.List;

import net.sf.json.JSONObject;

public interface StudentInfoService {
	
	List getStudentsInfo(String examId, String gradeId);
	
	boolean saveStudentsAnswers(String egId, String csId, JSONObject data);
	
	List getAllPaper(String csId);
	
	boolean deleteAnswers(String csId, String batchNum);
}
