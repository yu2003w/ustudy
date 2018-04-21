package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.analysis.EgsScoreAnaly;
import com.ustudy.exam.model.analysis.QuesObjScoreAnaly;
import com.ustudy.exam.model.analysis.QuesSubScoreAnaly;

public interface AnalysisService {

	List<QuesObjScoreAnaly> getObjQuesReport(long egsId, long clsId);
	
	List<QuesSubScoreAnaly> getSubQuesReport(long egsId, long clsId);
	
	List<EgsScoreAnaly> getEgsScoreAnaly(long egsId, long clsId);
	
}
