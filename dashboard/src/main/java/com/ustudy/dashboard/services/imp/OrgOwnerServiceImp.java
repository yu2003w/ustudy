package com.ustudy.dashboard.services.imp;

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
import com.ustudy.dashboard.model.OrgOwner;
import com.ustudy.dashboard.services.OrgOwnerService;
import com.ustudy.dashboard.util.DashboardUtil;
import com.ustudy.dashboard.util.OrgOwnerRowMapper;

@Service
public class OrgOwnerServiceImp implements OrgOwnerService {

	private static final Logger logger = LogManager.getLogger(OrgOwnerServiceImp.class);
	
	@Autowired
	private JdbcTemplate jdbcT;
	
	@Override
	public List<OrgOwner> getList(int id) {
		List<OrgOwner> ooL = null;
		String sqlOrg = "select * from ustudy.orgowner where id > ? limit 10000";
		try {
			if (id < 0)
				id = 0;
			ooL = jdbcT.query(sqlOrg, new OrgOwnerRowMapper(), id);
			logger.debug("Fetched " + ooL.size() + " items of user");

			for (OrgOwner oo : ooL) {
				// Noted: for getList() service, front end doesn't need additional
				// permissions related information
				logger.debug(oo.toString());
			}

		} catch (DataAccessException e) {
			logger.warn("getList(), retrieve data from id " + id + " failed.");
			logger.warn(e.getMessage());
		}
		return ooL;
	}

	@Override
	@Transactional
	public int createItem(OrgOwner item) {
		// Noted: Schema for table ustudy.orgowner is as below,
		// id, name, loginname, passwd, orgtype, orgid, role, ctime
		String sqlOwner = "insert into ustudy.orgowner values(?,?,?,?,?,?,?);";

		// insert record into dashoboard.school firstly, also auto generated keys is required.
		KeyHolder keyH = new GeneratedKeyHolder();
		int id = -1;    // auto generated id
	
		// need to retrieve auto id of school item which is returned back in header location
		int num = jdbcT.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(sqlOwner, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.INTEGER);
				psmt.setString(2, item.getName());
				psmt.setString(3, item.getLoginname());
				psmt.setString(4, item.getPasswd());
				
				//TODO: need to check whether organization id is valid for corresponding type
				psmt.setString(5, item.getOrgType());
				psmt.setString(6, item.getOrgId());
				
				// account creation time should be set to current time
				psmt.setString(7, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				
				return psmt;
			}
		}, keyH);
		
		String msg = null;
		if (num != 1) {
			msg = "createItem(), return value for OrgOwner insert is " + num;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
	
		id = keyH.getKey().intValue();
		if (id < 0) {
			msg = "createItem() failed with invalid id " + id;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}

		num =  populateTeachers(item);
		if (num < 0) { 
			msg = "createItem(), failed to populate data for teacher. Return value is " + num;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
		else
			logger.debug("createItem(), populated teacher data.");
		return id;
	}

	@Transactional
	@Override
	public int updateItem(OrgOwner item, int id) {
		String updateOrg = "update ustudy.orgowner set ";
		OrgOwner origin = displayItem(id);
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
			logger.debug("Update SQL for orgowner item " + id + " -->" + updateOrg);
		}
		updateOrg += " where id = ?";
		int num = jdbcT.update(updateOrg, id);
		return num;
	}

	@Override
	public int delItemSet(String ids) {
		List<String> idsList = DashboardUtil.parseIds(ids);
		int len = idsList.size();
		if (len == 0)
			return 0;
		
		String sqlDel = "delete from ustudy.orgowner where ";
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				sqlDel += "id = '" + idsList.get(0) + "'";
			}
			else
				sqlDel += " or id = '" + idsList.get(i) + "'";
		}
		logger.debug("delItemSet(): assembled sql for batch deletion --> " + sqlDel);
		return jdbcT.update(sqlDel);
	}

	@Override
	public int deleteItem(int id) {
		String sqlDel = "delete from ustudy.orgowner where id = ?";
		return jdbcT.update(sqlDel, id);
	}
	
	@Override
	public OrgOwner displayItem(int id) {
		
		String sqlDis = "select * from ustudy.orgowner where id = ?";
		OrgOwner item = jdbcT.queryForObject(sqlDis, new OrgOwnerRowMapper(), id);
				
		return item;
	}

	@Transactional
	private int populateTeachers(OrgOwner item) {
		// Noted: Schema for table ustudy.teacher is as below,
		// id, teacid, teacname, passwd, orgtype, orgid, ctime, lltime
		String sqlOwner = "insert into ustudy.teacher values(?,?,?,?,?,?,?,?);";

		// insert record into ustudy.teacher firstly, also auto generated keys is required.
		KeyHolder keyH = new GeneratedKeyHolder();
		int id = -1;    // auto generated id
	
		// need to retrieve auto id of teacher item which is returned back in header location
		int num = jdbcT.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(sqlOwner, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.INTEGER);
				psmt.setString(2, item.getLoginname());
				psmt.setString(3, item.getName());
				psmt.setString(4, item.getPasswd());
				
				psmt.setString(5, item.getOrgType());
				psmt.setString(6, item.getOrgId());
				
				// teacher creation time should be set to current time
				psmt.setString(7, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				psmt.setNull(8, java.sql.Types.DATE);
				
				return psmt;
			}
		}, keyH);
		
		String msg = null;
		if (num != 1) {
			msg = "populateTeachers(), return value for populate teacher is " + num;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
	
		id = keyH.getKey().intValue();
		if (id < 0) {
			msg = "populateTeachers() failed with invalid id " + id;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
		
		// add default role_name "org_owner"
		String sqlRoles = "insert into ustudy.teacherroles values(?,?,?)";
		msg = null;
		num = jdbcT.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(sqlRoles, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.NULL);
				psmt.setString(2, "org_owner");
				psmt.setString(3, item.getLoginname());
				return psmt;
			}
		});
		if (num != 1) {
			msg = "populateTeachers(), return value from default role insert is " + num;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
			
		logger.debug("populateTeachers(), default role populated for " + item.getLoginname());

		return id;
	}
	
}
