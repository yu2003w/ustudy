package com.ustudy.datawrapper;

/**
 * Initiated by Jared on May 26, 2017.
 * 
 * Separate database logic from application logic.
 * 
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.ustudy.datawrapper.OpResult.OpStatus;

public class DataProvider {

	private static final Logger logger = LogManager.getLogger(DataProvider.class);
	
	public static OpResult execQuery(DataSource ds, ItemSchema schema) {
		
		OpResult ret = null;
		String statem = schema.getSqlSt();
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			rs= st.executeQuery(statem);
			String data = schema.assemble(rs);
			if (data == null)
				data = InterStatement.ResultEmpty;
			ret = new OpResult(OpStatus.OP_Successful, data);
			rs.close();
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
			ret = new OpResult(OpStatus.Op_ServiceUnavailable, out);
		}
		
		return ret;
	}
	
	public static OpResult execUpdate(DataSource ds, ItemSchema schema) {
		OpResult res = null;
		Connection conn = null;
		String sqlSt = schema.getSqlSt();

		try {
			conn = ds.getConnection();
			Statement st = conn.createStatement();
			int ret = st.executeUpdate(sqlSt, Statement.RETURN_GENERATED_KEYS);
			if (ret == 0) {
				logger.warn("No items are updated when executing --> " + sqlSt);
				res = new OpResult(OpStatus.OP_NoContent, InterStatement.ResultNoContent);
			}
			else {
				res = new OpResult(OpStatus.OP_Successful, InterStatement.ResultUpdated);
				// if it's insert operation, need to retrieve auto increment key
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					res.setKey(rs.getInt(1));
					logger.debug("Value of auto increment key is " + res.getKey());
				}
				rs.close();

			}
			st.close();
			conn.close();
		} catch (SQLException sqlE) {
			logger.warn("SQL problem -->" + sqlE.getMessage());
			res = new OpResult(OpStatus.Op_InterServerError, InterStatement.ResultDBError);
		} catch (Exception e) {
			logger.info(e.getMessage());
			logger.warn("Failed to update item, unexpected exceptions caught");
			res = new OpResult(OpStatus.Op_InterServerError, InterStatement.ResultInterErr);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ce) {
					logger.debug(ce.getMessage());
					logger.warn("Encountered problems when closing connection");
				}
			}
		}
		return res;
	}
}
