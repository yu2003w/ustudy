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
import com.ustudy.exam.service.report.EgsReportService;

@RestController
@RequestMapping(value="/exam/report/")
public class EgsReportController {

	private static final Logger logger = LogManager.getLogger(EgsReportController.class);
	
	@Autowired
	private EgsReportService egsReportS;
	
	@RequestMapping(value = "/answer/details/{egsid}/", method = RequestMethod.GET)
	public UResp getEgsAnsReport(@PathVariable("egsid") @Valid long egsid, HttpServletResponse resp) {
		UResp res = new UResp();
		
		if (egsid <= 0) {
			logger.error("getEgsAnsReport(), invalid parameter");
			res.setMessage("Invalid parameter");
			resp.setStatus(400);
			return res;
		}
		
		try {
			res.setData(egsReportS.getEgsAnsReport(egsid));
			res.setRet(true);
		} catch (Exception e) {
			logger.error("getEgsAnsReport(), egsid=" + egsid + ", exception->" + e.getMessage());
			res.setMessage(e.getMessage());
			resp.setStatus(500);
		}
		
		return res;
		
	}
	
	@RequestMapping(value = "/egsscore/{egsid}/", method = RequestMethod.GET)
	public UResp getEgsScoreReport(@PathVariable("egsid") @Valid long egsid, HttpServletResponse resp) {
		UResp res = new UResp();
		
		if (egsid <= 0) {
			logger.error("getEgsScoreReport(), invalid parameter");
			res.setMessage("Invalid parameter");
			resp.setStatus(400);
			return res;
		}
		
		try {
			res.setData(egsReportS.getEgsScoreReport(egsid));
			res.setRet(true);
		} catch (Exception e) {
			logger.error("getEgsScoreReport(), egsid=" + egsid + ", exception->" + e.getMessage());
			res.setMessage(e.getMessage());
			resp.setStatus(500);
		}
		
		return res;
		
	}
	
}
