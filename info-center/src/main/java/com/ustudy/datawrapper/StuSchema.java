package com.ustudy.datawrapper;

import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			String name = jObj.getString(InterStatement.STU_NAME);
			String stuno = jObj.getString(InterStatement.STU_NO);
			if (name.isEmpty()) {
				logger.warn("name is empty which is not allowed");
				return false;
			}
					
			sqlSt = InterStatement.STU_INSERT_PREFIX + name + "',";
			JsonString jgrade = jObj.getJsonString(InterStatement.STU_GRADE);
			if (jgrade != null)
				sqlSt += "'" + jgrade.getString() + "',";
			else
				sqlSt += "null,";
			JsonString jstuclass = jObj.getJsonString(InterStatement.STU_CLASS);
			if (jstuclass != null)
				sqlSt += "'" + jstuclass.getString() + "',";
			else
				sqlSt += "null,";
			sqlSt += "'" + stuno + "',";
			JsonString jcate = jObj.getJsonString(InterStatement.STU_CATEG);
			if (jcate != null)
				sqlSt += "'" + jcate.getString() + "',";
			else
				sqlSt += "null,";
			JsonString jtrans = jObj.getJsonString(InterStatement.STU_TRANS);
			// noted, transient is boolean value
			if (jtrans != null)
				sqlSt += jtrans.getString();
			else
				sqlSt += "null";
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
				
				JsonString jVal = jObj.getJsonString(schT[1][i]);
				if (jVal != null) {
					String val = jVal.getString();
					if (!val.isEmpty()) {
						if (first) {
							first = false;
							sqlSt = InterStatement.STU_UPDATE_PREFIX + schT[0][i] +
									" = \"" + val + "\"";
						}
						else
							sqlSt += ", " + schT[0][i] + " = \"" + val + "\"";
					}
					// if the field is empty, just ignore it
				}	
			}
			if (!first)
				sqlSt += " where id = " + id + ";";
			logger.debug("SQL for update ->" + sqlSt);
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
						    Boolean.valueOf(rs.getString(schT[0][i])) + "}";
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
