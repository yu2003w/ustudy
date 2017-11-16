package com.ustudy.info.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.info.model.TeacherBrife;

public class TeacherBrifeRowMapper implements RowMapper<TeacherBrife> {

	@Override
	public TeacherBrife mapRow(ResultSet rs, int rowId) throws SQLException {
		TeacherBrife tea = new TeacherBrife(rs.getString("teacid"), rs.getString("teacname"));
		return tea;
	}
}
