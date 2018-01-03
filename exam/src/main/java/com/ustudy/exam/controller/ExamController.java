package com.ustudy.exam.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.UResp;
import com.ustudy.exam.service.ExamService;

@RestController
@RequestMapping(value = "/")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExamController {
	
	private static final Logger logger = LogManager.getLogger(ExamController.class);
	
	@Autowired
	private ExamService service;
	
	/**
	 * 获取考试信息
	 * @param request HttpServletRequest 
	 * @param response HttpServletResponse
	 * @return Map
	 */
	@RequestMapping(value = "/allexams", method = RequestMethod.GET)
	public Map getAllExams(HttpServletRequest request, HttpServletResponse response) {
		
		logger.debug("getAllExams().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getAllExams());

		return result;
	}
    
    /**
     * 根据考试状态获取考试信息
     * @param examStatus 考试状态
     * @param request HttpServletRequest 
     * @param response HttpServletResponse
     * @return Map
     */
    @RequestMapping(value = "/exams/{status}", method = RequestMethod.GET)
    public UResp getExams(@PathVariable String status, HttpServletResponse resp) {
        
        logger.debug("getExams(), examStatus: " + status);
        
        UResp res = new UResp();
        
        try {
        	res.setData(service.getExamsByStatus(status));
        	res.setRet(true);
        }catch (Exception e) {
        	res.setMessage("getExams(), " + e.getMessage());
        	resp.setStatus(500);
        }

        return res;
        
    }
    
    /**
     * 根据考试状态获取考试信息
     * @param examStatus 考试状态
     * @param request HttpServletRequest 
     * @param response HttpServletResponse
     * @return Map
     */
    @RequestMapping(value = "/exams", method = RequestMethod.GET)
    public Map getExams(@RequestParam(value="finished", defaultValue="false") Boolean finished, 
            @RequestParam(value="gradeId", defaultValue="0") Long gradeId, 
            @RequestParam(value="subjectId", defaultValue="0") Long subjectId, 
            @RequestParam(value="startDate", defaultValue="") String startDate, 
            @RequestParam(value="endDate", defaultValue="") String endDate, 
            @RequestParam(value="name", defaultValue="") String name) {
        
        logger.debug("getExams(finished: " + finished + ",gradeId=" + gradeId + ",subjectId=" + subjectId + ",startDate=" + startDate + ",endDate=" + endDate + ",name=" + name + ").");
        
        Map result = new HashMap<>();
        
        try {
            result.put("success", true);
            result.put("data", service.getExams(finished, gradeId, subjectId, startDate, endDate, name));
        }catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }
    
    /**
     * 获得该用户能访问的已发布成绩的考试列表
     * @return Map
     */
    @RequestMapping(value = "/teacher/exams", method = RequestMethod.GET)
    public Map getTeacherExams() {
        
        logger.debug("getTeacherExams().");
        
        Map result = new HashMap<>();
        
        try {
            result.put("success", true);
            result.put("data", service.getTeacherExams());
        }catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }
	
	/**
	 * 根据考试状态获取考试信息
	 * @param examStatus 考试状态
	 * @param request HttpServletRequest 
	 * @param response HttpServletResponse
	 * @return Map
	 */
	@RequestMapping(value = "/exam/{examId}", method = RequestMethod.GET)
	public Map getExam(@PathVariable Long examId, HttpServletRequest request, HttpServletResponse response) {
		
		logger.debug("getExam().");
		logger.debug("examId: " + examId);
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getExamsById(examId));

		return result;
	}

	@RequestMapping(value = "/exam/options", method = RequestMethod.GET)
	public Map getOptions(HttpServletResponse response) {
		logger.debug("getOptions().");

		Map result = new HashMap<>();
		Map data = new HashMap<>();

		data.put("grades", service.getGrades());
		data.put("subjects", service.getSubjects());

		result.put("success", true);
		result.put("data", data);

		return result;
    }

	@RequestMapping(value = "/exam/summary/{examId}", method = RequestMethod.GET)
	public Map getExamSummary(@PathVariable Long examId, HttpServletResponse response) {
		logger.debug("getExamSummary(" + examId + ").");

		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getExamSummary(examId));

		return result;
    }
}
