package com.ustudy.exam.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ustudy.exam.mapper.ExcePaperMapper;
import com.ustudy.exam.model.ExcePaperSum;
import com.ustudy.exam.service.ExcePaperService;

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

}
