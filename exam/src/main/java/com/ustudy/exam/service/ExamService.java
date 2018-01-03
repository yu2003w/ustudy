package com.ustudy.exam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ustudy.exam.model.Exam;

import net.sf.json.JSONArray;

public interface ExamService {
	
	List<Exam> getAllExams();
	
	JSONArray getExams(Boolean finished, Long gradeId, Long subjectId, String startDate, String endDate, String name);
	
	JSONArray getTeacherExams();
	
	List<Exam> getExamsByStatus(String status);
	
	Exam getExamsById(Long id);

    ArrayList<Map<String, Object>> getGrades();

	ArrayList<Map<String, Object>> getSubjects();
	
	JSONArray getExamSummary(Long examId);
}
