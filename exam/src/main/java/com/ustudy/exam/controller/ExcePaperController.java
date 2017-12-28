package com.ustudy.exam.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@RequestMapping(value = "/exception/paper/{subId}", method = RequestMethod.GET)
	public UResp getErrorPapers(@PathVariable Long subId) {
		UResp res = new UResp();
		
		try {
			List<ExcePaperSum> ep = epS.getErrorPapers(subId);
			res.setData(ep);
			res.setRet(true);
			logger.info("getErrorPapers(), exception paper list retrieved successfully");
		} catch(Exception e) {
			logger.error("getErrorPapers(), failed to retrieve exception paper list," + e.getMessage());
			res.setMessage("failed to retrieve exception paper list");
		}
		
		return res;
	}
	
	@RequestMapping(value = "/paper", method = RequestMethod.POST)
	public UResp updateErrorPaper(@RequestBody String paper) {
		UResp res = new UResp();
		
		try {
			if (epS.updateErrorPaper(paper)) {
				res.setRet(true);
				logger.info("updateErrorPaper(), update exception paper successfully");
			} else {
				res.setRet(false);
				logger.info("updateErrorPaper(), update exception paper failed");
			}
		} catch(Exception e) {
			logger.error("updateErrorPaper(), update error paper failed," + e.getMessage());
			res.setMessage("failed to retrieve exception paper list");
		}
		
		return res;
	}
	
}
