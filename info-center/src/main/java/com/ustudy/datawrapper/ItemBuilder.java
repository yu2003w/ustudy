package com.ustudy.datawrapper;
/**
 * 
 * @author jared
 * Initiated by Jared on May 16, 2017.
 * 
 */

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonReader;

import java.io.StringReader;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.apache.tomcat.jdbc.pool.DataSource;

public class ItemBuilder {

	private static final Logger logger = LogManager.getLogger(ItemBuilder.class);
	
	public static boolean buildItem(DataSource ds, final String type, final String data) {
		if (type.compareTo(InterStatement.STU_TYPE) == 0) {
			String state = buildStuItem(data);
			if (state != null) {
				return itemUpdate(ds, state);
			}
			return false;
		}
		return true;
	}
	
	
	public static String getItem(DataSource ds, final String type, int id) {
		if (type.compareTo(InterStatement.STU_TYPE) == 0) {
			String item = getStuItem(ds, id);
			return item;
		}
		return null;
	}
	
	/**
	 * 
	 * @param data -- JSON String for constructing student information
	 * @return sql statement to be executed.
	 */
	private static String buildStuItem(final String data) {
		JsonReader reader = Json.createReader(new StringReader(data));
		JsonObject jObj = reader.readObject();
		reader.close();
		String name = jObj.getString(InterStatement.STU_NAME);
		String stuno = jObj.getString(InterStatement.STU_NO);
		if (name.isEmpty() || stuno.isEmpty()) {
			logger.info("name or stuno is empty");
			return null;
		}
				
		String result = InterStatement.STU_INSERT_PREFIX + name + "',";
		JsonString jgrade = jObj.getJsonString(InterStatement.STU_GRADE);
		if (jgrade != null)
			result += "'" + jgrade.getString() + "',";
		else
			result += "null,";
		JsonString jstuclass = jObj.getJsonString(InterStatement.STU_CLASS);
		if (jstuclass != null)
			result += "'" + jstuclass.getString() + "',";
		else
			result += "null,";
		result += "'" + stuno + "',";
		JsonString jcate = jObj.getJsonString(InterStatement.STU_CATEGORY);
		if (jcate != null)
			result += "'" + jcate.getString() + "',";
		else
			result += "null,";
		JsonString jtrans = jObj.getJsonString(InterStatement.STU_TRANS);
		// noted, transient is boolean value
		if (jtrans != null)
			result += jtrans.getString();
		else
			result += "null";
		result += ");";
		logger.debug(result);
		return result;
	}
	
	private static boolean itemUpdate(DataSource ds, final String state) {
		Connection conn = null;

		try {
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			int ret = st.executeUpdate(state);		
			st.close();
			conn.close();
			if (ret == 1)
				return true;
			else
				return false;
		} catch (Exception e) {
			logger.warn(e.getMessage());
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ce) {
					logger.warn(ce.getMessage());
				}
			}
		}
		return false;
	}
	
	private static String getStuItem(DataSource ds, int id) {
		String result = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			String statem = InterStatement.STU_GET_PREFIX + String.valueOf(id);
			ResultSet rs = st.executeQuery(statem);
			if (rs.next()) {
				result = "{\"id\":\"" + rs.getString(InterStatement.STU_ID) +
						"\",\"Name\":\"" + rs.getString(InterStatement.STU_NAME) + 
						"\",\"Stuno\":\"" + rs.getString(InterStatement.STU_NO) +
						"\", \"Grade\":\"" + rs.getString(InterStatement.STU_GRADE) +
						"\",\"Class\":\"" + rs.getString(InterStatement.STU_CLASS) +
						"\",\"Category\":\"" + rs.getString(InterStatement.STU_CATEGORY) +
						"\",\"Transient\":\"" + rs.getString(InterStatement.STU_TRANS) + "\"}";
				if (rs.next()) {
					logger.error("Duplicated records in student for " + id);
				}
			}
			else {
				logger.info("No record fetched in student for " + id);
				return null;
			}
			rs.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			logger.warn(e.getMessage());
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ce) {
					logger.warn(ce.getMessage());
				}
			}
		}
		
		logger.debug(result);
		return result;
	}
}
