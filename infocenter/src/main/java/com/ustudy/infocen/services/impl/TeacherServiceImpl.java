package com.ustudy.infocen.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.mysql.cj.api.jdbc.Statement;
import com.ustudy.infocen.model.Teacher;
import com.ustudy.infocen.services.TeacherService;
import com.ustudy.infocen.util.InfocenUtil;
import com.ustudy.infocen.util.TeacherRowMapper;

@Service
public class TeacherServiceImpl implements TeacherService {

	private static final Logger logger = LogManager.getLogger(TeacherServiceImpl.class);
	
	@Autowired
	private JdbcTemplate jTea;
	
	@Override
	public List<Teacher> getList(int id) {
		List<Teacher> teaL = null;
		String sqlOrg = "select * from ustudy.teacher where id > ? limit 10000";
		try {
			if (id < 0)
				id = 0;
			teaL = jTea.query(sqlOrg, new TeacherRowMapper(), id);
			logger.debug("Fetched " + teaL.size() + " items of user");

			for (Teacher tea : teaL) {
				// Noted: for getList() service, front end doesn't need additional
				// permissions related information
				logger.debug(tea.toString());
			}

		} catch (DataAccessException e) {
			logger.warn("getList(), retrieve data from id " + id + " failed.");
			logger.warn(e.getMessage());
		}
		return teaL;
	}

	@Override
	public Teacher displayItem(int id) {
		String sqlDis = "select * from ustudy.teacher where id = ?";
		Teacher item = jTea.queryForObject(sqlDis, new TeacherRowMapper(), id);
				
		return item;
	}

	@Override
	@Transactional
	public int createItem(Teacher item) {
		// Noted: Schema for table ustudy.teacher is as below,
		// id, teacid, teacname, passwd, ctime, lltime
		String sqlOwner = "insert into ustudy.teacher values(?,?,?,?,?,?);";

		// insert record into ustudy.teacher firstly, also auto generated keys is required.
		KeyHolder keyH = new GeneratedKeyHolder();
		int id = -1;    // auto generated id
	
		// need to retrieve auto id of teacher item which is returned back in header location
		int num = jTea.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(sqlOwner, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.INTEGER);
				psmt.setString(2, item.getTeacId());
				psmt.setString(3, item.getTeacName());
				psmt.setString(4, item.getPasswd());
				
				// teacher creation time should be set to current time
				psmt.setString(5, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				psmt.setNull(6, java.sql.Types.DATE);
				
				return psmt;
			}
		}, keyH);
		if (num != 1) {
			String msg = "createItem(), return value for teacher insert is " + num;
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
	public int updateItem(Teacher item, int id) {
		String updateOrg = "update ustudy.teacher set ";
		Teacher origin = displayItem(id);
		Map<String, String> orgDiff = item.compare(origin);
		if (orgDiff.size() == 0) {
			logger.info("No changes in orgowner and no need to update");
			return 0;
		}
		else {
			// some stuff in school changed, need to populate changed fields
			Set<Map.Entry<String,String>> fields = orgDiff.entrySet();
			boolean first = true;
			for (Map.Entry<String, String> elem : fields) {
				if (first) {
					updateOrg += elem.getKey() + " = '" + elem.getValue() + "'";
					first = false;
				}
				else
					updateOrg += ", " + elem.getKey() + " = '" + elem.getValue() + "'";
			}
			logger.debug("Update SQL for teacher item " + id + " -->" + updateOrg);
		}
		updateOrg += " where id = ?";
		int num = jTea.update(updateOrg, id);
		return num;
	}

	@Override
	public int delItemSet(String ids) {
		List<String> idsList = InfocenUtil.parseIds(ids);
		int len = idsList.size();
		if (len == 0)
			return 0;
		
		String sqlDel = "delete from ustudy.teacher where ";
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				sqlDel += "id = '" + idsList.get(0) + "'";
			}
			else
				sqlDel += " or id = '" + idsList.get(i) + "'";
		}
		logger.debug("delItemSet(): assembled sql for batch deletion --> " + sqlDel);
		return jTea.update(sqlDel);
	}

	@Override
	public int deleteItem(int id) {
		String sqlDel = "delete from ustudy.teacher where id = ?";
		return jTea.update(sqlDel, id);
	}

}
