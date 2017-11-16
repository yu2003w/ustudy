package com.ustudy.info.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.info.model.UElem;

public class UElemRowMapper implements RowMapper<UElem> {

	@Override
	public UElem mapRow(ResultSet rs, int rowId) throws SQLException {
		UElem e = new UElem(rs.getString("value"));
		return e;
	}
}
