package com.ustudy.exam.controller;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.exam.model.ScoreTask;
import com.ustudy.exam.service.ScoreTaskService;
import com.ustudy.exam.utility.ExamUtil;

@RestController
public class ScoreTaskController {

	private static final Logger logger = LogManager.getLogger(ScoreTaskController.class);
	
	@Autowired
	private ScoreTaskService stS; 
	
	@RequestMapping(value = "/scoretask/list/", method = RequestMethod.GET)
	public List<ScoreTask> getScoreTask(HttpServletResponse resp) {
		logger.debug("getScoreTask(), start to retriing all examination result.");
		
		// fetch score task for currently logged in teacher
		List<ScoreTask> st = null;
		String teacid = null;
		try {
			teacid = ExamUtil.getCurrentUserId();
			st = stS.getScoreTask(teacid);
		} catch (Exception e) {
			logger.warn("getScoreTask()" + e.getMessage());
			String msg = "Failed to retrieve score task for teacher " + teacid;
			try {
				resp.sendError(500, msg);
			} catch (Exception re) {
				logger.warn("Failed to set error status in response");
			}
		}

		return st;
	}
}
