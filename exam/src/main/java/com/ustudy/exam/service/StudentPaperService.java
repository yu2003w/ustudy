package com.ustudy.exam.service;

import net.sf.json.JSONArray;

public interface StudentPaperService {
	
	JSONArray getStudentPapers(Long csId);
}
