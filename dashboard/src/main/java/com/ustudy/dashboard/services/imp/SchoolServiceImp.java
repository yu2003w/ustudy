package com.ustudy.dashboard.services.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
import com.ustudy.dashboard.services.SchoolService;
import com.ustudy.dashboard.util.DashboardUtil;
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
		String sqlSch = "select * from ustudy.school where id > ? limit 10000";
		try {
			if (id < 0)
				id = 0;
			schs = jdbcT.query(sqlSch, new SchoolRowMapper(), id);
			logger.debug("Fetched " + schs.size() + " items of school");
			
			for (School sch: schs) {
				// Noted: for getList() service, front end doesn't need grades related information
				// to display, so here, no need to assemble grades information. To do so, whole 
				// processing could be speedup.
				// assembleGrades(sch);
				logger.debug(sch.toString());
			}
			
	    } catch (DataAccessException e) {
			logger.warn("SchoolService.getList() retrieve data from id " + id + " failed.");
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
			
		// Noted: Schema for table ustudy.school is as below,
		// id, school_id, school_name, school_type, province, city, district
		String sqlSch = "insert into ustudy.school values(?,?,?,?,?,?,?);";

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
		String updateSch = "update ustudy.school set ";
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
		String sqlDelGr = "delete from ustudy.grade where school_id = ?";
		int numOfGr = jdbcT.update(sqlDelGr, data.getSchoolId());
		logger.info(numOfGr + " grade items deleted for update.");
		
		numOfGr = saveGrades(data.getGrades(), data.getSchoolId());
		logger.info(numOfGr + " grade items saved into database");
		
		return num;
	}
	
	@Transactional
	@Override
	public int deleteItem(int id) {
		
		String sqlDel = "delete from ustudy.school where id = ?";
		return jdbcT.update(sqlDel, id);
	}
	
	@Transactional
	@Override
	public int delItemSet(String ids) {
	
		List<String> idsList = DashboardUtil.parseIds(ids);
		int len = idsList.size();
		if (len == 0)
			return 0;
		
		String sqlDel = "delete from ustudy.school where ";
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				sqlDel += "id = '" + idsList.get(0) + "'";
			}
			else
				sqlDel += " or id = '" + idsList.get(i) + "'";
		}
		logger.debug("Assembled sql for batch deletion --> " + sqlDel);
		return jdbcT.update(sqlDel);	
	}
	
	@Override
	public School displayItem(int id) {
		String sqlDis = "select * from ustudy.school where id = ?";
		
		School item = jdbcT.queryForObject(sqlDis, new SchoolRowMapper(), id);
		// need to query and assemble grade/course information
		if (item != null)
		    assembleGrades(item);
		
		return item;
	}
	
	private void assembleGrades(School sch) {
		List<Grade> gNames = null;
		String sqlGra = "select id, grade_name, classes_num from ustudy.grade where school_id = ?;";
		gNames = jdbcT.query(sqlGra, new GradeRowMapper(), sch.getSchoolId());
		
		for (Grade gn : gNames) {
			List<Subject> cs = null;
			String sqlCs = "select course_name from ustudy.course where grade_id = ?;";
			cs = jdbcT.query(sqlCs, new SubjectRowMapper(), gn.getId());
			gn.setSubjects(cs);
			// logger.debug(gn.toString());
		}
		sch.setGrades(gNames);
	}
	
	
	// Store grades information into database
	private int saveGrades(List<Grade> grades, final String schId) {
		int num = 0;
		String msg = null;
		// grade schema is as below,
		// id, grade_name, classes_num, school_id
		String sqlGr = "insert into ustudy.grade values(?,?,?,?)";
		String sqlCla = "insert into ustudy.course values(?,?,?);";
		for (Grade gr : grades) {
			List<Subject> subs = gr.getSubjects();
			// insert grade related information into ustudy.grade firstly
			// need to retrieve auto generated key
			int id = -1;
			KeyHolder keyH = new GeneratedKeyHolder();
			num = jdbcT.update(new PreparedStatementCreator(){
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement psmt = conn.prepareStatement(sqlGr, Statement.RETURN_GENERATED_KEYS);
					psmt.setNull(1, java.sql.Types.NULL);
					psmt.setString(2, gr.getGradeName());
					psmt.setInt(3, gr.getNum());
					psmt.setString(4, schId);
					return psmt;
				}
			}, keyH);
			if (num != 1) {
				msg = "saveGrades(), return value from grade insert is " + num;
				logger.warn(msg);
				throw new RuntimeException(msg);
			}
			id = keyH.getKey().intValue();
			if (id < 0) {
				msg = "saveGrades(), auto generated id is " + id;
				logger.warn(msg);
				throw new RuntimeException(msg);
			}
			for (Subject sub : subs) {
				// course schema is as below,
				// id, grade_id, course_name
				num = jdbcT.update(sqlCla, null, id, sub.getCourseName());
				if (num != 1) {
					msg = "saveGrades(), return value for course insert is " + num;
					logger.warn(msg);
					throw new RuntimeException(msg);
				}
			}
			logger.debug("Num of courses for " + schId + "," + gr.getGradeName() + " is " + subs.size());
			logger.debug(schId + " of " + gr.getGradeName() + " has " + gr.getNum() + " classes");
		}
		return grades.size();
	}
	
}
