package com.ustudy.exam.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.service.ExamSubjectService;

@RestController
@RequestMapping(value = "/")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExamSubjectController {
	
	private static final Logger logger = LogManager.getLogger(ExamSubjectController.class);
	
	@Autowired
	private ExamSubjectService service;
	
	/**
	 * 获取所有考试科目信息
	 * @return
	 */
	@RequestMapping(value = "/examsubjects", method = RequestMethod.GET)
	public Map getAllExamSubject() {
		
		logger.debug("getAllExamSubject().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getAllExamSubject());

		return result;
	}
	
	/**
	 * 根据考试ID获取考试科目信息
	 * @param examId 考试ID
	 * @return
	 */
	@RequestMapping(value = "/examsubjects/{examId}", method = RequestMethod.GET)
	public Map getAllExamSubjectByExamId(@PathVariable String examId) {
		
		logger.debug("getAllExamSubjectByExamId().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		
		List<ExamSubject> examSubjects = service.getAllExamSubjectByExam(examId);
		Map<String, Map<String, Object>> grades = new HashMap<>();
		for (ExamSubject examSubject : examSubjects) {
			Map<String, Object> grade = grades.get("" + examSubject.getGradeId());
			if(grade == null){
				grade = new HashMap<>();
				grade.put("id", examSubject.getGradeId());
				grade.put("grade", examSubject.getGradeName());
				grade.put("subjects", new ArrayList<>());
			}
			List<ExamSubject> subjects = (List<ExamSubject>) grade.get("subjects");
			subjects.add(examSubject);
			grade.put("subjects", subjects);
			
			grades.put("" + examSubject.getGradeId(), grade);
		}
		
		result.put("data", grades.values());

		return result;
	}
	
	/**
	 * 根据考试ID、年级ID获取考试科目信息
	 * @param examId 考试ID
	 * @param gradeId 年级ID
	 * @return
	 */
	@RequestMapping(value = "/examsubjects/{examId}/{gradeId}", method = RequestMethod.GET)
	public Map getAllExamSubjectByExamIdAndGradeId(@PathVariable String examId, @PathVariable String gradeId) {
		
		logger.debug("getAllExamSubjectByExamIdAndGradeId().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getAllExamSubjectByExamAndGrade(examId, gradeId));

		return result;
	}
	
	/**
	 * 根据考试ID、年级ID、科目ID获取考试科目信息
	 * @param examId 考试ID
	 * @param gradeId 年级ID
	 * @param subjectId 科目ID
	 * @return
	 */
	@RequestMapping(value = "/gxamsubject/{examId}/{gradeId}/{subjectId}", method = RequestMethod.GET)
	public Map getExamSubjectByExamIdAndGradeIdAndSubjectId(@PathVariable String examId, @PathVariable String gradeId, @PathVariable String subjectId) {
		
		logger.debug("getExamSubjectByExamIdAndGradeIdAndSubjectId().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getExamSubjectByExamAndGradeAndSubject(examId, gradeId, subjectId));

		return result;
	}
	
	/**
	 * 根据考试科目ID获取考试科目信息
	 * @param id 考试科目ID
	 * @return
	 */
	@RequestMapping(value = "/examsubject/{id}", method = RequestMethod.GET)
	public Map getAllExamSubjectById(@PathVariable String id) {
		
		logger.debug("getAllExamSubjectById().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getExamSubjectById(id));

		return result;
	}
}
