package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.StudentPaper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface StudentPaperService {
	
	JSONArray getStudentPapers(Long csId);
	
	List<StudentPaper> getAnswerPapers(JSONObject parameter) throws Exception;
}
