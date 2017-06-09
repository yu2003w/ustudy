package com.ustudy.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.ustudy.admin.model.Account;

@Service
public class AccountService {

	private static final Logger logger = LogManager.getLogger(AccountService.class);
	
	@Autowired
	private JdbcTemplate jdbcT;
	
	public void invoke() {
	
	String sql = "select * from sec_users;";
	
	try {
	jdbcT.query(sql, new RowMapper<Account>(){
		@Override
		public Account mapRow(ResultSet rs, int num) throws SQLException{
			Account usr = new Account("jared");
			return usr;
		}
	});
	} catch (Exception e) {
		logger.debug(e.getMessage());
	}
	}
}
