package com.ustudy.info.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.UResp;
import com.ustudy.info.model.Examinee;
import com.ustudy.info.services.ExamineeService;

@RestController
@RequestMapping(value="info/examinee/")
public class ExamineeController {

	private final static Logger logger = LogManager.getLogger(ExamineeController.class);
	
	@Autowired
	private ExamineeService examS;
	
	@RequestMapping(value="create/", method = RequestMethod.POST)
	public UResp createExaminee(@RequestBody @Valid List<Examinee> exs, HttpServletResponse resp) {
		
		UResp res = new UResp();
		if (exs == null || exs.isEmpty()) {
			logger.warn("createExaminee(), input parameter is empty");
			res.setMessage("input parameter is empty");
			resp.setStatus(400);
			return res;
		}
		
		try {
			int num = examS.createExaminee(exs);
			res.setRet(true);
			logger.info("createExaminee(), " + num + " examinees imported");;
		} catch (Exception e) {
			logger.error("createExaminee(), " + e.getMessage());
			res.setMessage("failed to create examinee with exception->" + e.getMessage());
			resp.setStatus(500);
		}
		
		return res;
		
	}
	
	@RequestMapping(value="delete/", method = RequestMethod.DELETE)
	public UResp deleteExaminee(@PathVariable @Valid int id, HttpServletResponse resp) {
		UResp res = new UResp();
		try {
			int ret = examS.deleteExaminee(id);
			if (ret != 1) {
				logger.error("deleteExamainee(), failed to delete examination " + id + 
						", returned value->" + ret);
				res.setMessage("failed to delete examination " + id + ", returned value->" + ret);
				return res;
			}
			res.setRet(true);
		} catch (Exception e) {
			logger.error("deleteExaminee(), failed to delete examination->" + id + 
					", with exeception" + e.getMessage());
			res.setMessage("deleteExaminee(), failed with exception->" + e.getMessage());
			resp.setStatus(500);
		}
		
		return res;
	}
	
	@RequestMapping(value="update/", method = RequestMethod.POST)
	public UResp updateExaminee(@RequestBody @Valid List<Examinee> exs, HttpServletResponse resp) {
		UResp res = new UResp();
		if (exs == null || exs.isEmpty()) {
			logger.warn("updateExaminee(), input parameter is empty");
			res.setMessage("input parameter is empty");
			resp.setStatus(400);
			return res;
		}
		
		try {
			int ret = examS.updateExaminee(exs);
			logger.info("updateExaminee(), " + ret + " examinees updated");
		} catch (Exception e) {
			logger.error("updateExaminee(), failed with exception->" + e.getMessage());
			res.setMessage("updateExaminee(), failed with exception->" + e.getMessage());
			resp.setStatus(500);
		}
		
		return res;
	}
	
}
