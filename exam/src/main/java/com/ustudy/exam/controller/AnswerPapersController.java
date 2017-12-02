package com.ustudy.exam.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.UResp;
import com.ustudy.exam.service.StudentPaperService;

@RestController
public class AnswerPapersController {

	private static final Logger logger = LogManager.getLogger(AnswerPapersController.class);

	@Autowired
	private StudentPaperService service;
	
	@RequestMapping(value = "/answer/papers")
	public UResp getAnswerPapers(@RequestParam Long egsId, 
			@RequestParam(value="questionId", defaultValue="0") Long questionId,
			@RequestParam(value="classId", defaultValue="0") Long classId,
			@RequestParam(value="type", defaultValue="") String type,
			@RequestParam(value="text", defaultValue="") String text,
			@RequestParam(value="viewAnswerPaper", defaultValue="false") Boolean viewAnswerPaper) {

		logger.debug("getAnswerPapers().");
		logger.debug("egsId: " + egsId + ",questionId: " + questionId + ",classId: " + classId + ",type: " + type + ",text: " + text + ",viewAnswerPaper: " + viewAnswerPaper);

		UResp result = new UResp();
		try {
			result.setRet(true);
			result.setData(service.getAnswerPapers(egsId, questionId, classId, type, text, viewAnswerPaper));
		} catch (Exception e) {
			result.setRet(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

}
