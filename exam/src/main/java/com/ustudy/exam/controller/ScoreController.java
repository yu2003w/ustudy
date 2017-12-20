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
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.exam.service.ScoreService;

@RestController
@RequestMapping(value = "/score")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ScoreController {

	private static final Logger logger = LogManager.getLogger(ScoreController.class);

	@Autowired
	private ScoreService service;
	
	@RequestMapping(value = "/recalculate/{egsId}/{quesno}/{answer}", method = RequestMethod.POST)
    public Map recalculateQuestionScore(@PathVariable Long egsId, @PathVariable Integer quesno, @PathVariable String answer, HttpServletRequest request,
            HttpServletResponse response) {

        logger.debug("recalculateQuestionScore().");
        logger.debug("egsId: " + egsId + ",quesno=" + quesno + ",answer=" + answer);

        Map result = new HashMap<>();

        try {
            if (service.recalculateQuestionScore(egsId, quesno, answer)) {
                result.put("success", true);
            } else {
                result.put("success", false);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/recalculate/{egsId}", method = RequestMethod.POST)
    public Map recalculateQuestionsScore(@PathVariable Long egsId, HttpServletRequest request,
            HttpServletResponse response) {

        logger.debug("recalculateQuestionScore().");
        logger.debug("egsId: " + egsId);

        Map result = new HashMap<>();

        try {
            if (service.recalculateQuestionScore(egsId)) {
                result.put("success", true);
            } else {
                result.put("success", false);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/publish/{examId}/{release}", method = RequestMethod.POST)
    public Map publishExamScore(@PathVariable Long examId, @PathVariable Boolean release) {

        logger.debug("publishExamScore(examId:"+examId+",release:"+release+").");

        Map result = new HashMap<>();

        try {
        	if (release) {
        		if (service.publishExamScore(examId, release)) {
        			result.put("success", true);
        			result.put("message", "考试成绩发布成功");
        		} else {
        			result.put("success", false);
        			result.put("message", "考试成绩发布失败");
        		}
        	} else {
        		if (service.publishExamScore(examId, release)) {
        			result.put("success", true);
        			result.put("message", "考试成绩取消发布成功");
        		} else {
        			result.put("success", false);
        			result.put("message", "考试成绩取消发布失败");
        		}
			}
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

}
