package com.ustudy.infocent;
/**
 *  Initiated by Jared on May 15, 2017.
 *  
 */

import org.apache.tomcat.jdbc.pool.DataSource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.ustudy.datawrapper.DataProvider;
import com.ustudy.datawrapper.InterStatement;
import com.ustudy.datawrapper.ItemSchema;
import com.ustudy.datawrapper.OpResult;
import com.ustudy.datawrapper.SchemaFactory;
import com.ustudy.datawrapper.InterStatement.*;
import com.ustudy.datawrapper.OpResult.OpStatus;


public class ListAssemble {
	
	private static final Logger logger = LogManager.getLogger(ListAssemble.class);
	
	/**
	 * 
	 * @param datas DataSource for DB related operations
	 * @param type  Item Type indicated from request
	 * @param id    Start ID for query
	 * @return
	 */
	public static OpResult assemble(DataSource datas, ItemType type, int id) {
		
		OpResult result = null;
		
		ItemSchema sch = SchemaFactory.createSchema(type);
		if (sch == null) {
			logger.debug("Failed to create ItemSchema");
			result = new OpResult(OpStatus.OP_BadRequest, InterStatement.ResultTypeNotSupported);
		}
		
		if (!sch.genListSql(id, 0)) {
			result = new OpResult(OpStatus.OP_BadRequest, InterStatement.ResultQueryIDInvalid);
		}
		else
			result = DataProvider.execQuery(datas, sch);
		
		return result;
	}
	
}
