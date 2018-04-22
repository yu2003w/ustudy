package com.ustudy.exam.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.UResp;
import com.ustudy.exam.service.AnalysisService;

@RestController
@RequestMapping(value = "/exam/analysis/")
public class AnalysisController {

	private static final Logger logger = LogManager.getLogger(AnalysisController.class);
	
	@Autowired
	private AnalysisService anaS;
	
	@RequestMapping(value = "/examlist/", method = RequestMethod.GET)
	public UResp getExamBrifeList(HttpServletResponse response) {
		
		logger.debug("getExamBrifeList(), retrieve exam brife list for analysis");
		UResp res = new UResp();
		
		try {
			res.setData(anaS.getExamsForAnaly());
			res.setRet(true);
		} catch (Exception e) {
			logger.error("getExamBrifeList(), failed with exception->" + e.getMessage());
			res.setMessage(e.getMessage());
			response.setStatus(500);
			e.printStackTrace();
		}
		
		return res;
	}
	
	
	@RequestMapping(value = "questions/object/{egsid}/{clsid}", method = RequestMethod.GET)
	public UResp getObjQuesReport(@PathVariable("egsid") @Valid long egsid, 
			@PathVariable("clsid") @Valid long clsid, HttpServletResponse resp) {
		
		logger.debug("getObjQuesReport(), egsid=" + egsid + ", clsid=" + clsid);
		UResp res = new UResp();
		
		try {
			res.setData(anaS.getObjQuesReport(egsid, clsid));
			res.setRet(true);
		} catch (Exception e) {
			logger.error("getObjQuesReport(), " + e.getMessage());
			e.printStackTrace();
			res.setMessage(e.getMessage());
			resp.setStatus(500);
		}
		
		
		return res;
	}
	
	@RequestMapping(value = "questions/subject/{egsid}/{clsid}", method = RequestMethod.GET)
	public UResp getSubQuesReport(@PathVariable("egsid") @Valid long egsid, 
			@PathVariable("clsid") @Valid long clsid, HttpServletResponse resp) {
		
		logger.debug("getSubQuesReport(), egsid=" + egsid + ", clsid=" + clsid);
		UResp res = new UResp();
		
		try {
			res.setData(anaS.getSubQuesReport(egsid, clsid));
			res.setRet(true);
		} catch (Exception e) {
			logger.debug("getSubQuesReport(), " + e.getMessage());
			res.setMessage(e.getMessage());
			resp.setStatus(500);
			e.printStackTrace();
		}
		
		return res;
	}
	
	@RequestMapping(value = "questions/{egsid}/{clsid}", method = RequestMethod.GET)
	public UResp getEgsScoreReport(@PathVariable("egsid") @Valid long egsid, 
			@PathVariable("clsid") @Valid long clsid, HttpServletResponse resp) {
		logger.debug("getEgsScoreReport(), assemble egs score report for egsid=" + egsid + ", clsid=" + clsid);
		UResp res = new UResp();
		
		try {
			res.setData(anaS.getEgsScoreAnaly(egsid, clsid));
			res.setRet(true);
		} catch (Exception e) {
			logger.error("getEgsScoreReport(), failed with exception->" + e.getMessage());
			res.setMessage(e.getMessage());
			resp.setStatus(500);
			e.printStackTrace();
		}
		return res;
	}
	
}
