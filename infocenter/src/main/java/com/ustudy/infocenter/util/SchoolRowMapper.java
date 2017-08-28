package com.ustudy.infocenter.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.infocenter.model.School;

public class SchoolRowMapper implements RowMapper<School> {

	@Override
	public School mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		School item = new School(rs.getString("id"), rs.getString("school_id"), 
			rs.getString("school_name"), rs.getString("school_type"),
			rs.getString("province"), rs.getString("city"), rs.getString("district"));
		return item;
	}
}
