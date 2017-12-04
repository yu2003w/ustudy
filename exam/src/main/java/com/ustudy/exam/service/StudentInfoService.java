package com.ustudy.exam.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface StudentInfoService {
	
	List getStudentsInfo(Long examId, Long gradeId);
	
	boolean saveStudentsAnswers(Long examId, Long egsId, JSONObject data);
	
	JSONArray getStudentPapers(Long egsId);
	
	boolean deleteAnswers(Long egsId, String batchNum);
}
