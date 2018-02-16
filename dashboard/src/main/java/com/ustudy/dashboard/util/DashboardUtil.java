package com.ustudy.dashboard.util;

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

public class DashboardUtil {
	
	private static final Logger logger = LogManager.getLogger(DashboardUtil.class);

	/**
	 * Json String should be in the format as below,
	 * [
	 *   {"id": 123},
	 *   {"id": 124}
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
				ret.add(String.valueOf(obj.getInt("id")));
			}
		} catch (JsonException je) {
			logger.error("parseIds(), invalid json data->" + je.getMessage());	
		}
		
		return ret;
	}
	
	private static ConcurrentHashMap<String, String> acctRoleM = 
			new ConcurrentHashMap<String, String>();

	public static ConcurrentHashMap<String, String> getAcctRoleMap() {
		if(null == acctRoleM || acctRoleM.size() == 0){
			initRoleMapping();
		}
		return acctRoleM;
	}
	
	public static void initRoleMapping() {
		acctRoleM.put("operator", "运维");
		acctRoleM.put("sales", "市场");
		acctRoleM.put("reseller", "代理商");
		acctRoleM.put("visitor", "临时帐号");
		
		acctRoleM.put("运维", "operator");
		acctRoleM.put("市场", "sales");
		acctRoleM.put("代理商", "reseller");
		acctRoleM.put("临时帐号", "visitor");
	}
	
}
