package com.ustudy.exam.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.AnalysisMapper;
import com.ustudy.exam.model.analysis.EgsScoreAnaly;
import com.ustudy.exam.model.analysis.QuesObjScoreAnaly;
import com.ustudy.exam.model.analysis.QuesSubScoreAnaly;
import com.ustudy.exam.service.AnalysisService;

@Service
public class AnalysisServiceImpl implements AnalysisService {
	
	private static final Logger logger = LogManager.getLogger(AnalysisServiceImpl.class);

	@Autowired
	private AnalysisMapper anaM;
	
	@Override
	public List<QuesObjScoreAnaly> getObjQuesReport(long egsId, long clsId) {
		
		logger.debug("getObjQuesReport(), retrieve info for egsid=" + egsId + ", clsid=" + clsId);
		
		return anaM.calQuesObjReport(egsId, clsId);
	}

	@Override
	public List<QuesSubScoreAnaly> getSubQuesReport(long egsId, long clsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EgsScoreAnaly> getEgsScoreAnaly(long egsId, long clsId) {
		// TODO Auto-generated method stub
		return null;
	}

}
