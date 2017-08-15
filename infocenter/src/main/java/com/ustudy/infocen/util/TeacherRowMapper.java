package com.ustudy.infocen.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.infocen.model.Teacher;

public class TeacherRowMapper implements RowMapper<Teacher> {

	@Override
	public Teacher mapRow(ResultSet rs, int rowId) throws SQLException {
		Teacher tea = new Teacher(rs.getString("id"), rs.getString("teacid"), rs.getString("teacname"),
				rs.getString("passwd"), rs.getString("ctime"), rs.getString("lltime"));
		return tea;
	}
}
