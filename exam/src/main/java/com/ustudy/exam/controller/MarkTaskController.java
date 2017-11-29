package com.ustudy.exam.controller;


import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ustudy.UResp;
import com.ustudy.exam.model.ExamGradeSub;
import com.ustudy.exam.model.MarkTask;
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
			String msg = "Failed to retrieve mark task for teacher " + teacid;
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

	@RequestMapping(value="/marktasks/{examId}/{gradeId}/{subjectId}", method = RequestMethod.GET)
	public UResp getAllMarkTasks(HttpServletResponse resp, @PathVariable String examId,
							   @PathVariable String gradeId, @PathVariable String subjectId) {

		UResp res = new UResp();
		
		List<MarkTask> mList = null;
		try {
			mList = stS.getMarkTasksBySub(new ExamGradeSub(examId, gradeId, subjectId));
			res.setRet(true);
			res.setMessage("retrieved mark tasks successfully");
			res.setData(mList);
		} catch (Exception e) {
			logger.warn("getAllMarkTasks()" + e.getMessage());
			String msg = "Failed to retrieve mark tasks ->" + e.getMessage();
			res.setMessage(msg);
			res.setRet(false);
			resp.setStatus(500);
			return res;

		}
	
		return res;
	}

	@RequestMapping(value="/marktasks/{examId}/{gradeId}/{subjectId}/{questionId}", method = RequestMethod.GET)
	public UResp getMarkTask(HttpServletResponse resp, @PathVariable String examId, @PathVariable String gradeId,
						   @PathVariable String subjectId, @PathVariable String questionId) {

		UResp res = new UResp();
		
		MarkTask marktask = null;
		try {
			 marktask = stS.getMarkTaskByEGSQuestion(new ExamGradeSub(examId, gradeId, subjectId), questionId);
			 res.setData(marktask);
			 res.setRet(true);
			 res.setMessage("retrieved mark task successfully");
		} catch (Exception e) {
			logger.warn("getMarkTask()" + e.getMessage());
			String msg = "Failed to retrieve mark task ->" + e.getMessage();
			res.setMessage(msg);
			res.setRet(false);
			resp.setStatus(500);
			return res;

		}

		return res;
		
	}
	
	@RequestMapping(value = "marktask/create/", method = RequestMethod.POST)
	public UResp createMarkTask(@RequestBody @Valid MarkTask mt, HttpServletResponse resp) {
		UResp res = new UResp();
		if (mt == null) {
			logger.warn("createMarkTask(), received parameter is not valid");
			res.setMessage("parameter invalid");
			return res;
		}
		
		logger.debug("createMarkTask(), item to be created -> " + mt.toString());
		try {
			if (!stS.createMarkTask(mt)) {
				logger.warn("createMarkTask(), failed to create mark task");
				res.setMessage("Failed to create mark task");
				resp.setStatus(500);
				return res;
			}
			logger.debug("createMarkTask(), mark task created.");
		} catch (Exception e) {
			logger.warn("createMarkTask(), failed to create mark task with exception " + e.getMessage());
			res.setMessage(e.getMessage());
			resp.setStatus(500);
			return res;
		}
		
		res.setRet(true);
		return res;
	}

	@RequestMapping(value = "marktask/update/", method = RequestMethod.POST)
	public UResp updateMarkTask(@RequestBody @Valid MarkTask mt, HttpServletResponse resp) {
		UResp res = new UResp();
		
		if (mt == null) {
			logger.warn("updateMarkTask(), received parameter is invalid");
			res.setMessage("parameter invalid");
			return res;
		}
		logger.debug("updateMarkTask(), item to be updated --> " + mt.toString());
		
		try {
			if (!stS.updateMarkTask(mt)) {
				logger.warn("updateMarkTask(), failed to update mark task.");
				res.setMessage("failed to update mark task specified");
				res.setData(mt);
				resp.setStatus(500);
				return res;
			}
			logger.debug("deleteMarkTask(), mark task updated");
		} catch (Exception e) {
			logger.warn("updateMarkTask(), failed to update mark task with exception -> " + e.getMessage());
			res.setMessage("failed to update mark task with exception -> " + e.getMessage());
			resp.setStatus(500);
			return res;
		} 
		
		res.setRet(true);
		return res; 
		
	}

	@RequestMapping(value = "marktask/delete/", method = RequestMethod.POST)
	public UResp deleteMarkTask(@RequestBody @Valid MarkTask mt, HttpServletResponse resp) {
		UResp res = new UResp();

		if (mt == null) {
			logger.warn("deleteMarkTask(), received parameter is invalid");
			res.setMessage("parameter invalid");
			return res;
		}
		logger.debug("deleteMarkTask(), item to be delete --> " + mt.toString());
		
		try {
			if (!stS.deleteMarkTask(mt)) {
				logger.warn("deleteMarkTask(), failed to delete mark task.");
				res.setMessage("failed to delete mark task specified");
				res.setData(mt);
				resp.setStatus(500);
				return res;
			}
			logger.debug("deleteMarkTask(), mark task deleted");
		} catch (Exception e) {
			logger.warn("deleteMarkTask(), failed to delete mark task with exception -> " + e.getMessage());
			res.setMessage("failed to delete mark task with exception -> " + e.getMessage());
			resp.setStatus(500);
			return res;
		} 
		
		res.setRet(true);
		return res; 
	}
	
}
