package com.ustudy.dashboard.services.imp;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.dashboard.mapper.ConfigMapper;
import com.ustudy.dashboard.model.Subject;
import com.ustudy.dashboard.services.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {

	private static final Logger logger = LogManager.getLogger(ConfigService.class);
	
	@Autowired
	private ConfigMapper confM;
	
	@Override
	public List<Subject> getSubList() {
		List<Subject> subL = confM.getSubList();
		
		if (subL == null || subL.isEmpty()) {
			logger.error("getSubList(), no subject retrieved, please check system configuration");
			throw new RuntimeException("No subject retrieved, please check system configuration");
		}
		
		logger.debug("getSubList(), subject configured->" + subL.toString());
		return subL;
	}

}
