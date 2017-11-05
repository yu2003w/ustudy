package com.ustudy.exam.service.impl;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.MultipleScoreSetDao;
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.QuesAnswerDivDao;
import com.ustudy.exam.dao.RefAnswerDao;
import com.ustudy.exam.service.TaskAllocationService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class TaskAllocationServiceImpl implements TaskAllocationService {
	
	private static final Logger logger = LogManager.getLogger(TaskAllocationServiceImpl.class);
	
	@Resource
	private QuesAnswerDao quesAnswerDaoImpl;
	
	@Resource
	private QuesAnswerDivDao quesAnswerDivDaoImpl;
	
	@Resource
	private RefAnswerDao refAnswerDaoImpl;
	
	@Resource
	private MultipleScoreSetDao multipleScoreSetDaoImpl;

	@Override
	public JSONArray getQuestions(int egsId) throws Exception {
		logger.info("getQuestions -> egsId=" + egsId);
		return null;
	}

	@Override
	public JSONObject getSchools(int schoolId) throws Exception {
		logger.info("getSchools -> schoolId=" + schoolId);
		return null;
	}

	@Override
	public JSONObject getGrades(int gradeId) throws Exception {
		// logger.info("getGrades -> gradeId=" + gradeId);
		return null;
	}

}
