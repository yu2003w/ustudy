package com.ustudy.info.services.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.info.mapper.ExamInfoMapper;
import com.ustudy.info.model.ExamInfo;
import com.ustudy.info.model.GradeSubs;
import com.ustudy.info.services.ExamInfoService;

@Service
public class ExamInfoServiceImpl implements ExamInfoService {
	
	private final static Logger logger = LogManager.getLogger(ExamInfoServiceImpl.class);

	@Autowired
	private ExamInfoMapper exM;
	
	@Override
	@Transactional
	public void createExamInfo(ExamInfo ex) {
		
		int ret = exM.createExamInfo(ex);
		if (ret < 0 || ret > 2 || ex.getId() < 0) {
			logger.error("createExamInfo(), failed to create examination " + ex);
			throw new RuntimeException("createExamInfo(), failed to create exam record.");
		}
		
		// populate exam school
		for (String sid:ex.getSchIds()) {
			ret = exM.createExamSchool(ex.getId(), sid);
			if (ret < 0 || ret > 2) {
				logger.error("createExamInfo(), failed to populate exam school record " + sid);
				throw new RuntimeException("createExamInfo(), failed to populate exam school record.");
			}
		}
		
		// populate grades information
		for (GradeSubs gss:ex.getGrades()) {
			for (int sub_id:gss.getSubjectIds()) {
				ret = exM.createExamGradeSub(ex.getId(), gss.getId(), sub_id);
				if (ret < 0 || ret > 2) {
					logger.error("createExamInfo(), failed to populate examgradesub record " + gss.getId() + 
							"->" + sub_id);
					throw new RuntimeException("createExamInfo(), failed to populate examgradesub record.");
				}
			}
			
			
		}
		
	}

	@Override
	@Transactional
	public void deleteExamInfo(int id) {
		int ret = exM.deleteExamInfo(id);
		if (ret != 1) {
			logger.error("deleteExamInfo(), exam info delete failed for " + id);
			throw new RuntimeException("deleteExamInfo(), failed to delete exam for " + id);
		}
		
	}

}
