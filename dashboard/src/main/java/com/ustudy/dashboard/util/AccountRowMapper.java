package com.ustudy.dashboard.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.dashboard.model.Account;

public class AccountRowMapper implements RowMapper<Account> {

	@Override
	public Account mapRow(ResultSet rs, int num) throws SQLException {
		Account item = new Account(rs.getString("id"), rs.getString("loginname"),
			rs.getString("fullname"), rs.getString("phone"), rs.getString("passwd"),
			rs.getString("ugroup"), rs.getString("ctime"), rs.getString("ll_time"),
			rs.getString("status"));
		return item;
	}
}
