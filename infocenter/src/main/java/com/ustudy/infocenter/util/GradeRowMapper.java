package com.ustudy.infocenter.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.infocenter.model.Grade;
import com.ustudy.infocenter.model.TeacherBrife;

public class GradeRowMapper implements RowMapper<Grade> {

	@Override
	public Grade mapRow(ResultSet rs, int num) throws SQLException {
		Grade item = new Grade(rs.getString("id"), rs.getString("grade_name"), 
				new TeacherBrife(rs.getString("grade_owner"), rs.getString("teacname")),
				rs.getString("classes_num"));
		return item;
	}
}
