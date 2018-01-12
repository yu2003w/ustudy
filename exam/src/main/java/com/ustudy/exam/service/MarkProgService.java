package com.ustudy.exam.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ustudy.exam.model.statics.ExamMarkProgress;
import com.ustudy.exam.model.statics.QuesMarkMetrics;

public interface MarkProgService {

	public List<ExamMarkProgress> getExamMarkProg(String sid);
	
	public List<QuesMarkMetrics> getEgsMarkProg(int eid, int egsid);
	
	public Collection<Map<String, Object>> getTeacherMarkProgress(String orgId, int egsId);
	
}
