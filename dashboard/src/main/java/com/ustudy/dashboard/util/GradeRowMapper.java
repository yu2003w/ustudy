package com.ustudy.dashboard.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.dashboard.model.Grade;

public class GradeRowMapper implements RowMapper<Grade> {

	@Override
	public Grade mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Grade(rs.getString("id"),
				rs.getString("grade_name"),
				rs.getInt("classes_num"));
	}
}
