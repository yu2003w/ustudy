package com.ustudy.info.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.info.model.SubjectTeac;

public class SubjectTeacRowMapper implements RowMapper<SubjectTeac> {

	@Override
	public SubjectTeac mapRow(ResultSet rs, int row) throws SQLException {
		String tid = rs.getString("sub_owner");
		String tn = null;
		if (tid != null && !tid.isEmpty()) {
			tn = rs.getString("teacname");
		}
		else {
			// to fit frontend logic, set teacher id/name as empty string
			tid = "";
			tn = "";
		}
		return new SubjectTeac(rs.getString("sub_name"), tid, tn);
	}
}
