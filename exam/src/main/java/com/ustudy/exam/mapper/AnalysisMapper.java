package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ustudy.exam.model.analysis.EgsScoreAnaly;
import com.ustudy.exam.model.analysis.ExamBrifeAnaly;
import com.ustudy.exam.model.analysis.QuesObjScoreAnaly;
import com.ustudy.exam.model.analysis.QuesSubScoreAnaly;

@Mapper
public interface AnalysisMapper {

	public List<ExamBrifeAnaly> getExamBrifeList(String orgid);
	
	public List<QuesObjScoreAnaly> calQuesObjReport(@Param("egsid")long egsId, @Param("clsid") long clsId);
	
	public List<QuesSubScoreAnaly> calQuesSubReport(@Param("egsid") long egsid, @Param("clsid") long clsId);
	
	// calculate egs score report
	public List<EgsScoreAnaly> calEgsScoreRepport(@Param("egsid") long egsid, @Param("clsid") long clsId);
	
	
	
}
