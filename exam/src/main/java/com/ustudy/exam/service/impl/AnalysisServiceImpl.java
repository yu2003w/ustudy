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
		
		for (QuesObjScoreAnaly qos: osL) {
			qos.adjustChoices();
		}
		
		logger.debug("getObjQuesReport(), score analysis for egsid=" + egsId + ", clsid=" + clsId + 
				", number of items retrived " + osL.size());
		logger.trace("getObjQuesReport(), details->" + osL.toString());
		return osL;
	}

	@Override
	public List<QuesSubScoreAnaly> getSubQuesReport(long egsId, long clsId) {
		
		List<QuesSubScoreAnaly> ssL = anaM.calQuesSubReport(egsId, clsId);
		
		logger.debug("getSubQuesReport(), score analysis for egsid=" + egsId + ", clsid=" + clsId + 
				", number of items retrieved " + ssL.size());
		logger.trace("getSubQuesReport(), details->" + ssL.toString());
		return ssL;
	}

	@Override
	public List<EgsScoreAnaly> getEgsScoreAnaly(long egsId, long clsId) {
		List<EgsScoreAnaly> esL = anaM.calEgsScoreRepport(egsId, clsId);
		logger.debug("getEgsScoreAnaly(), egs score report for egsid=" + egsId + ", clsId=" + clsId);
		logger.trace("getEgsScoreAnaly(), details->" + esL.toString());
		return esL;
	}

	@Override
	public List<ExamBrifeAnaly> getExamsForAnaly() {
		
		String orgType = ExamUtil.retrieveSessAttr("orgType");
		String orgId = ExamUtil.retrieveSessAttr("orgId");
		String teaid = ExamUtil.retrieveSessAttr("uid");
		String tearole = ExamUtil.retrieveSessAttr("role");
		if (orgId == null || orgType == null || orgId.isEmpty() || orgType.isEmpty() || 
				teaid == null || teaid.isEmpty() || tearole == null || tearole.isEmpty()) {
			logger.error("getAllExamsForAnaly(), failed to user identity information");
			throw new RuntimeException("failed to retrieve org info");
		}
		
		logger.debug("getExamsForAnaly(), retrieve exams brife infomation for orgid=" + orgId + ", teaid=" + teaid + 
				",tearole=" + tearole);
		
		//filter result set based on user's role and subject
		// TODO: authentication metrics needed here
		List<ExamBrifeAnaly> examL = null;
		if (tearole.equals("清道夫") || tearole.equals("校长") || tearole.equals("考务老师")) {
			examL = anaM.getExamBrifeList(orgId, null);
		} /* else if (tearole.equals("年级主任")) {
			// access all subjects in his grade
			examL = anaM.getExamBrifeList(orgId, teaid);
		} else if (tearole.equals("学科组长")) {
			// access only his subjects
			examL = anaM.getExamBrifeList(orgId, teaid);
		} */ else {
			examL = anaM.getExamBrifeList(orgId, teaid);
		}
		
		logger.trace("getExamsForAnaly(), details->" + examL.toString());
		return examL;
	}

}
