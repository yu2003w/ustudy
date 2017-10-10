package com.ustudy.exam.service;

import java.util.List;
import java.util.Map;

public interface ClientService {

	public boolean saveTemplates(String templates);
	
	public Map<String, String> getTemplates(String CSID);
	
	public List<Map<String, String>> getSubject(String EGID, String GDID);
	
	public Map<String, List<Map<String, String>>> getExamGrade(String egID, String markingStatus);
	
	public Map<String, List<Map<String, String>>> getExams(String MarkingStatus);
	
	public Map<String, List<Map<String, String>>> getPermissionList(String tokenstr);
	
	public Map<String, Object> login(String username, String password);
	
	public Map<String, String> update();
}
