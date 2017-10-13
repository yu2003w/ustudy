package com.ustudy.exam.service;

import java.util.List;
import java.util.Map;

import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamGrade;
import com.ustudy.exam.model.ExamSubject;

public interface ClientService {
	
	public Map<String, Object> login(String token);

	public boolean saveTemplates(String templates);
	
	public Map<String, String> getTemplates(String CSID);
	
	public List<ExamSubject> getExamSubject(String EGID, String GDID);
	
	public List<ExamGrade> getExamGrade(String egID, String markingStatus);
	
	public Map<String, List<Exam>> getExams(String markingStatus);
	
	public Map<String, List<Map<String, String>>> getPermissionList(String tokenstr);
	
}
