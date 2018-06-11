package com.ustudy.exam.service.report;

import java.util.List;

import com.ustudy.exam.model.report.EgsAnsDetail;
import com.ustudy.exam.model.report.SingleEgsScore;

public interface EgsReportService {
	
	EgsAnsDetail getEgsAnsReport(long egsid);
	
	List<SingleEgsScore> getEgsScoreReport(long egsid);
	
}
