package com.ustudy.dashboard.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.dashboard.model.OrgOwner;

public class OrgOwnerRowMapper implements RowMapper<OrgOwner> {

	// assemble object from database
	@Override
	public OrgOwner mapRow(ResultSet rs, int num) throws SQLException {
		OrgOwner item = new OrgOwner(rs.getString("id"), rs.getString("name"), rs.getString("loginname"),
				rs.getString("passwd"), rs.getString("orgtype"), rs.getString("orgid"), rs.getString("role"),
				rs.getString("ctime"));
		return item;
	}
}
