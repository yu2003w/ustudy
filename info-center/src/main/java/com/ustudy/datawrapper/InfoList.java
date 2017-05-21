package com.ustudy.datawrapper;
/**
 *  Initiated by Jared on May 15, 2017.
 *  
 */

import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.ustudy.datawrapper.InterStatement.*;
import com.ustudy.datawrapper.OpResult.OpStatus;


public class InfoList {
	
	private static final Logger logger = LogManager.getLogger(InfoList.class);
	
	/**
	 * 
	 * @param datas DataSource could be used for creating connection
	 * @return String Retrieved data in JSON format
	 */
	public static OpResult getList(DataSource datas, ItemType type) {
		OpResult result = null;
		
		Connection conn = null;
		try {
			conn = datas.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = null;
			switch (type) {
			case STUDENT:
				rs= st.executeQuery(InterStatement.STU_LIST);
				String data = assembleStuList(rs);
				if (data == null)
					data = InterStatement.ResultEmpty;
				result = new OpResult(OpStatus.OP_Successful, data);
				rs.close();
				break;
			default:
				String out = "{\"Reason\":\"" + type + " is not supported\"}";
				logger.warn(out);
				result = new OpResult(OpStatus.OP_BadRequest, out);
			}
			st.close();
			conn.close();
		} catch (Exception e) {
			logger.warn("Query failed --> " + e.getMessage());
			String out = "{\"Reason\":\"Query failed\"}";
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ce) {
					logger.warn("Close connection failed -->" + e.getMessage());
				}
			}
			result = new OpResult(OpStatus.Op_ServiceUnavailable, out);
		}
		return result;
	}
	
	private static String assembleStuList(ResultSet rs) {
		String result = null;
		try {
			while (rs.next()){
				if (result == null) {
					result = "{\"Students\":[" + ItemBuilder.stuItemJson(rs);
				}
				else {
					result += "," + ItemBuilder.stuItemJson(rs);
				}
					
			}
			if (result != null) {
				result += "]}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		
		logger.debug(result);
		return result;
	}
}
