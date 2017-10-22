package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.StudentInfoDao;
import com.ustudy.exam.service.StudentInfoService;

import net.sf.json.JSONObject;

@Service
public class StudentInfoServiceImpl implements StudentInfoService {
	
	private static final Logger logger = LogManager.getLogger(StudentInfoServiceImpl.class);
	
	@Resource
	private StudentInfoDao daoImpl;

	public List getStudentsInfo(String examId, String gradeId) {
		logger.debug("getStudentsInfo -> examId:" + examId + ", gradeId:" + gradeId);
		return new ArrayList<>();
	}

	@Override
	public boolean saveStudentsAnswers(String egId, String csId, JSONObject data) {
		logger.debug("saveStudentsAnswers -> egId:" + egId + ", csId:" + csId + ", data:" + data);
		return false;
	}

	@Override
	public List getAllPaper(String csId) {
		logger.debug("getAllPaper -> csId:" + csId);
		return null;
	}

	@Override
	public boolean deleteAnswers(String csId, String batchNum) {
		logger.debug("deleteAnswers -> csId:" + csId + ", batchNum:" + batchNum);
		return false;
	}
	
}
