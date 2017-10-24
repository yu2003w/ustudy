package com.ustudy.exam.service.impl;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.StudentObjectAnswerDao;
import com.ustudy.exam.dao.StudentPaperDao;
import com.ustudy.exam.service.StudentAnswerService;

import net.sf.json.JSONObject;

@Service
public class StudentAnswerServiceImpl implements StudentAnswerService {
	
	private static final Logger logger = LogManager.getLogger(StudentAnswerServiceImpl.class);
	
	@Resource
	private StudentPaperDao paperDaoImpl;
	
	@Resource
	private StudentObjectAnswerDao answerDaoImpl;

	public boolean saveStudentsAnswers(String egId, String csId, JSONObject data) {
		logger.debug("saveStudentsAnswers -> egId:" + egId + ", csId:" + csId + ", data:" + data);
		return false;
	}
	
}
