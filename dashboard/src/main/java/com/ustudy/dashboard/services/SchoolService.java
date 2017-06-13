package com.ustudy.dashboard.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.ustudy.dashboard.model.Subject;
import com.ustudy.dashboard.model.Grade;
import com.ustudy.dashboard.model.School;

@Service
public class SchoolService {

	private static final Logger logger = LogManager.getLogger(SchoolService.class);
	
	@Autowired
	private JdbcTemplate jdbcT;
	
	public List<School> getList(int id) {
		List<School> schs = null;
		String sqlSch = "select * from school where id > ? limit 10000";
		try {
			schs = jdbcT.query(sqlSch, new RowMapper<School>() {
				
				@Override
				public School mapRow(ResultSet rs, int rowNum) throws SQLException {
					logger.debug("Firstly column -> " + rs.getString(1));
					School item = new School(rs.getString("id"), rs.getString("school_id"), 
						rs.getString("school_name"), rs.getString("school_type"),
						rs.getString("province"), rs.getString("city"), rs.getString("district"));
					return item;
				}
			}, id);
			for (School sch: schs) {
				logger.debug(sch.toString());
				
				List<String> gNames = null;
				String sqlGra = "select distinct grade_name from course where school_id = ?;";
				gNames = jdbcT.query(sqlGra, new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("grade_name");
						
					}
				}, sch.getSchoolId());
				
				List<Grade> grades = new ArrayList<Grade>();
				for (String gn : gNames) {
					List<Subject> cs = null;
					String sqlCs = "select distinct course_name from course where school_id = ? and grade_name = ?;";
					cs = jdbcT.query(sqlCs, new RowMapper<Subject>() {
						@Override
						public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
							Subject item = new Subject(rs.getString("course_name"));
							return item;
						}
					}, sch.getSchoolId(), gn);
					grades.add(new Grade(gn, cs));
				}
				sch.setGrades(grades);
			}
			
	    } catch (DataAccessException e) {
			logger.warn("SchoolService.getList() from id " + id + " failed with spring DAO exceptions");
			logger.warn(e.getMessage());
		} 
		return schs;
	}
}
