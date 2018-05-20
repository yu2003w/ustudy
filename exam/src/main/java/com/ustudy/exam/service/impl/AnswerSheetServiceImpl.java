package com.ustudy.exam.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.AnswerSheetMapper;
import com.ustudy.exam.model.anssheet.AnsPaper;
import com.ustudy.exam.model.anssheet.ExamGrSubMeta;
import com.ustudy.exam.service.AnswerSheetService;
import com.ustudy.exam.utility.ExamUtil;

@Service
public class AnswerSheetServiceImpl implements AnswerSheetService {

	private static final Logger logger = LogManager.getLogger(AnswerSheetServiceImpl.class);
	
	@Autowired
	private AnswerSheetMapper ansM;
	
	@Override
	public List<ExamGrSubMeta> getAnswerSheetMeta() {
		
		String orgType = ExamUtil.retrieveSessAttr("orgType");
		String orgId = ExamUtil.retrieveSessAttr("orgId");
		if (orgId == null || orgType == null || orgId.isEmpty() || orgType.isEmpty()) {
			logger.error("getAnswerSheetMeta(), failed to retrieve org info");
			throw new RuntimeException("failed to retrieve org info");
		}
		
		logger.debug("getAnswerSheetMeta(), retrieve exams brife infomation for " + orgId);
		
		List<ExamGrSubMeta> meta = ansM.getAnswerSheetMeta(orgId);
		
		logger.debug("getAnswerSheetMeta(), " + meta.size() + " items retrieved for " + orgId);
		
		return meta;
	}

	@Override
	public List<AnsPaper> getAnsPapers(long quesid, String type, long clsid, String key) {
		List<AnsPaper> apL = ansM.getAnsPapers(quesid, type, clsid, key);
		logger.debug("getAnsPapers(), quesid=" + quesid + ", type=" + type + ", " + apL.size() + 
				" items retrieved");
		return apL;
	}

}
