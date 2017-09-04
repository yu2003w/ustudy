package com.ustudy.dashboard.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.dashboard.model.OrgBrife;

public class OrgBrifeRowMapper implements RowMapper<OrgBrife> {

	private String type = null;
	
	@Override
	public OrgBrife mapRow(ResultSet rs, int num) throws SQLException {
		OrgBrife item = new OrgBrife(rs.getString("school_id"), rs.getString("school_name"), this.type);
		return item;
	}

	public OrgBrifeRowMapper(String t) {
		this.type = t;
	}
	
}
