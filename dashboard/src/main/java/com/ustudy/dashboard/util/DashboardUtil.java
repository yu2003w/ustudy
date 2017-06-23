package com.ustudy.dashboard.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DashboardUtil {
	
	private static final Logger logger = LogManager.getLogger(DashboardUtil.class);

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
}
