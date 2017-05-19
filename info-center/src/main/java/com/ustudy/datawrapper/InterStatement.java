package com.ustudy.datawrapper;
/**
 * Initiated by Jared on May 15, 2017.
 *
 * This class includes SQL related statements.
 * 
 */

public class InterStatement {

	public static final String INFO_CENTER_DS = "mysql/infocenter";
	
	public static final String STU_TYPE = "student";
	public static final String TEACH_TYPE = "teacher";
	public static final String SCHOOL_TYPE = "school";
	public static final String EXAM_TYPE = "exam";
	
	public static final String STU_LIST = "select id, name, grade, class from student";
	
	//SQL statement prefix
	public static final String STU_INSERT_PREFIX = "insert into student values (null,'";
	public static final String STU_GET_PREFIX = "select * from student where id = ";
	
	public static final String STU_ID = "id";
	
	// labels for fields in table student
	public static final String STU_NAME = "name";
	public static final String STU_GRADE = "grade";
	public static final String STU_CLASS = "class";
	public static final String STU_NO = "stuno";
	public static final String STU_CATEGORY = "category";
	public static final String STU_TRANS = "transient";
	
}
