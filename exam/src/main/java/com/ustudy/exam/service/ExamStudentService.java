package com.ustudy.exam.service;

import net.sf.json.JSONArray;

public interface ExamStudentService {

	JSONArray getStudentInfoByExamGrade(Long examId, Long gradeId);
	
}
