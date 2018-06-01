package com.ustudy.dashboard.services.imp;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonReader;

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
		
		// parse and populate basic subject information for child subs
		Map<Integer, String> subM = new HashMap<Integer, String>();
		for (Subject sub: subL) {
			if (sub.getChild() == null || sub.getChild().isEmpty()) {
				subM.put(Integer.valueOf(sub.getSubId()), sub.getSubName());
			}
		}
		logger.trace("getSubList(), basic subject->" + subM.toString());
		for (Subject sub: subL) {
			if (sub.getChild() != null && sub.getChild().length() > 0) {
				sub.setChildSubs(getChilds(sub.getChild(), subM));
			}
		}
		logger.debug("getSubList(), subjects->" + subL.toString());
		return subL;
	}

	
	/**
	 * @param child  --- child subjects with format [7,8,9]
	 * @param subM  --- basic subject dictionary
	 * @return
	 */
	private TreeMap<Integer, String> getChilds(String child, Map<Integer, String> subM) {
		TreeMap<Integer, String> childSubM = new TreeMap<Integer, String>();
		JsonReader reader = Json.createReader(new StringReader(child));
		JsonArray ids = reader.readArray();
		reader.close();
		for (int i = 0; i < ids.size(); i++) {
			JsonNumber sid = ids.getJsonNumber(i);
			childSubM.put(Integer.valueOf(sid.intValue()), subM.get(sid.intValue()));
		}
		return childSubM;
	}
}
