package com.ustudy.exam.controller;


import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.exam.model.MarkTaskBrife;
import com.ustudy.exam.model.QuesComb;
import com.ustudy.exam.model.QuestionPaper;
import com.ustudy.exam.service.MarkTaskService;
import com.ustudy.exam.utility.ExamUtil;

@RestController
@RequestMapping(value="/exam/")
public class MarkTaskController {

	private static final Logger logger = LogManager.getLogger(MarkTaskController.class);
	
	@Autowired
	private MarkTaskService stS; 
	
	@RequestMapping(value = "/marktask/list/", method = RequestMethod.GET)
	public List<MarkTaskBrife> getMarkTask(HttpServletResponse resp) {
		logger.debug("getMarkTask(), start to retrieving all examination result.");
		
		// fetch score task for currently logged in teacher
		List<MarkTaskBrife> st = null;
		String teacid = null;
		try {
			teacid = ExamUtil.getCurrentUserId();
			st = stS.getMarkTaskBrife(teacid);
		} catch (Exception e) {
			logger.warn("getMarkTask()" + e.getMessage());
			String msg = "Failed to retrieve score task for teacher " + teacid;
			try {
				resp.sendError(500, msg);
			} catch (Exception re) {
				logger.warn("Failed to set error status in response");
			}
		}

		return st;
	}
	
	@RequestMapping(value = "/marktask/paper/view/", method = RequestMethod.POST)
	public MarkTaskBrife getTaskPapers(@RequestBody @Valid QuesComb quesR, HttpServletResponse resp) {
		if (quesR == null) {
			logger.warn("getTaskPapers(), request parameter is not valid");
			try {
				resp.sendError(500, "getTaskPapers(), request parameter is not valid");
			} catch (Exception e) {
				logger.warn("Failed to set error status in response");
			}
		}
		else
			logger.debug("getTaskPapers(), start to retrieving student papers per request -> \n" +  quesR);
		
		// fetch score task for currently logged in teacher
		MarkTaskBrife st = null;
		String teacid = null;
		try {
			teacid = ExamUtil.getCurrentUserId();
			st = stS.getTaskPapers(teacid, quesR);
		} catch (Exception e) {
			logger.warn("getTaskPapers()" + e.getMessage());
			String msg = "Failed to retrieve score task for teacher " + teacid;
			try {
				resp.sendError(500, msg);
			} catch (Exception re) {
				logger.warn("Failed to set error status in response," + re.getMessage());
			}
		}

		return st;
	}
	
	@RequestMapping(value="/marktask/paper/update/", method = RequestMethod.POST)
	public String updateMarkResult(@RequestBody @Valid QuestionPaper up, HttpServletResponse resp) {
		if (up == null) {
			logger.warn("updateMarkResult(), request parameter is not valid");
			try {
				resp.sendError(500, "updateMarkResult(), request parameter is not valid");
			} catch (Exception e) {
				logger.warn("Failed to set error status in response");
			}
		}
		else
			logger.debug("updateMarkResult(), update marked result ->" + up.toString());
		
		String result = null;
		try {
			if (stS.updateMarkResult(up)) {
				result = "update mark result successully";
			}
			else
				result = "update mark result failed";
		} catch (Exception e) {
			logger.warn("updateMarkResult()," + e.getMessage());
			try {
				resp.sendError(500, "updateMarkResult(), failed to update mark result.");
			} catch (Exception re) {
				logger.warn("updateMarkResult(), failed to set error status. " + re.getMessage());
			}
		}
		
		return result;
	}
}
