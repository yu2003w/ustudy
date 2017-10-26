package com.ustudy.exam.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ExamStudentDao;
import com.ustudy.exam.model.ExamStudent;
import com.ustudy.exam.service.ExamStudentService;

@Service
public class ExamStudentServiceImpl implements ExamStudentService {

	@Resource
	private ExamStudentDao examStudentDaompl;
	
	public List<ExamStudent> getStudentInfoByExamGrade(int examId, int gradeId) {
		return examStudentDaompl.getStudentInfoByExamIDAndGradeId(examId, gradeId);
	}

}
