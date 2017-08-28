package com.ustudy.infocenter.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.infocenter.model.SubjectTeac;

public class SubjectTeacRowMapper implements RowMapper<SubjectTeac> {

	@Override
	public SubjectTeac mapRow(ResultSet rs, int row) throws SQLException {
		SubjectTeac item = new SubjectTeac(rs.getString("sub_name"), 
				rs.getString("sub_owner"), rs.getString("teacname"));
		return item;
	}
}
