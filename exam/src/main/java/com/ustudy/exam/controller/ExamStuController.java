package com.ustudy.exam.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.exam.service.ExamStuService;

@RestController
@RequestMapping(value = "/")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExamStuController {
	
	private static final Logger logger = LogManager.getLogger(ExamStuController.class);
	
	@Autowired
	private ExamStuService service;
	
	/**
	 * 
	 * getExamStudents[获取考试全部考生信息]
	 * 创建人:  dulei
	 * 创建时间: 2017年12月13日 下午10:18:56
	 *
	 * @Title: getExamStudents
	 * @param examId 考试ID
	 * @param request
	 * @param response
	 * @return 考生信息
	 */
	@RequestMapping(value = "/students/{examId}/{gradeId}", method = RequestMethod.GET)
	public Map getExamStudents(@PathVariable Long examId, @PathVariable Long gradeId, @RequestParam(required=false) Long classId, @RequestParam(required=false) String text) {
		
		logger.debug("getExamStudents().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getExamStudents(examId, gradeId, classId, text));

		return result;
	}
	
	/**
	 * 
	 * getMissExamStudents[按科目获取缺考考生]
	 * 创建人:  dulei
	 * 创建时间: 2017年12月17日 下午7:41:19
	 *
	 * @Title: getMissExamStudents
	 * @param egsId 考试科目
	 * @param classId 班级
	 * @param text 姓名或考号
	 * @return
	 */
    @RequestMapping(value = "/students/miss/{egsId}/{gradeId}", method = RequestMethod.GET)
    public Map getMissExamStudents(@PathVariable Long egsId, @PathVariable Long gradeId, @RequestParam(required=false) Long classId, @RequestParam(required=false) String text) {
        
        logger.debug("getMissExamStudents().");
        
        Map result = new HashMap<>();

        result.put("success", true);
        result.put("data", service.getMissExamStudents(egsId, gradeId, classId, text));

        return result;
    }
	
}
