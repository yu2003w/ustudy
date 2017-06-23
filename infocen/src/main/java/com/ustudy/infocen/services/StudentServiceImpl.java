package com.ustudy.infocen.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.cj.api.jdbc.Statement;
import com.ustudy.infocen.model.Student;
import com.ustudy.infocen.util.InfocenUtil;
import com.ustudy.infocen.util.StudentRowMapper;

@Service
public class StudentServiceImpl implements StudentService{

	private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcT;
	
	@Override
	public List<Student> getList(int id) {

		List<Student> stuL = null;
		String sqlList = "select * from infocenter.student where id > ? limit 10000";
		try {
			stuL = jdbcT.query(sqlList, new StudentRowMapper(), id);
			logger.debug("Fetched " + stuL.size() + " student items");
		} catch (DataAccessException dae) {
			logger.warn("getlist(), failed to retrive data from id " + id);
			logger.warn(dae.getMessage());
		}
		return stuL;
	}
	
	@Override
	@Transactional
	public int createItem(Student data) {
		
		// Noted: schema for table infocenter.student is as below,
		// id, name, grade, class, stuno, category, transient
		String sqlCr = "insert into infocenter.student values (?,?,?,?,?,?,?)";
		
		// keyholder is required for generating auto id
		KeyHolder keyH = new GeneratedKeyHolder();
		
		int id = -1;
		int num = jdbcT.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(sqlCr, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.INTEGER);
				psmt.setString(2, data.getStuName());
				psmt.setString(3, data.getStuGrade());
				psmt.setString(4, data.getStuClass());
				psmt.setString(5, data.getStuId());
				psmt.setString(6, data.getStuCate());
				psmt.setBoolean(7, data.isTransi());
				return psmt;
			}
		}, keyH);
		if (num != 1) {
			String msg = "createItem(), return value for student insert is " + num;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
	
		id = keyH.getKey().intValue();
		if (id < 0) {
			String msg = "createItem() failed with invalid id " + id;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
		
		return id;
	}
	
	@Override
	@Transactional
	public int deleteItem(int id) {
		String sqlDel = "delete from infocenter.student where id = ?";
		return jdbcT.update(sqlDel, id);
	}
	
	@Override
	@Transactional
	public int delItemSet(String ids) {
		List<String> idL = InfocenUtil.parseIds(ids);
		logger.debug(ids + idL.size());
		for (String s : idL)
			logger.debug(s);
		int len = idL.size();
		if (len == 0)
			return 0;
		
		String sqlDel = "delete from infocenter.student where ";
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				sqlDel += "id = '" + idL.get(0) + "'";
			}
			else
				sqlDel += " or id = '" + idL.get(i) + "'";
		}
		logger.debug("Assembled sql for batch deletion --> " + sqlDel);
		return jdbcT.update(sqlDel);

	}
	
	@Override
	public Student displayItem(int id) {
		String sqlDis = "select * from infocenter.student where id = ?";
		Student item = jdbcT.queryForObject(sqlDis, new StudentRowMapper(), id);
		return item;
	}
	
	@Override
	@Transactional
	public int updateItem(Student data) {
		String sqlUp = "update infocenter.student set name = ?, grade = ?,"
				+ " class = ?, stuno = ?, category = ?, transient = ? where id = ?";
		int num = jdbcT.update(sqlUp, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, data.getStuName());
				ps.setString(2, data.getStuGrade());
				ps.setString(3, data.getStuClass());
				ps.setString(4, data.getStuId());
				ps.setString(5, data.getStuCate());
				ps.setBoolean(6, data.isTransi());
				ps.setString(7, data.getId());
			}
		});
		
		logger.debug("updateItem(), " + num + " student updated.");
		return num;
	}
	
}
