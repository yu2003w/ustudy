package com.ustudy.dashboard.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.ustudy.dashboard.model.Grade;
import com.ustudy.dashboard.model.School;

@Service
public class SchoolService {

	private static final Logger logger = LogManager.getLogger(SchoolService.class);
	
	@Autowired
	private JdbcTemplate jdbcT;
	
	public List<School> getList(int id) {
		List<School> res = null;
		String sqlSt = "select * from school where id > ? limit 10000";
		try {
			res = jdbcT.query(sqlSt, new RowMapper<School>() {
				
				@Override
				public School mapRow(ResultSet rs, int rowNum) throws SQLException {
					Grade[] grades = null;
					School item = new School(rs.getString(0), rs.getString(1),
						rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
						rs.getString(6), grades);
					return item;
				}
			});
	    } catch (DataAccessException e) {
			logger.warn("SchoolService.getList() from id " + id + " failed with spring DAO exceptions");
			logger.warn(e.getMessage());
		} 
		return res;
	}
}
