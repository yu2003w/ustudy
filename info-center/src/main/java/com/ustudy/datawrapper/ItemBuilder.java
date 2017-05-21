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
import java.util.Arrays;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.ustudy.datawrapper.InterStatement.*;
import com.ustudy.datawrapper.OpResult.OpStatus;

/**
 * @author jared
 *
 */
public class ItemBuilder {

	private static final Logger logger = LogManager.getLogger(ItemBuilder.class);
	
	public static int buildItem(DataSource ds, final ItemType type, final String data) {
		switch (type) {
		    case STUDENT:
			    String state = buildStuItem(data);
			    if (state != null) {
				    return itemCreate(ds, state);
			    }
			    break;
		    default:
			    logger.error("Unsupported type");
		}
		
		return -1;
	}
	
	public static String getItem(DataSource ds, final ItemType type, int id) {
		String item = null;
		switch (type) {
			case STUDENT:
		    	item = getStuItem(ds, id);
			    break;
		    default:
			    logger.warn("Unsupported type");
		}
		
		return item;
	}
	
	public static OpResult updateItem(DataSource ds, final ItemType type, 
			final String id, final String data) {
		OpResult res = null;
		Connection conn = null;
		try {
			String sqlState = null;
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			switch (type) {
			case STUDENT:
				sqlState = buildStuUpdateSQL(id, data);
				break;
			default:
				logger.warn("Unsupported type for " + id);
				res = new OpResult(OpStatus.OP_BadRequest,
						InterStatement.ResultNotSupported);
				conn.close();
				st.close();
				return res;
			}
			int ret = st.executeUpdate(sqlState);
			if (ret == 0) {
				logger.info("No item available for update with id " + id);
				res = new OpResult(OpStatus.OP_NoContent, InterStatement.ResultNoContent);
			}
			else {
				res = new OpResult(OpStatus.OP_Successful, InterStatement.ResultUpdated);
			}
			st.close();
			conn.close();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.warn("Encountered problems when update item.");
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ce) {
					logger.debug(ce.getMessage());
					logger.warn("Encountered problems when closing connection");
				}
			}
			res = new OpResult(OpStatus.Op_InterServerError, InterStatement.ResultDBError);
		}
		return res;
	}
	
	public static OpResult deleteItem(DataSource ds, final ItemType type, final String id) {
        OpResult res = null;
		Connection conn = null;
		try {
			String sqlState = null;
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			switch (type) {
			case STUDENT:
				sqlState = InterStatement.STU_DELETE_PREFIX + id;
				break;
			case TEACHER:
				break;
			default:
				logger.warn("Unsupported type for deletion");
				res = new OpResult(OpStatus.Op_InterServerError, 
						InterStatement.ResultNotSupported);
				return res;
			}
			
			int ret = st.executeUpdate(sqlState);
			if (ret == 0) {
				logger.info("No item available for deletion with id " + id);
				res = new OpResult(OpStatus.OP_NoContent, InterStatement.ResultNoContent);
			}
			else {
				res = new OpResult(OpStatus.OP_Successful, InterStatement.ResultDeleted);
			}
			st.close();
			conn.close();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.warn("Encountered problems when delete item.");
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ce) {
					logger.debug(ce.getMessage());
					logger.warn("Encountered problems when closing connection");
				}
			}
			res = new OpResult(OpStatus.Op_InterServerError, InterStatement.ResultDBError);
		}
		
		return res;
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
		if (name.isEmpty()) {
			logger.info("name is empty");
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
		JsonString jcate = jObj.getJsonString(InterStatement.STU_CATEG);
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
	
	private static String buildStuUpdateSQL(String id, String data) {
		String res = null;
		JsonReader reader = Json.createReader(new StringReader(data));
		JsonObject jObj = reader.readObject();
		reader.close();
		boolean first = true;
				
		// first element is table column, second element is table label
		int len = InterStatement.STU_TABLE[0].length;
		for (int i = 1; i <= len; i++) {
			JsonString jVal = jObj.getJsonString(InterStatement.STU_TABLE[1][i]);
			if (jVal != null) {
				String val = jVal.getString();
				if (!val.isEmpty()) {
					if (first) {
						first = false;
						res = "update student set " + InterStatement.STU_TABLE[0][i] +
								"=" + val;
					}
					else
						res += "," + InterStatement.STU_TABLE[0][i] + "=" + val;
				}
				// if the field is empty, just ignore it
			}	
		}
		if (!first)
			res += "where id = " + id + ";";
		logger.debug("SQL for update ->" + res);
		return res;
	}
	
	private static int itemCreate(DataSource ds, final String state) {
		Connection conn = null;
		int key = -1;
		try {
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			// here to retrieve primary key
			int ret = st.executeUpdate(state, Statement.RETURN_GENERATED_KEYS);	
			if (ret == 0) {
				logger.warn("Failed to execute statement -> " + state
						+ ". Returned value is " + ret);
				return key;
			}
			ResultSet rs = st.getGeneratedKeys();
			if (rs.next()) {
				key = rs.getInt(1); // get the auto increment key here 
			}
			else {
				logger.warn("Failed to retrieve key after insert.");
			}
			st.close();
			conn.close();
			if (key < 1) {
				logger.warn("auto increment key value after insert is invalid --> " + key);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ce) {
					logger.debug(ce.getMessage());
					logger.warn("Failed to close connection.");
				}
			}
		}
		return key;
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
				result = stuItemJson(rs);
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
	
	/**
	 * 
	 * @param rs Caller need to make sure rs is valid
	 * @return JSON format string for single student item
	 */
	public static String stuItemJson(ResultSet rs) throws SQLException {
		String result = "{\"" + InterStatement.STU_ID + "\":\"" + 
	        rs.getString(InterStatement.COL_STU_ID) +
			"\",\"" + InterStatement.STU_NAME + "\":\"" + 
	        rs.getString(InterStatement.COL_STU_NAME) + 
			"\",\"" + InterStatement.STU_GRADE + "\":\"" +
	        rs.getString(InterStatement.COL_STU_GRADE) + 
			"\",\"" + InterStatement.STU_CLASS + "\":\"" +
	        rs.getString(InterStatement.COL_STU_CLASS) + 
			"\",\"" + InterStatement.STU_NO + "\":\"" +
	        rs.getString(InterStatement.COL_STU_NO) +
			"\",\"" + InterStatement.STU_CATEG + "\":\"" +
	        rs.getString(InterStatement.COL_STU_CATEG) +
			"\",\"" + InterStatement.STU_TRANS + "\":\"" + 
	        rs.getString(InterStatement.COL_STU_TRANS) + "\"}";
		return result;
	}
}
