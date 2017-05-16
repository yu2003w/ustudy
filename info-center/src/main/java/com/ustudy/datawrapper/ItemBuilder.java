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

import org.apache.tomcat.jdbc.pool.DataSource;

public class ItemBuilder {

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
			System.out.println("buildStuItem() called");
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
		System.out.println(result);
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
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ce) {
					ce.printStackTrace();
				}
			}
		}
		return false;
	}
}
