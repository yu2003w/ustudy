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
	public Map getExamSubjects() {
		
		logger.debug("getExamSubjects().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getExamSubjects());

		return result;
	}
	
	/**
	 * 根据考试ID获取考试科目信息
	 * @param examId 考试ID
	 * @return
	 */
	@RequestMapping(value = "/examsubjects/{examId}", method = RequestMethod.GET)
	public Map getExamSubjects(@PathVariable Long examId) {
		
		logger.debug("getExamSubjects().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		
		List<ExamSubject> examSubjects = service.getExamSubjects(examId);
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
	public Map getExamSubjects(@PathVariable Long examId, @PathVariable Long gradeId) {
		
		logger.debug("getExamSubjects().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getExamSubjects(examId, gradeId));

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
	public Map getExamSubjects(@PathVariable Long examId, @PathVariable Long gradeId, @PathVariable Long subjectId) {
		
		logger.debug("getExamSubjects().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getExamSubjects(examId, gradeId, subjectId));

		return result;
	}
	
	/**
	 * 根据考试科目ID获取考试科目信息
	 * @param id 考试科目ID
	 * @return
	 */
	@RequestMapping(value = "/examsubject/{id}", method = RequestMethod.GET)
	public Map getExamSubject(@PathVariable Long id) {
		
		logger.debug("getExamSubject().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getExamSubject(id));

		return result;
	}
}
