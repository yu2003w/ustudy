package com.ustudy.exam.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.UResp;
import com.ustudy.exam.service.AnswerSheetService;

@RestController
@RequestMapping(value = "/answersheet/")
public class AnswerSheetController {

	private static final Logger logger = LogManager.getLogger(AnswerSheetController.class);
	
	@Autowired
	private AnswerSheetService ansS;
	
	@RequestMapping(value = "summary/", method = RequestMethod.GET)
	public UResp retrieveAnswerSheet(HttpServletResponse resp) {
		
		UResp res = new UResp();
		
		logger.debug("retrieveAnswerSheet(), to retrieve answer sheet meta information");
		try {
			res.setData(ansS.getAnswerSheetMeta());
			res.setRet(true);
		} catch (Exception e) {
			logger.error("retrieveAnswerSheet(), exception->" + e.getMessage());
			res.setMessage(e.getMessage());
			resp.setStatus(500);
		}
		
		return res;
	}
	
}
