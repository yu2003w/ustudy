package com.ustudy.infocenter.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.infocenter.model.Student;

public class StudentRowMapper implements RowMapper<Student> {

	@Override
	public Student mapRow(ResultSet rs, int rowId) throws SQLException {
		Student stu = new Student(rs.getString("id"), rs.getString("name"), 
			rs.getString("stuno"), rs.getString("grade"), rs.getString("class"), 
			rs.getString("category"), rs.getBoolean("transient"));
		return stu;
	}
	
}
