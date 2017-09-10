package com.ustudy.infocenter.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * @author jared
 *
 */
public class InfoUtil {

	private static final Logger logger = LogManager.getLogger(InfoUtil.class);

	/**
	 * Json String should be in the format as below,
	 * [
	 *   {"id": "123"},
	 *   {"id": "124"}
	 * ]
	 * @param data --- json data contains content of ids
	 * @return  --- list of ids parsed from json string
	 */
	public static List<String> parseIds(String data) {
		
		JsonReader reader = Json.createReader(new StringReader(data));
		List<String> ret = new ArrayList<String>();
		try {
			JsonArray jArr = reader.readArray();
			reader.close();
			int len = jArr.size();
			for (int i = 0; i < len; i++) {
				JsonObject obj = jArr.getJsonObject(i);
				ret.add(obj.getJsonString("id").getString());
			}
		} catch (JsonException je) {
			logger.info(je.getMessage());
			logger.warn("Invalid json data in request for parsing");	
		}
		
		return ret;
	}
	
	private static ConcurrentHashMap<String, String> rolemapping = 
			new ConcurrentHashMap<String, String>();

	public static ConcurrentHashMap<String, String> getRolemapping() {
		return rolemapping;
	}

	// this method is for role mapping between frontend input and backend logic
	public static void initRoleMapping() {
		rolemapping.put("任课老师", "teacher");
		rolemapping.put("班主任", "cteacher");
		rolemapping.put("备课组长", "pleader");
		rolemapping.put("学科组长", "sleader");
		rolemapping.put("年级主任", "gleader");
		rolemapping.put("校长", "org_owner");
		rolemapping.put("考务老师", "leader");
		
		rolemapping.put("teacher", "任课老师");
		rolemapping.put("cteacher", "班主任");
		rolemapping.put("pleader", "备课组长");
		rolemapping.put("sleader", "学科组长");
		rolemapping.put("gleader", "年级主任");
		rolemapping.put("org_owner", "校长");
		rolemapping.put("leader", "考务老师");
	}
	
	/*
	 * Calling this method should catch exceptions throws by shiro
	 */
	public static String retrieveSessAttr(String key) {
		Session ses = SecurityUtils.getSubject().getSession();
		Object obj = ses.getAttribute(key);
		if (obj != null)
			return obj.toString();
		else
			return null;
	}

}
