package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.ExamStudent;

public interface ExamStudentService {

	List<ExamStudent> getStudentInfoByExamGrade(int examId, int gradeId);
	
}
