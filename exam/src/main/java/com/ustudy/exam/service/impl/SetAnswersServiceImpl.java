package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.QuesAnswerDivDao;
import com.ustudy.exam.dao.RefAnswerDao;
import com.ustudy.exam.model.QuesAnswer;
import com.ustudy.exam.model.QuesAnswerDiv;
import com.ustudy.exam.model.RefAnswer;
import com.ustudy.exam.service.SetAnswersService;

import net.sf.json.JSONObject;

@Service
public class SetAnswersServiceImpl implements SetAnswersService {
	
	private static final Logger logger = LogManager.getLogger(SetAnswersServiceImpl.class);
	
	@Resource
	private QuesAnswerDao quesAnswerDaoImpl;
	
	@Resource
	private QuesAnswerDivDao quesAnswerDivDaoImpl;
	
	@Resource
	private RefAnswerDao refAnswerDaoImpl;

	public Map<String, Object> getQuesAnswer(int egsId) throws Exception {
		
		logger.info("getQuesAnswer -> egsId=" + egsId);
		
		try {
			Map<String, Object> result = new HashMap<>();
			
			result.put("quesAnswers", quesAnswerDaoImpl.getQuesAnswers(egsId));
			result.put("quesAnswerDivs", quesAnswerDivDaoImpl.getAllQuesAnswerDivs(egsId));
			result.put("refAnswers", refAnswerDaoImpl.getRefAnswers(egsId));
			
			return result;
		} catch (Exception e) {
			logger.error("getQuesAnswer select error. -> msg = " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
	}

	public boolean deleteQuesAnswers(int egsId) throws Exception {
		
		logger.info("deleteQuesAnswers -> egsId=" + egsId);
		
		try {
			quesAnswerDivDaoImpl.deleteQuesAnswerDivs(egsId);
			quesAnswerDaoImpl.deleteQuesAnswers(egsId);
			refAnswerDaoImpl.deleteRefAnswers(egsId);
			
			return true;
		} catch (Exception e) {
			logger.error("deleteQuesAnswers delete error. -> msg = " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
	}

	public boolean saveQuesAnswers(int egsId, JSONObject ques) throws Exception {
		
		try {
			deleteQuesAnswers(egsId);
			
			List<RefAnswer> refAnswers = new ArrayList<>();
			if(!refAnswers.isEmpty()){
				refAnswerDaoImpl.insertRefAnswers(refAnswers);
			}
			List<QuesAnswerDiv> quesAnswerDivs = new ArrayList<>();
			if(!quesAnswerDivs.isEmpty()){
				quesAnswerDivDaoImpl.insertQuesAnswerDivs(quesAnswerDivs);
			}
			List<QuesAnswer> quesAnswers = new ArrayList<>();
			if(!quesAnswers.isEmpty()){
				quesAnswerDaoImpl.insertQuesAnswers(quesAnswers);
			}
			
			return true;
		} catch (Exception e) {
			logger.error("saveQuesAnswers save error. -> msg = " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
	}

}
