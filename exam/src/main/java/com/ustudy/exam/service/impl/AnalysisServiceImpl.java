package com.ustudy.exam.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.AnalysisMapper;
import com.ustudy.exam.model.analysis.EgsScoreAnaly;
import com.ustudy.exam.model.analysis.ExamBrifeAnaly;
import com.ustudy.exam.model.analysis.QuesObjScoreAnaly;
import com.ustudy.exam.model.analysis.QuesSubScoreAnaly;
import com.ustudy.exam.service.AnalysisService;
import com.ustudy.exam.utility.ExamUtil;

@Service
public class AnalysisServiceImpl implements AnalysisService {
	
	private static final Logger logger = LogManager.getLogger(AnalysisServiceImpl.class);

	@Autowired
	private AnalysisMapper anaM;
	
	@Override
	public List<QuesObjScoreAnaly> getObjQuesReport(long egsId, long clsId) {		
		
		List<QuesObjScoreAnaly> osL = anaM.calQuesObjReport(egsId, clsId);
		
		logger.debug("getObjQuesReport(), score analysis for egsid=" + egsId + ", clsid=" + clsId + 
				", number of items retrived " + osL.size());
		return osL;
	}

	@Override
	public List<QuesSubScoreAnaly> getSubQuesReport(long egsId, long clsId) {
		
		List<QuesSubScoreAnaly> ssL = anaM.calQuesSubReport(egsId, clsId);
		
		logger.debug("getSubQuesReport(), score analysis for egsid=" + egsId + ", clsid=" + clsId + 
				", number of items retrieved " + ssL.size());
		return ssL;
	}

	@Override
	public List<EgsScoreAnaly> getEgsScoreAnaly(long egsId, long clsId) {
		List<EgsScoreAnaly> esL = anaM.calEgsScoreRepport(egsId, clsId);
		logger.debug("getEgsScoreAnaly(), egs score report for egsid=" + egsId + ", clsId=" + clsId + 
				",details->" + esL.toString());
		return esL;
	}

	@Override
	public List<ExamBrifeAnaly> getExamsForAnaly() {
		
		String orgType = ExamUtil.retrieveSessAttr("orgType");
		String orgId = ExamUtil.retrieveSessAttr("orgId");
		if (orgId == null || orgType == null || orgId.isEmpty() || orgType.isEmpty()) {
			logger.error("getAllExamsForAnaly(), failed to retrieve org info");
			throw new RuntimeException("failed to retrieve org info");
		}
		
		logger.debug("getExamsForAnaly(), retrieve exams brife infomation for " + orgId);
		
		List<ExamBrifeAnaly> examL = anaM.getExamBrifeList(orgId);
		
		logger.trace("getExamsForAnaly(), exams->" + examL.toString());
		return examL;
	}

}
