package com.ustudy.datawrapper;
/**
 * Initiated by Jared on May 15, 2017.
 *
 * This class includes SQL related statements.
 * 
 */

public class InterStatement {

	public enum ItemType {
		STUDENT ("student"),
		TEACHER ("teacher"),
		SCHOOL ("school"),
		EXAM ("exam"),
		UNSUPPORTED ("unsupported");
		
		private String val;
		private ItemType(String type) {
			if (type.compareTo("student") == 0 || type.compareTo("teacher") == 0 ||
					type.compareTo("school") == 0 || type.compareTo("exam") == 0) {
				this.val = type;
			}
			else
				this.val = "unsupported";
		}	
		public String getVal() {
			return this.val;
		}
	}
	
	public static final String REST_PREFIX = "services/info/";
	public static final String INFO_CENTER_DS = "mysql/infocenter";
	public static final String STU_LIST = "select * from student where id > ";
	
	//SQL statement prefix
	public static final String STU_INSERT_PREFIX = "insert into student values (null,'";
	public static final String STU_GET_PREFIX = "select * from student where id = ";
	public static final String STU_DELETE_PREFIX = "delete from student where id = ";
	public static final String STU_UPDATE_PREFIX = "update student set ";
	
	public static final String ID = "id";
	public static final String STU_LIST_HEADER = "students";

	// lables and columns for table infocenter.student
	public static final String [][] STU_TABLE = {
		{"id", "name", "grade", "class", "stuno", "category", "transient"},
		{"id", "studentName", "grade", "class", "studentId", "type", "isTemp"},
	};
	
	public static final String ResultEmpty = "{\"Result\":\"Empty\"}";
	public static final String ResultTypeNotSupported = "{\"Result\":\"Type not supported\"}";
	public static final String ResultDuplicated = "{\"Result\":\"Duplicated\"}";
	public static final String ResultDeleted = "{\"Result\":\"Item Deleted\"}";
	public static final String ResultUpdated = "{\"Result\":\"Updated\"}";
	public static final String ResultDBError = "{\"Result\":\"Internal DB error\"}";
	public static final String ResultNoContent = "{\"Result\":\"No content\"}";
	public static final String ResultInvalidJson = "{\"Result\":\"Invalid Json Data\"}";
	public static final String ResultInterErr = "{\"Result\":\"Internal Error\"}";
	public static final String ResultItemCreated = "{\"Result\":\"Item Created\"}";
	public static final String ResultQueryIDInvalid = 
			"{\"Result\":\"Start ID for query is invalid\"}";
	
	public static final int STU_LIMIT = 10000;
}
