package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ustudy.exam.model.report.EgsAnsDetail;
import com.ustudy.exam.model.report.ExamineeAnsDetail;

@Mapper
public interface EgsAnsReportMapper {

	/**
	 * retrive exmainee answer details per egs id
	 * @param egsid
	 * @return
	 */
	List<ExamineeAnsDetail> getEEAnsDetails(long egsid);
	
	EgsAnsDetail getEgsAnsMeta(long egsid);
	
}
