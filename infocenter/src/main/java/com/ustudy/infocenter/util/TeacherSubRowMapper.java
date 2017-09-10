package com.ustudy.infocenter.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.infocenter.model.TeacherSub;
import com.ustudy.infocenter.model.UElem;

public class TeacherSubRowMapper implements RowMapper<TeacherSub> {

	@Override
	public TeacherSub mapRow(ResultSet rs, int num) throws SQLException {
		List<UElem> subs = new ArrayList<UElem>();
		subs.add(new UElem(rs.getString("subName")));
		TeacherSub sub = new TeacherSub(rs.getString("teacherName"), rs.getString("teacherId"), subs);
		return sub;
	}

}
