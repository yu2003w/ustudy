package com.ustudy.dashboard.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.ustudy.dashboard.model.Account;

@Service
public class AccountService {

	private static final Logger logger = LogManager.getLogger(AccountService.class);
	
	@Autowired
	private JdbcTemplate jdbcT;
	
	public List<Account> query(String id) {
		List<Account> acList = null;
		String sql = "select * from users where id > ?;";
		try {
			acList = jdbcT.query(sql, new RowMapper<Account>(){	
				@Override
				public Account mapRow(ResultSet rs, int num) throws SQLException{
					Account usr = new Account(rs.getString("loginname"));
					return usr;
				}
		    }, id);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return acList;
	}
}
