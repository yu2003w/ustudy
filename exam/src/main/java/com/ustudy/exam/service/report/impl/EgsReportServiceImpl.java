package com.ustudy.exam.service.report.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.EgsReportMapper;
import com.ustudy.exam.model.report.EgsAnsDetail;
import com.ustudy.exam.model.report.SingleEgsScore;
import com.ustudy.exam.service.report.EgsReportService;

@Service
public class EgsReportServiceImpl implements EgsReportService {

	private static final Logger logger = LogManager.getLogger(EgsReportServiceImpl.class);
	
	@Autowired
	private EgsReportMapper egsReportM;

	@Override
	public EgsAnsDetail getEgsAnsReport(long egsid) {
		logger.debug("getEgsAnsReport(), retrieving answer details for egs " + egsid);
		
		EgsAnsDetail egsD = egsReportM.getEgsAnsMeta(egsid);
		if (egsD != null && ((egsD.getSubRef() != null && egsD.getSubRef().size() > 0) || 
				egsD.getObjRef() != null && egsD.getObjRef().size() > 0))
		egsD.setDetails(egsReportM.getEEAnsDetails(egsid));
		
		logger.trace("getEgsAnsReport(), " + egsD.toString());
		return egsD;
	}

	@Override
	public List<SingleEgsScore> getEgsScoreReport(long egsid) {
		logger.trace("getEgsScoreReport(), retrieving score for egs " + egsid);
		List<SingleEgsScore> scoreL = egsReportM.getSingleEgsScore(egsid);
		logger.debug("getEgsScoreReport(), items for egs " + egsid + " is " + scoreL.size());
		return scoreL;
	}

}
