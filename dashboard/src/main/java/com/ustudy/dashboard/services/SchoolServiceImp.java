package com.ustudy.dashboard.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.dashboard.model.Subject;
import com.ustudy.dashboard.util.GradeRowMapper;
import com.ustudy.dashboard.util.SchoolRowMapper;
import com.ustudy.dashboard.util.SubjectRowMapper;
import com.mysql.cj.api.jdbc.Statement;
import com.ustudy.dashboard.model.Grade;
import com.ustudy.dashboard.model.School;

/**
 * @author jared
 *
 */

@Service
public class SchoolServiceImp implements SchoolService {

	private static final Logger logger = LogManager.getLogger(SchoolServiceImp.class);
	
	@Autowired
	private JdbcTemplate jdbcT;
	
	@Override
	public List<School> getList(int id) {
		List<School> schs = null;
		String sqlSch = "select * from dashboard.school where id > ? limit 10000";
		try {
			if (id < 0)
				id = 0;
			schs = jdbcT.query(sqlSch, new SchoolRowMapper(), id);
			logger.debug("Fetched " + schs.size() + " items of school");
			for (School sch: schs) {
				assembleGrades(sch);
				logger.debug(sch.toString());
			}
			
	    } catch (DataAccessException e) {
			logger.warn("SchoolService.getList() from id " + id + " failed with spring DAO exceptions");
			logger.warn(e.getMessage());
		} 
		return schs;
	}
	
	
	/**
	 *  Noted: spring framework will handle runtime exceptions, so don't need to write
	 *  try {} catch{} here. Just throw out runtime exceptions.
	 */
	@Transactional
	@Override
	public int createItem(School data) {
			
		// Noted: Schema for table dashboard.school is as below,
		// id, school_id, school_name, school_type, province, city, district
		String sqlSch = "insert into dashboard.school values(?,?,?,?,?,?,?);";

		// insert record into dashoboard.school firstly, also auto generated keys is required.
		KeyHolder keyH = new GeneratedKeyHolder();
		int id = -1;    // auto generated id
	
		// need to retrieve auto id of school item which is returned back in header location
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

		int numOfGr = saveGrades(data.getGrades(), data.getSchoolId());
		logger.info(numOfGr + " grade items saved into database");

		return id;
	}
	
	@Transactional
	@Override
	public int updateItem(School data, int id) {
		String updateSch = "update dashboard.school set ";
		School origin = displayItem(id);
		Map<String, String> schDiff = data.compare(origin);
		if (schDiff.size() == 0) {
			logger.info("No changes in school and no need to update");
		}
		else {
			// some stuff in school changed, need to populate changed fields
			Set<Map.Entry<String,String>> fields = schDiff.entrySet();
			boolean first = true;
			for (Map.Entry<String, String> elem : fields) {
				if (first) {
					updateSch += elem.getKey() + " = '" + elem.getValue() + "'";
					first = false;
				}
				else
					updateSch += ", " + elem.getKey() + " = '" + elem.getValue() + "'";
			}
			logger.debug("Update SQL for school item " + id + " -->" + updateSch);
		}
		updateSch += " where id = ?";
		int num = jdbcT.update(updateSch, id);
		
		// for grades related information, need to replace previous information
		// delete origin grades information firstly, then insert new values
		String sqlDelGr = "delete from course where school_id = ?";
		int numOfGr = jdbcT.update(sqlDelGr, data.getSchoolId());
		logger.info(numOfGr + " grade items deleted for update.");
		
		numOfGr = saveGrades(data.getGrades(), data.getSchoolId());
		logger.info(numOfGr + " grade items saved into database");
		
		return num;
	}
	
	@Transactional
	@Override
	public int deleteItem(int id) {
		
		String sqlDel = "delete from dashboard.school where id = ?";
		return jdbcT.update(sqlDel, id);
	}
	
	@Override
	public School displayItem(int id) {
		String sqlDis = "select * from dashboard.school where id = ?";
		School item = jdbcT.queryForObject(sqlDis, new SchoolRowMapper(), id);
		// need to query and assemble grade/course information
		assembleGrades(item);
		return item;
	}
	
	private void assembleGrades(School sch) {
		List<String> gNames = null;
		String sqlGra = "select distinct grade_name from course where school_id = ?;";
		gNames = jdbcT.query(sqlGra, new GradeRowMapper(), sch.getSchoolId());
		
		List<Grade> grades = new ArrayList<Grade>();
		for (String gn : gNames) {
			List<Subject> cs = null;
			String sqlCs = "select distinct course_name from course where school_id = ? and grade_name = ?;";
			cs = jdbcT.query(sqlCs, new SubjectRowMapper(), sch.getSchoolId(), gn);
			grades.add(new Grade(gn, cs));
		}
		sch.setGrades(grades);
	}
	
	
	// Store grades information into database
	private int saveGrades(List<Grade> grades, final String schId) {
		int num = 0;
		String sqlGr = "insert into dashboard.course values(?,?,?,?);";
		for (Grade gr : grades) {
			List<Subject> subs = gr.getSubjects();
			for (Subject sub : subs) {
				num = jdbcT.update(sqlGr, null, gr.getGradeName(), sub.getCourseName(), schId);
				if (num != 1) {
					String msg = "SchoolServiceImpl.createItem(), return value for course insert is " + num;
					logger.warn(msg);
					throw new RuntimeException(msg);
				}
				logger.debug(schId + "," + gr.getGradeName() + "," + sub.getCourseName() + "inserted.");
			}
			logger.debug("Num of courses for " + schId + "," + gr.getGradeName() + " is " + subs.size());
		}
		return grades.size();
	}
	
}
