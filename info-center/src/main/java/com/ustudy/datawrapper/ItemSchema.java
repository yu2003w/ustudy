package com.ustudy.datawrapper;


/**
 * @author jared
 *
 */

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ItemSchema {

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
	
}
