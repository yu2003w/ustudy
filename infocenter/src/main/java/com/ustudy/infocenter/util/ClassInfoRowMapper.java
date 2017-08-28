package com.ustudy.infocenter.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ustudy.infocenter.model.ClassInfo;

public class ClassInfoRowMapper implements RowMapper<ClassInfo> {

	@Override
	public ClassInfo mapRow(ResultSet rs, int num) throws SQLException {
		ClassInfo cls = new ClassInfo(rs.getString("id"), rs.getString("cls_name"), rs.getString("cls_type"),
				rs.getString("teacid"), rs.getString("teacname"));
		return cls;
	}
}
