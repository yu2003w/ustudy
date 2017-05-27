package com.ustudy.datawrapper;

import com.ustudy.datawrapper.InterStatement.ItemType;

public class SchemaFactory {
	
	public static ItemSchema createSchema(ItemType type) {

		ItemSchema sch = null;
		switch (type) {
		case STUDENT:
			sch = new StuSchema();
			break;
		default:
			break;
		}
		
		return sch;
	}

}
