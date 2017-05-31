package com.ustudy.datawrapper;


/**
 * @author jared
 *
 */

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonArray;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.StringReader;
import java.util.List;
import java.util.ArrayList;

public abstract class ItemSchema {

	private static final Logger logger = LogManager.getLogger(ItemSchema.class);
	// table schema
	protected String [][] schT = null;
	// sql statement 
	protected String sqlSt = null;
	
	public abstract boolean genListSql(int id, int rows);
	// add item
	public abstract boolean genAddSql(String para);
	// update existed item
	public abstract boolean genUpdateSql(String data, String id);
	//delete single item
	public abstract boolean genDelSql(String id);
	// delete item set
	public abstract boolean genDelSetSql(String data);
	// retrieve single item
	public abstract boolean genGetSql(String id);
	
	public abstract String assemble(ResultSet rs);
		
	public String[][] getTableSchema() {
		return schT;
	}
	
	// this method is necessary because low level
	// data provider is not aware of high level logic
	public String getSqlSt() {
		return sqlSt;
	}
	
	/**
	 * 
	 * @param rs ResultSet for assembling item
	 * @return  JSON string for single item
	 * @throws SQLException
	 */
	public String assembleItem(ResultSet rs) throws SQLException {
		String result = "{\"";
		boolean first = true;
		int len = schT[0].length;
		
		for (int i = 0; i < len; i++) {
			if (first) {
				result += schT[1][i] + "\":\"" + rs.getString(schT[0][i]);
				first = false;
			}
			else {
				result += "\",\"" + schT[1][i] + "\":\"" +
					rs.getString(schT[0][i]);
			}
		}
		result += "\"}";
		
		return result;
	}
	
	/**
	 * Json String should be in the format as below,
	 * {"ids":[
	 *   {"id": "123"},
	 *   {"id": "124"}
	 *   ]
	 * }
	 * @param data
	 * @return
	 */
	protected List<String> parseIds(String data) {
		
		JsonReader reader = Json.createReader(new StringReader(data));
		List<String> ret = new ArrayList<String>();
		try {
			JsonArray jArr = reader.readArray();
			reader.close();
			int len = jArr.size();
			for (int i = 0; i < len; i++) {
				JsonObject obj = jArr.getJsonObject(i);
				ret.add(obj.getJsonString(InterStatement.ID).getString());
			}
		} catch (JsonException je) {
			logger.info(je.getMessage());
			logger.warn("Invalid json data in request for updating item");	
		} catch (Exception e) {
			logger.info(e.getMessage());
			logger.warn("Failed to generate sql for updating item");
		}
		
		return ret;
	}
	
}
