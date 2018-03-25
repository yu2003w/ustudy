package com.ustudy.mmadapter.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.mmadapter.mapper.SchoolMapper;
import com.ustudy.mmadapter.model.School;
import com.ustudy.mmadapter.service.SchoolService;

@Service
public class SchoolServiceImpl implements SchoolService {

	private static final Logger logger = LogManager.getLogger(SchoolServiceImpl.class);
	
	@Autowired
	private SchoolMapper schM;
	
	@Override
	public List<School> getSchoolsByKey(String key) {
		
		List<School> schL = schM.getSchoolsByKey(key);
		
		logger.trace("getSchoolsByKey(), schools retrieved by " + key + ":" + schL.toString());
		return schL;
	}

}
