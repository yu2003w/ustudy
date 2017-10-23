package com.ustudy.exam.service.impl;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ExamDao;
import com.ustudy.exam.model.Quesarea;
import com.ustudy.exam.service.QuesareaService;

@Service
public class QuesareaServiceImpl implements QuesareaService {
	
	private static final Logger logger = LogManager.getLogger(QuesareaServiceImpl.class);
	
	@Resource
	private ExamDao examDaoImpl;

	@Override
	public boolean insertQuesarea(Quesarea quesarea) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
