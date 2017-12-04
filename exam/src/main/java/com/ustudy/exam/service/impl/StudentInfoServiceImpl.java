package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.StudentInfoDao;
import com.ustudy.exam.service.StudentInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class StudentInfoServiceImpl implements StudentInfoService {
	
	private static final Logger logger = LogManager.getLogger(StudentInfoServiceImpl.class);
	
	@Resource
	private StudentInfoDao daoImpl;

	public List getStudentsInfo(Long examId, Long gradeId) {
		logger.debug("getStudentsInfo -> examId:" + examId + ", gradeId:" + gradeId);
		return new ArrayList<>();
	}

	@Override
	public boolean saveStudentsAnswers(Long examId, Long egsId, JSONObject data) {
		logger.debug("saveStudentsAnswers -> examId:" + examId + ", egsId:" + egsId + ", data:" + data);
		return false;
	}

	@Override
	public JSONArray getStudentPapers(Long egsId) {
		logger.debug("getStudentPapers -> egsId:" + egsId);
		return null;
	}

	@Override
	public boolean deleteAnswers(Long egsId, String batchNum) {
		logger.debug("deleteAnswers -> egsId:" + egsId + ", batchNum:" + batchNum);
		return false;
	}
	
}
