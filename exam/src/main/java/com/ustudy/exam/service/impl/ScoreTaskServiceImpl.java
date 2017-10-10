package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.ScoreTaskMapper;
import com.ustudy.exam.model.MetaScoreTask;
import com.ustudy.exam.model.ScoreTask;
import com.ustudy.exam.service.ScoreTaskService;
import com.ustudy.exam.utility.ExamUtil;

@Service
public class ScoreTaskServiceImpl implements ScoreTaskService {

	@Autowired
	private ScoreTaskMapper scoreTaskM;
	
	@Override
	public List<ScoreTask> getScoreTask(String teacid) {
		
		List<MetaScoreTask> mstL = scoreTaskM.getMetaScoreTask(teacid);
		List<ScoreTask> stL = new ArrayList<ScoreTask>();
		for (MetaScoreTask mst: mstL) {
			stL.add(assembleScoreTask(mst));
		}
		return stL;
	}
	
	private ScoreTask assembleScoreTask(MetaScoreTask mst) {
		/*
		 *  Several steps to construct score task
		 *  1, retrieve meta score task
		 *  2, construct basic score task information
		 *  3, retrieve question paper information 
		 */
		// retrieve basic information such as exam name, grade name, subject and so on.
		ScoreTask st = scoreTaskM.getScoreTask(mst.getQuesid());
		// set teacher information
		st.setTeacherId(mst.getTeacid());
		st.setTeacherName(ExamUtil.retrieveSessAttr("uname"));
		// set score task related information
		st.setScoreType(mst.getScoreType());
		
		return st;
	}

}
