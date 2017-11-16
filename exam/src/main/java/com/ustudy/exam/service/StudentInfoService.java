package com.ustudy.exam.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface StudentInfoService {
	
	List getStudentsInfo(Long examId, Long gradeId);
	
	boolean saveStudentsAnswers(Long egId, Long csId, JSONObject data);
	
	JSONArray getStudentPapers(Long csId);
	
	boolean deleteAnswers(Long csId, String batchNum);
}
