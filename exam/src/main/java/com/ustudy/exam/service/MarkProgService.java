package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.statics.ExamMarkProgress;
import com.ustudy.exam.model.statics.QuesMarkMetrics;
import com.ustudy.exam.model.statics.TeaMarkProgress;

public interface MarkProgService {

	public List<ExamMarkProgress> getExamMarkProg(String sid);
	
	public List<QuesMarkMetrics> getEgsMarkProg(int eid, int egsid);
	
	//public Collection<Map<String, Object>> getTeacherMarkProgress(String orgId, int egsId);
	
	public List<TeaMarkProgress> getTeaMarkProg(String orgId, int egsid);
	
}
