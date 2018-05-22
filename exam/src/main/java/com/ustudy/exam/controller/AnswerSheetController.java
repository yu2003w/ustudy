package com.ustudy.exam.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.UResp;
import com.ustudy.exam.service.AnswerSheetService;

@RestController
@RequestMapping(value = "/exam/answersheet/")
public class AnswerSheetController {

	private static final Logger logger = LogManager.getLogger(AnswerSheetController.class);
	
	@Autowired
	private AnswerSheetService ansS;
	
	@RequestMapping(value = "summary", method = RequestMethod.GET)
	public UResp retrieveAnswerSheet(@RequestParam(value = "startdate", required = false) String startdate,
			@RequestParam(value = "enddate", required = false) String enddate, 
			@RequestParam(value = "key", required = false) String key,
			HttpServletResponse resp) {
		
		UResp res = new UResp();
		
		logger.debug("retrieveAnswerSheet(), startdate=" + startdate + ", enddate=" + enddate + ", key=" + key);
		try {
			res.setData(ansS.getAnswerSheetMeta());
			res.setRet(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("retrieveAnswerSheet(), exception->" + e.getMessage());
			res.setMessage(e.getMessage());
			resp.setStatus(500);
		}
		
		return res;
	}
	
	
	@RequestMapping(value = "papers/{qid}/{type}", method = RequestMethod.GET)
	public UResp getAnsPapers(@PathVariable("qid") @Valid long quesid, @PathVariable("type") @Valid String type, 
			@RequestParam(value = "clsid", required = false) Long clsid, 
			@RequestParam(value = "key", required = false) String key, HttpServletResponse resp) {
		UResp res = new UResp();
		
		logger.debug("getAnsPapers(), qid=" + quesid + ", type=" + type);
		
		try {
			res.setData(ansS.getAnsPapers(quesid, type, clsid == null? 0 : clsid, key));
			res.setRet(true);
		} catch (Exception e) {
			logger.error("getAnsPapers(), exception->" + e.getMessage());
			res.setMessage(e.getMessage());
			resp.setStatus(500);
		}
		
		return res;
	}
	
}
