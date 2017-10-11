package com.ustudy.exam.service;

import java.util.List;
import java.util.Map;

import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamSubject;

public interface ClientService {

	public boolean saveTemplates(String templates);
	
	public Map<String, String> getTemplates(String CSID);
	
	public List<ExamSubject> getSubject(String EGID, String GDID);
	
	public Map<String, List<Map<String, String>>> getExamGrade(String egID, String markingStatus);
	
	public Map<String, List<Exam>> getExams(String markingStatus);
	
	public Map<String, List<Map<String, String>>> getPermissionList(String tokenstr);
	
	public Map<String, Object> login(String username, String password);
	
	public Map<String, String> update();
}
