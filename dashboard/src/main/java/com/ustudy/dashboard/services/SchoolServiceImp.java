package com.ustudy.dashboard.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.dashboard.model.Subject;
import com.mysql.cj.api.jdbc.Statement;
import com.ustudy.dashboard.model.Grade;
import com.ustudy.dashboard.model.School;

@Service
public class SchoolServiceImp implements SchoolService {

	private static final Logger logger = LogManager.getLogger(SchoolServiceImp.class);
	
	@Autowired
	private JdbcTemplate jdbcT;
	
	@Override
	public List<School> getList(int id) {
		List<School> schs = null;
		String sqlSch = "select * from school where id > ? limit 10000";
		try {
			schs = jdbcT.query(sqlSch, new RowMapper<School>() {
				
				@Override
				public School mapRow(ResultSet rs, int rowNum) throws SQLException {
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
	
	@Transactional
	@Override
	public int createItem(School data) {
		
		// Noted: spring framework will handle runtime exceptions, so don't need to 
		// try {} catch{} here. Just throw out runtime exceptions.
		List<Grade> grades = data.getGrades();
		// Noted: Schema for table dashboard.school is as below,
		// id, school_id, school_name, school_type, province, city, district
		String sqlSch = "insert into dashboard.school values(?,?,?,?,?,?,?);";
		String sqlGr = "insert into dashboard.course values(?,?,?,?);";

		// insert record into dashoboard.school firstly, also auto generated keys is required.
		KeyHolder keyH = new GeneratedKeyHolder();
		int id = -1;    // auto generated id
		//int num = jdbcT.update(sqlSch, null, data.getSchoolId(), data.getSchoolName(), data.getSchoolType(),
		//		data.getProvince(), data.getCity(), data.getDistrict());
		int num = jdbcT.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(sqlSch, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.INTEGER);
				psmt.setString(2, data.getSchoolId());
				psmt.setString(3, data.getSchoolName());
				psmt.setString(4, data.getSchoolType());
				psmt.setString(5, data.getProvince());
				psmt.setString(6, data.getCity());
				psmt.setString(7, data.getDistrict());
				return psmt;
			}
		}, keyH);
		if (num != 1) {
			String msg = "SchoolServiceImpl.createItem(), return value for school insert is " + num;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
	
		id = keyH.getKey().intValue();
		if (id < 0) {
			String msg = "SchoolService.createItem() failed with invalid id " + id;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}

		for (Grade gr : grades) {
			List<Subject> subs = gr.getSubjects();
			for (Subject sub : subs) {
				num = jdbcT.update(sqlGr, null, gr.getGradeName(), sub.getCourseName(), data.getSchoolId());
				if (num != 1) {
					String msg = "SchoolServiceImpl.createItem(), return value for course insert is " + num;
					logger.warn(msg);
					throw new RuntimeException(msg);
				}
				logger.debug("number of affected rows is " + num);
			}
		}

		return id;
	}
}
