package com.ustudy.exam.service.report.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.EgsAnsReportMapper;
import com.ustudy.exam.model.report.EgsAnsDetail;
import com.ustudy.exam.service.report.EgsAnsReport;

@Service
public class EgsAnsReportImpl implements EgsAnsReport {

	private static final Logger logger = LogManager.getLogger(EgsAnsReportImpl.class);
	
	@Autowired
	private EgsAnsReportMapper egsAnsM;

	@Override
	public EgsAnsDetail getEgsAnsReport(long egsid) {
		logger.debug("getEgsAnsReport(), retrieving answer details for egs " + egsid);
		
		EgsAnsDetail egsD = egsAnsM.getEgsAnsMeta(egsid);
		if (egsD != null && ((egsD.getSubRef() != null && egsD.getSubRef().size() > 0) || 
				egsD.getObjRef() != null && egsD.getObjRef().size() > 0))
		egsD.setDetails(egsAnsM.getEEAnsDetails(egsid));
		
		logger.trace("getEgsAnsReport(), " + egsD.toString());
		return egsD;
	}

}
