package com.ustudy.info.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.info.model.Grade;
import com.ustudy.info.model.TeacherBrife;

public class GradeRowMapper implements RowMapper<Grade> {

	@Override
	public Grade mapRow(ResultSet rs, int num) throws SQLException {
		String tid = rs.getString("grade_owner");
		TeacherBrife tea = null;
		if (tid != null && !tid.isEmpty()) {
			tea = new TeacherBrife(tid, rs.getString("teacname"));
		}
		else {
			tea = new TeacherBrife("", "");
		}
		Grade item = new Grade(rs.getString("id"), 
				rs.getString("grade_name"), tea, rs.getString("classes_num"));
		return item;
	}
}
