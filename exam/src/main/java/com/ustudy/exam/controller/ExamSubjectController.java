package com.ustudy.exam.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.service.ExamSubjectService;
import com.ustudy.exam.service.ScoreService;

@RestController
@RequestMapping(value = "/")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExamSubjectController {
	
	private static final Logger logger = LogManager.getLogger(ExamSubjectController.class);
	
	@Autowired
	private ExamSubjectService service;
	
	@Autowired
    private ScoreService scoreService;
	
	/**
	 * 获取所有考试科目信息
	 * @return
	 */
	@RequestMapping(value = "/examsubjects", method = RequestMethod.GET)
	public Map getExamSubjects(@RequestParam(required=false) Long subjectId, 
			@RequestParam(required=false) Long gradeId, 
			@RequestParam(required=false) String start, 
			@RequestParam(required=false) String end, 
			@RequestParam(required=false) String examName) {
		
		logger.debug("getExamSubjects().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getExamSubjects(subjectId, gradeId, start, end, examName));

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
	
	//TODO： Assemble exam subject info, related question info, related mark task info together
	// so front end could reduce request
	@RequestMapping(value = "/examsub/list/{eid}", method = RequestMethod.GET)
	public UResp getExamSubBrife(@PathVariable int eid, HttpServletResponse resp) {
		UResp res = new UResp();
		return res;
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
	@RequestMapping(value = "/examsubject/{examId}/{gradeId}/{subjectId}", method = RequestMethod.GET)
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
	
	/**
	 * 获取最新一次考试信息
	 * @return
	 */
	@RequestMapping(value = "/last/examsubjects", method = RequestMethod.GET)
	public Map getLastExamSubjects() {
		
		logger.debug("getLastExamSubject().");
		
		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", service.getLastExamSubjects());

		return result;
	}
    
    /**
     * 
     * updateExamSubjectStatus[根据考试科目ID修改开始状态]
     * 创建人:  dulei
     * 创建时间: 2017年12月13日 下午8:23:46
     *
     * @Title: updateExamSubjectStatus
     * @param egsId 考试科目ID
     * @param release 是否发布
     * @return 返回状态
     */
    @RequestMapping(value = "/examsubject/status/{egsId}/{release}", method = RequestMethod.POST)
    public Map updateExamSubjectStatus(@PathVariable Long egsId, @PathVariable Boolean release) {
        
        logger.debug("updateExamSubjectStatus().");
        
        Map result = new HashMap<>();
        
        if(release){
            try {
                scoreService.recalculateQuestionScore(egsId);
            } catch (Exception e) {
                logger.error(e.getMessage());
                result.put("success", false);
                result.put("data", "更新失败");
                return result;
            }
        }

        if(service.updateExamSubjectStatus(egsId, release)){
            
            new Thread() {
                public void run() {
                    try {
                        ExamSubject examSubject = service.getExamSubject(egsId);
                        if(null != examSubject && examSubject.getExamid() > 0){
                            scoreService.publishExamScore(examSubject.getExamid(), true);
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }                    
                }
            }.start();
            
            result.put("success", true);
            result.put("data", "更新成功");
        }else {
            result.put("success", false);
            result.put("data", "更新失败");
        }

        return result;
    }
    
    /**
     * 
     * updateExamSubjectStatus[这里用一句话描述这个方法的作用]
     * 创建人:  dulei
     * 创建时间: 2017年12月13日 下午8:27:12
     *
     * @Title: updateExamSubjectStatus
     * @param examId 考试ID
     * @param gradeId 年级ID
     * @param subjectId 科目ID
     * @param release 是否发布
     * @return 放回状态
     */
    @RequestMapping(value = "/examsubject/status/{examId}/{gradeId}/{subjectId}/{release}", method = RequestMethod.POST)
    public Map updateExamSubjectStatus(@PathVariable Long examId, @PathVariable Long gradeId, @PathVariable Long subjectId, @PathVariable Boolean release) {
        
        logger.debug("updateExamSubjectStatus().");
        
        Map result = new HashMap<>();

        if(service.updateExamSubjectStatus(examId, gradeId, subjectId, release)){
            result.put("success", true);
            result.put("data", "更新成功");
        }else {
            result.put("success", false);
            result.put("data", "更新失败");
        }

        return result;
    }
}
