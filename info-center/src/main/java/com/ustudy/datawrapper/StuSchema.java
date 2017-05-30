package com.ustudy.datawrapper;

import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class StuSchema extends ItemSchema {

	private static final Logger logger = LogManager.getLogger(StuSchema.class);
	
	public StuSchema() {
		schT = InterStatement.STU_TABLE;
	}
	
	@Override
	public boolean genListSql(int id, int rows) {
		
		if (id < 0) {
			logger.debug("Invalid parameter, start id is negative");
			return false;
		}
		int num = rows;
		if (num <= 0) {
			num = InterStatement.STU_LIMIT;
		}
		sqlSt = InterStatement.STU_LIST + String.valueOf(id) +
			" limit " + String.valueOf(num);
		
		return true;
	}
	
	/**
	 *  Need to parse JSON data into sql statement
	 */
	@Override
	public boolean genAddSql(String jsonData) {
		JsonReader reader = Json.createReader(new StringReader(jsonData));
		try {
			JsonObject jObj = reader.readObject();
			reader.close();
			
			// noted: before insert operation, need to check value of each field is proper
			int len = InterStatement.STU_TABLE[0].length - 1;
			String val = jObj.getString(InterStatement.STU_TABLE[1][1]);
			if (val.isEmpty()) {
				logger.warn(InterStatement.STU_TABLE[1][1]+ " must have a value");
				return false;
			}
			else
				sqlSt = InterStatement.STU_INSERT_PREFIX + val + "'";
			
			for (int i = 2; i < len; i++) {
				JsonString jVal = jObj.getJsonString(InterStatement.STU_TABLE[1][i]);
				if (jVal != null)
					sqlSt += ",'" + jVal.getString() + "'";
				else
					sqlSt += ",null";
			}
			boolean isTransient = jObj.getBoolean(InterStatement.STU_TABLE[1][len], false);
			sqlSt += "," + String.valueOf(isTransient);
			sqlSt += ");";		
		} catch (JsonException je) {
			logger.info(je.getMessage());
			logger.warn("Invalid json data in request for adding item");
			return false;
		} catch (Exception e) {
			logger.info(e.getMessage());
			logger.warn("Failed to generate sql for add item");
			return false;
		}

		// logger.debug(sqlSt);
		return true;
	}
	
	@Override
	public boolean genUpdateSql(String jsonData, String id) {
		
		JsonReader reader = Json.createReader(new StringReader(jsonData));
		try {
			JsonObject jObj = reader.readObject();
			reader.close();
			boolean first = true;
			
			// first element is table column, second element is table label
			int len = schT[0].length;
			for (int i = 1; i < len; i++) {
				String val = null;
				JsonString jVal = null;
				if (i == len -1) {
					val = String.valueOf(jObj.getBoolean(schT[1][i], false));
				}
				else {
					jVal = jObj.getJsonString(schT[1][i]);
					if (jVal != null)
						val = jVal.getString();
					if (val != null && !val.isEmpty())
						val = "\"" + val + "\"";
				}
				if (val != null && !val.isEmpty()) {
					// if the field is empty, just ignore it	
					if (first) {
						first = false;
						sqlSt = InterStatement.STU_UPDATE_PREFIX + schT[0][i] +
								" = " + val;
					}
					else
						sqlSt += ", " + schT[0][i] + " = " + val;
				}
			}
			if (!first)
				sqlSt += " where id = " + id + ";";
		} catch (JsonException je) {
			logger.info(je.getMessage());
			logger.warn("Invalid json data in request for updating item");
			return false;
		} catch (Exception e) {
			logger.info(e.getMessage());
			logger.warn("Failed to generate sql for updating item");
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean genDelSql(String id) {
		if (Integer.valueOf(id) < 0) {
			logger.debug("Invalid parameter, start id is negative");
			return false;
		}
		sqlSt = InterStatement.STU_DELETE_PREFIX + id;
		return true;
	}
	
	@Override
	public boolean genDelSetSql(String data) {
		// need to parse id set firstly
		List<String> ids = parseIds(data);
		if (ids.isEmpty()) {
			logger.debug("No valid ids provided for delete");
			return false;
		}
		sqlSt = InterStatement.STU_DELETE_PREFIX;
		int len = ids.size();
		for (int i = 0; i < len; i++) {
	        if (i == 0)
	        	sqlSt += ids.get(i);
	        else
			    sqlSt += " or id = " + ids.get(i);
		}
		logger.debug(sqlSt);
		if (sqlSt != null)
		    return true;
		return false;
	}
	
	@Override
	public boolean genGetSql(String id) {
		if (Integer.valueOf(id) < 0) {
			logger.debug("Invalid parameter, start id is negative");
			return false;
		}
		sqlSt = InterStatement.STU_GET_PREFIX + id;
		return true;
	}
	
	@Override
	public String assemble(ResultSet rs) {
		String result = null;
		try {
			while (rs.next()){
				if (result == null) {
					result = "{\"" + InterStatement.STU_LIST_HEADER + "\":[" + assembleItem(rs);
				}
				else {
					result += "," + assembleItem(rs);
				}
			}
			if (result != null) {
				result += "]}";
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			return null;
		}
		
		logger.debug(result);
		
		return result;
	}
	
	@Override
	public String assembleItem(ResultSet rs) throws SQLException {
		String result = "{\"";
		
		int len = schT[0].length;
		
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				result += schT[1][i] + "\":\"" + rs.getString(schT[0][i]);
			}
			else {
				if (i == (len - 1)) {
					result += "\",\"" + schT[1][i] + "\":" +
						    String.valueOf(rs.getBoolean(schT[0][i])) + "}";
				}
				else {
					result += "\",\"" + schT[1][i] + "\":\"" +
						    rs.getString(schT[0][i]);
				}
			}
		}
		
		return result;
	}
}
