package com.ustudy.infocent;
/**
 * 
 * @author jared
 * Initiated by Jared on May 16, 2017.
 * 
 * Class for handling add/get/post item request.
 * 
 */

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.ustudy.datawrapper.DataProvider;
import com.ustudy.datawrapper.InterStatement;
import com.ustudy.datawrapper.ItemSchema;
import com.ustudy.datawrapper.OpResult;
import com.ustudy.datawrapper.SchemaFactory;
import com.ustudy.datawrapper.InterStatement.*;
import com.ustudy.datawrapper.OpResult.OpStatus;

/**
 * @author jared
 *
 */
public class ItemManager {

	private static final Logger logger = LogManager.getLogger(ItemManager.class);
	
	public static OpResult buildItem(DataSource ds, final ItemType type, final String data) {
		OpResult ret = null;
		ItemSchema sch = SchemaFactory.createSchema(type);
		if (sch == null) {
			logger.debug("Failed to create ItemSchema");
			ret = new OpResult(OpStatus.OP_BadRequest, InterStatement.ResultTypeNotSupported);
		}
		if (!sch.genAddSql(data)) {
			ret = new OpResult(OpStatus.OP_BadRequest, InterStatement.ResultDataInvalid);
		}
		else {
			ret = DataProvider.execUpdate(ds, sch);
		}
		
		return ret;
		
	}
	
	/**
	 * Retrieve Single Item
	 * @param ds
	 * @param type
	 * @param id
	 * @return
	 */
	public static OpResult getItem(DataSource ds, final ItemType type, String id) {
		
		OpResult res = null;
		ItemSchema sch = SchemaFactory.createSchema(type);
		if (sch == null) {
			logger.debug("Failed to create ItemSchema");
			res = new OpResult(OpStatus.OP_BadRequest, InterStatement.ResultTypeNotSupported);
		}
		if (!sch.genGetSql(id)) {
			res = new OpResult(OpStatus.OP_BadRequest, InterStatement.ResultQueryIDInvalid);
		}
		else {
			res = DataProvider.execQuery(ds, sch);
		}
		
		return res;
	}
	
	public static OpResult updateItem(DataSource ds, final ItemType type, 
			final String id, final String data) {
		
		OpResult res = null;
		ItemSchema sch = SchemaFactory.createSchema(type);
		if (sch == null) {
			logger.debug("Failed to create ItemSchema");
			res = new OpResult(OpStatus.OP_BadRequest, InterStatement.ResultTypeNotSupported);
		}
		if (!sch.genUpdateSql(data, id)) {
			res = new OpResult(OpStatus.OP_BadRequest, InterStatement.ResultQueryIDInvalid);
		}
		else {
			if (sch.getSqlSt() != null) 
			    res = DataProvider.execUpdate(ds, sch);
			else
				res = new OpResult(OpStatus.OP_BadRequest, InterStatement.ResultInvalidJson);
		}
		
		return res;

	}
	
	
	public static OpResult deleteItem(DataSource ds, final ItemType type, String id) {
        
		OpResult res = null;
        ItemSchema sch = SchemaFactory.createSchema(type);
        
        if (sch == null) {
			logger.debug("Failed to create ItemSchema");
			res = new OpResult(OpStatus.OP_BadRequest, InterStatement.ResultTypeNotSupported);
		}
		if (!sch.genDelSql(id)) {
			res = new OpResult(OpStatus.OP_BadRequest, InterStatement.ResultQueryIDInvalid);
		}
		else {
			res = DataProvider.execUpdate(ds, sch);
			if (res.getStatus() == OpStatus.OP_Successful)
			    res.setData(InterStatement.ResultDeleted);
		}
		
		return res;
	}
	
}
