package com.ustudy.exam.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.UResp;
import com.ustudy.exam.model.ExcePaperSum;
import com.ustudy.exam.service.ExcePaperService;
import com.ustudy.exam.utility.ExamUtil;

@RestController
@RequestMapping(value="/exam/")
public class ExcePaperController {

	private static final Logger logger = LogManager.getLogger(ExcePaperController.class);
	
	@Autowired
	private ExcePaperService epS;
	
	@RequestMapping(value = "/expaper/sum/", method = RequestMethod.GET)
	public UResp getExcePaperSum(HttpServletResponse resp) {
		UResp res = new UResp();
		
		String schId = ExamUtil.retrieveSessAttr("orgId");
		if (schId == null || schId.isEmpty()) {
			logger.error("getExcePaperSum(), failed to retrieve org id, maybe user not log in");
			throw new RuntimeException("getExcePaperSum(), failed to retrieve org id, maybe user not log in");
		}
		
		try {
			List<ExcePaperSum> ep = epS.getExcePaperSum(schId);
			res.setData(ep);
			res.setRet(true);
			logger.info("getExcePaperSum(), exception paper list retrieved successfully");
		} catch(Exception e) {
			logger.error("getExcePaperSum(), failed to retrieve exception paper list," + e.getMessage());
			res.setMessage("failed to retrieve exception paper list");
			resp.setStatus(500);
		}
		
		return res;
	}
	
}
