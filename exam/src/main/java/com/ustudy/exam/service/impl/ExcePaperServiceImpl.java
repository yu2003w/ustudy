package com.ustudy.exam.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ustudy.exam.mapper.ExcePaperMapper;
import com.ustudy.exam.model.ExcePaperSum;
import com.ustudy.exam.service.ExcePaperService;
import com.ustudy.exam.utility.ExamUtil;

public class ExcePaperServiceImpl implements ExcePaperService {
	
	private static final Logger logger = LogManager.getLogger(ExcePaperService.class);

	@Autowired
	private ExcePaperMapper exM;
	
	@Override
	public List<ExcePaperSum> getExcePaperSum(String schid) {
		
		List<ExcePaperSum> exP = exM.getPapersBySchool(2, schid);
		
		logger.debug("getExcePaperSum(), exception paper for school->" + schid + ", " + exP.toString());
		
		return exP;
		
	}

	public List<ExcePaperSum> getErrorPapers(Long subId) {
		
		String schId = ExamUtil.retrieveSessAttr("orgId");
		if (schId == null || schId.isEmpty()) {
			logger.error("getExcePaperSum(), failed to retrieve org id, maybe user not log in");
			throw new RuntimeException("getExcePaperSum(), failed to retrieve org id, maybe user not log in");
		}
		
		return null;
	}

	public boolean updateErrorPaper(String paper) {
		
		
		
		return false;
	}

}
