package com.ustudy.info.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.info.model.OwnerBrife;

public class OwnerRowMapper implements RowMapper<OwnerBrife> {

	@Override
	public OwnerBrife mapRow(ResultSet rs, int num) throws SQLException {
		OwnerBrife item = new OwnerBrife(rs.getString("loginname"), rs.getString("name"), rs.getString("role"));
		return item;
	}
}
