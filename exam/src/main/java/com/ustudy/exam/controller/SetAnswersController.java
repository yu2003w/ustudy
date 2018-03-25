package com.ustudy.exam.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.exam.service.ExamSubjectService;
import com.ustudy.UResp;
import com.ustudy.exam.service.AnswerService;

import net.sf.json.JSONObject;

@RestController
@RequestMapping(value = "/setanswers")
public class SetAnswersController {

	private static final Logger logger = LogManager.getLogger(SetAnswersController.class);

	@Autowired
	private AnswerService ansSer;
	
	@Autowired
	private ExamSubjectService exSubSer;

	@RequestMapping(value = "/answers/{egsId}", method = RequestMethod.GET)
	public UResp getQuesAnswers(@PathVariable Long egsId, HttpServletRequest request, 
			HttpServletResponse response) {

		logger.debug("getQuesAnswers(), retrieving answers for " + egsId);

		UResp res = new UResp();

		try {
			res.setData(ansSer.getQuesAnswer(egsId));
			res.setRet(true);
		} catch (Exception e) {
			res.setMessage("retrieve answers failed with exception->" + e.getMessage());
			logger.error("getQuesAnswers(), retrieved answers failed for " + egsId + ", " + e.getMessage());
		}

		return res;
	}

	@RequestMapping(value = "/answers/{egsId}", method = RequestMethod.POST)
	public UResp saveQuesAnswers(@PathVariable Long egsId, @RequestBody String paras, 
			HttpServletRequest request,	HttpServletResponse response) {

		logger.debug("saveQuesAnswers(), save answers for " + egsId + " with parameters->" + paras);
		
		UResp res = new UResp();

		try {
			JSONObject data = JSONObject.fromObject(paras);
			if (ansSer.saveQuesAnswers(egsId, data) && exSubSer.isAnswerSet(egsId).isRet()) {
				res.setRet(true);
			}
			
		} catch (Exception e) {
			res.setMessage("save answers failed with exception->" + e.getMessage());
			logger.error("saveQuesAnswers(), save answers for " + egsId + 
					" failed with exception->" + e.getMessage());
			e.printStackTrace();
		}

		return res;
	}

}
