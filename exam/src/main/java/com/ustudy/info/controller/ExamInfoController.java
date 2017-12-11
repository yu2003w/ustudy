package com.ustudy.info.controller;

import java.util.ArrayList;
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

import com.ustudy.UResp;
import com.ustudy.info.model.ExamInfo;
import com.ustudy.info.services.ExamInfoService;
import com.ustudy.info.util.InfoUtil;

@RestController
@RequestMapping("info/exam/")
public class ExamInfoController {

	private final static Logger logger = LogManager.getLogger(ExamInfoController.class);
	
	@Autowired
	private ExamInfoService exS;
	
	@RequestMapping(value="create/", method = RequestMethod.POST)
	public UResp createExam(@RequestBody @Valid ExamInfo ex, HttpServletResponse resp) {
		UResp res = new UResp();
		if (ex == null) {
			logger.warn("createExam(), received parameter is not valid");
			res.setMessage("parameter invalid");
			return res;
		}
		
		if (ex.getSchIds() == null || ex.getSchIds().isEmpty()) {
			if (InfoUtil.retrieveSessAttr("orgType").compareTo("学校") == 0) {
				List<String> schL = new ArrayList<String>();
				schL.add(InfoUtil.retrieveSessAttr("orgId"));
				ex.setSchIds(schL);
				logger.debug("createExam(), " + ex.getSchIds().toString());
			}
			else {
				logger.warn("createExam(), no schools specified in request parameter");
				res.setMessage("parameter invalid");
				return res;
			}
		}
		
		if (ex.getGrades() == null || ex.getGrades().isEmpty()) {
			logger.warn("createExam(), no grades information specified in request parameter");
			res.setMessage("parameter invalid");
			return res;
		}
		
		logger.debug("createExam(), item to be created -> " + ex.toString());
		try {
			if (!exS.createExamInfo(ex)) {
				logger.warn("createExam(), failed to create examination");
				res.setMessage("Failed to create examination");
				resp.setStatus(500);
				return res;
			}
			logger.debug("createExam(), examination created.");
		} catch (Exception e) {
			logger.warn("createExam(), failed to create examination with exception " + e.getMessage());
			res.setMessage(e.getMessage());
			resp.setStatus(500);
			return res;
		}
		
		res.setRet(true);
		return res;
	}
	
}
