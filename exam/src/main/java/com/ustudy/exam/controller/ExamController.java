package com.ustudy.exam.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(ExamController.class);
	
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
    public UResp getExams(@PathVariable boolean status, HttpServletResponse resp) {
        
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
        
        logger.debug("getExams().");
        logger.debug("finished: " + finished + ",gradeId=" + gradeId + ",subjectId=" + subjectId + ",startDate=" + startDate + ",endDate=" + endDate + ",name=" + name);
        
        Map result = new HashMap<>();

        result.put("success", true);
        result.put("data", service.getExams(finished, gradeId, subjectId, startDate, endDate, name));

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
