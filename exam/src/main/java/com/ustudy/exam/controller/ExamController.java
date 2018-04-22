package com.ustudy.exam.controller;

import java.util.HashMap;
import java.util.List;
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
import com.ustudy.exam.model.ExamGrBrife;
import com.ustudy.exam.service.ExamService;
import com.ustudy.exam.utility.ExamUtil;

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
	public Map getAllExams(HttpServletRequest request, HttpServletResponse resp) {
		
		String orgType = ExamUtil.retrieveSessAttr("orgType");
		String orgId = ExamUtil.retrieveSessAttr("orgId");
		if (orgId == null || orgType == null || orgId.isEmpty() || orgType.isEmpty()) {
			logger.error("getAllExams(), failed to retrieve org info");
			throw new RuntimeException("failed to retrieve org info");
		}
		
		logger.debug("getAllExams(), retrieve all exams for " + orgType + " with id " + orgId);
		Map result = new HashMap<>();

		try {
			result.put("success", true);
			result.put("data", service.getAllExams(orgId));
		} catch (Exception e) {
			logger.error("getAllExams(), failed with exception->" + e.getMessage());
			resp.setStatus(500);
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
     * 
     * getSubjectQuestionPapers[根据考试科目、题块号，返回该题的所有答题卡]
     * 创建人:  dulei
     * 创建时间: 2018年1月3日 下午10:17:37
     *
     * @Title: getSubjectQuestionPapers
     * @param subId 考试科目 
     * @param quesId 题块号
     * @return 该题的所有答题卡
     */
    @RequestMapping(value = "/{egsId}/{quesId}/papers", method = RequestMethod.GET)
    public Map getSubjectQuestionPapers(@PathVariable long egsId,@PathVariable long quesId) {
        
        logger.debug("getSubjectQuestionPapers(egsId:"+egsId+",quesId:"+quesId+").");
        
        Map result = new HashMap<>();
        
        try {
            result.put("success", true);
            result.put("data", service.getSubjectQuestionPapers(egsId, quesId));
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
	
	/**
	 * Get exams and related grades information belongs to certain school
	 * @param resp
	 * @return
	 */
	@RequestMapping(value =  "/exam/exgr/",  method = RequestMethod.GET)
	public UResp getExamGradesInfo(HttpServletResponse resp) {
		logger.debug("getExamGradesInfo(), /exam/exgr/ visited");
		UResp res = new UResp();
		
		String schId = ExamUtil.retrieveSessAttr("orgId");
		if (schId == null || schId.isEmpty()) {
			logger.error("getExamGradesInfo(), failed to retrieve org id, maybe user not log in");
			throw new RuntimeException("getExamGradesInfo(), failed to retrieve org id, maybe user not log in");
		}
		
		try {
			List<ExamGrBrife> egL = service.getExamGrInfo(schId);
			res.setData(egL);
			res.setRet(true);
		} catch (Exception e) {
			logger.error("getExamGradesInfo(), retrieve exams grades info with exception->" + e.getMessage());
			resp.setStatus(500);
			res.setMessage("getExamGradesInfo()," + e.getMessage());
		}
		
		return res;
	}
}
