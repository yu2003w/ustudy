package com.ustudy.datawrapper;
/**
 *  Initiated by Jared on May 15, 2017.
 *  
 */

import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.ustudy.datawrapper.InterStatement;

public class InfoList {
	
	/**
	 * 
	 * @param datas DataSource could be used for creating connection
	 * @return String Retrieved data in JSON format
	 */
	public static String getList(DataSource datas, String type) {
		String result = null;
		
		Connection conn = null;
		try {
			conn = datas.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = null;
			if (type.compareTo(InterStatement.STU_TYPE) == 0)
				rs = st.executeQuery(InterStatement.STU_LIST);
			result = assembleList(rs);
			rs.close();
			st.close();
			conn.close();
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
		return result;
		
	}
	
	private static String assembleList(ResultSet rs) {
		String result = null;
		try {
			while (rs.next()){
				if (result == null) {
					result = "{\"Students\":[{\"Name\":\"" + rs.getString("Name") + 
							"\", \"Grade\":\"" + rs.getString("Grade") + 
							"\",\"Class\":\"" + rs.getString("Class") + "\"}";;
				}
				else {
					result += ",{\"Name\":\"" + rs.getString("Name") + 
							"\", \"Grade\":\"" + rs.getString("Grade") +
							"\",\"Class\":\"" + rs.getString("Class") + "\"}";
				}
					
			}
			if (result != null) {
				result += "]}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
}
