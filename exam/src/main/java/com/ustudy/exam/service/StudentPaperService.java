package com.ustudy.exam.service;

import java.util.Map;

import net.sf.json.JSONArray;

public interface StudentPaperService {
	
	JSONArray getStudentPapers(Long csId);
	
	Map<String, Object> getAnswerPapers(Long egsId, Long questionId,	Long classId, String type, String text,	Boolean viewAnswerPaper) throws Exception;
}
