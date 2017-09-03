package com.ustudy.infocenter.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.infocenter.model.SubjectTeac;

public class SubjectTeacRowMapper implements RowMapper<SubjectTeac> {

	@Override
	public SubjectTeac mapRow(ResultSet rs, int row) throws SQLException {
		String tid = rs.getString("sub_owner");
		String tn = null;
		if (tid != null && !tid.isEmpty()) {
			return new SubjectTeac(rs.getString("sub_name"), tid, tn);
		}
		else
			return new SubjectTeac(rs.getString("sub_name"), null);

	}
}
