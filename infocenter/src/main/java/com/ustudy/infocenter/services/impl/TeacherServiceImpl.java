package com.ustudy.infocenter.services.impl;

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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.cj.api.jdbc.Statement;
import com.ustudy.infocenter.model.Teacher;
import com.ustudy.infocenter.model.UElem;
import com.ustudy.infocenter.services.TeacherService;
import com.ustudy.infocenter.util.InfoUtil;
import com.ustudy.infocenter.util.TeacherRowMapper;
import com.ustudy.infocenter.util.UElemRowMapper;

/**
 * @author jared
 *
 * Service module for teacher related db operations
 */

@Service
public class TeacherServiceImpl implements TeacherService {

	private static final Logger logger = LogManager.getLogger(TeacherServiceImpl.class);
	
	@Autowired
	private JdbcTemplate jTea;
	
	@Override
	public List<Teacher> getList(int id) {
		List<Teacher> teaL = null;
		
		String sqlOrg = "select * from ustudy.teacher where id > ? and orgid = ? and orgtype = ? limit 10000";
		try {
			if (id < 0)
				id = 0;
			if (getOrgId() == null || getOrgId().isEmpty() ||
					getOrgType() == null || getOrgType().isEmpty()) {
				logger.warn("getList(), it seemed that user not logged in");
				return teaL;
			}
			else
			    teaL = jTea.query(sqlOrg, new TeacherRowMapper(), id, getOrgId(), getOrgType());
			logger.debug("Fetched " + teaL.size() + " items of user");

			for (Teacher tea : teaL) {
				// Noted: for getList() service, front end doesn't need additional
				// permissions related information
				retrieveProp(tea);
				logger.debug(tea.toString());
			}

		} catch (DataAccessException e) {
			logger.warn("getList(), retrieve data from id " + id + " failed.");
			logger.warn(e.getMessage());
		}
		return teaL;
	}

	@Override
	@Transactional
	public Teacher displayItem(int id) {
		String sqlD = "select * from ustudy.teacher where id = ?";
		Teacher item = jTea.queryForObject(sqlD, new TeacherRowMapper(), id);
		
		retrieveProp(item);
		
		return item;
	}
	
	private void retrieveProp(Teacher item) {
		// retrieve subjects
		String sqlD = "select * from ustudy.teachersub where teac_id = ?";
		List<UElem> subs = jTea.query(sqlD, new UElemRowMapper(), item.getTeacId());
		item.setSubjects(subs);
				
		// retrieve classes
		sqlD = "select * from ustudy.teacherclass where teac_id = ?";
		List<UElem> cls = jTea.query(sqlD, new UElemRowMapper(), item.getTeacId());
		item.setClasses(cls);
				
		// retrieve grades
		sqlD = "select * from ustudy.teachergrade where teac_id = ?";
		List<UElem> gs = jTea.query(sqlD, new UElemRowMapper(), item.getTeacId());
		item.setGrades(gs);
		
		// retrieve roles and excluter addi_{teac_id}
		String aRole = "addi_" + item.getTeacId();
		sqlD = "select * from ustudy.teacherroles where teac_id = ?";
		List<UElem> rs = jTea.query(sqlD, new UElemRowMapper(), item.getTeacId());
		rs.remove(aRole);
		item.setRoles(rs);
		
		// retrieve additional permissions
		sqlD = "select * from ustudy.perms where role_name = ?";
		List<UElem> ps = jTea.query(sqlD, new UElemRowMapper(), item.getTeacId());
		rs.remove(aRole);
		item.setAddiPerms(ps);
		
	}
	
	@Override
	public Teacher findTeacherById(String teaid) {
		String sqlT = "select * from ustudy.teacher where teacid = ?";
		Teacher item = jTea.queryForObject(sqlT, new TeacherRowMapper(), teaid);
		return item;
	}

	@Override
	@Transactional
	public int createItem(Teacher item) {
		// Noted: Schema for table ustudy.teacher is as below,
		// id, teacid, teacname, passwd, ctime, lltime
		String sqlOwner = "insert into ustudy.teacher values(?,?,?,?,?,?,?,?);";

		// insert record into ustudy.teacher firstly, also auto generated keys is required.
		KeyHolder keyH = new GeneratedKeyHolder();
		int id = -1;    // auto generated id
	
		if (getOrgId() == null || getOrgId().isEmpty() ||
				getOrgType() == null || getOrgType().isEmpty()) {
			throw new RuntimeException("createItem(), it seemed user not logged in");
		}
		
		// need to retrieve auto id of teacher item which is returned back in header location
		int num = jTea.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(sqlOwner, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.INTEGER);
				psmt.setString(2, item.getTeacId());
				psmt.setString(3, item.getTeacName());
				psmt.setString(4, item.getPasswd());
				
				// Noted: need to populate current user's orgtype, orgid
				psmt.setString(5, getOrgType());
				psmt.setString(6, getOrgId());
				
				// teacher creation time should be set to current time
				psmt.setString(7, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				psmt.setNull(8, java.sql.Types.DATE);
				
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
		
		// save roles
		num = saveRoles(item.getRoles(), item.getTeacId());
		logger.debug("createItem(), " + num + " roles is saved for " + item.getTeacId());
		
		// save additional permissions
		num = 0;
		if (item.getAddiPerms() != null)
			num = saveAddiPerms(item.getAddiPerms(), item.getTeacId());
		logger.debug("createItem()," + num + " additional perms populated for " + item.getTeacId());
		
		// save subjects
		num = 0;
		if (item.getSubjects() != null)
		    num = saveSubjects(item.getSubjects(), item.getTeacId());
		logger.debug("createItem()," + num + " subjects populated for " + item.getTeacId());
		
		// save grades
		num = 0;
		if (item.getGrades() != null)
		    num = saveGrades(item.getGrades(), item.getTeacId());
		logger.debug("createItem()," + num + " grades populated for " + item.getTeacId());
				
		// save classes
		num = 0;
		if (item.getClasses() != null)
		    num = saveClasses(item.getClasses(), item.getTeacId());
		logger.debug("createItem()," + num + " classes populated for " + item.getTeacId());
		
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
		List<String> idsList = InfoUtil.parseIds(ids);
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

	private int saveRoles(List<UElem> roles, String teachid) {
		String sqlRoles = "insert into ustudy.teacherroles values(?,?,?)";
		String msg = null;
		int num = 0;
		
		if (roles != null) {
			for (UElem u:roles) {
				num = jTea.update(new PreparedStatementCreator(){
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement psmt = conn.prepareStatement(sqlRoles, Statement.RETURN_GENERATED_KEYS);
						psmt.setNull(1, java.sql.Types.NULL);
						psmt.setString(2, InfoUtil.getRolemapping().get(u.getValue()));
						psmt.setString(3, teachid);
						return psmt;
					}
				});
				if (num != 1) {
					msg = "saveRoles(), return value from role insert is " + num;
					logger.warn(msg);
					throw new RuntimeException(msg);
				}
				
				logger.debug("saveRoles(), Role saved -> " + u.getValue() + ":" + teachid);
			}
		}
		
		
		// Noted: a little tricky here, to populate additional permissions for the teacher,
		// need to populate role as "addi_teachid" for the teacher
		num = jTea.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(sqlRoles, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.NULL);
				psmt.setString(2, "addi_" + teachid);
				psmt.setString(3, teachid);
				return psmt;
			}
		});
		if (num != 1) {
			msg = "saveRoles(), return value from additional role insert is " + num;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
		
		logger.debug("saveRoles(), populated additional roles for " + teachid);
		
		if (roles != null)
			num = roles.size() + 1;
		else
			num = 1;
		return num;
		
	}
	
	private int saveAddiPerms(List<UElem> perms, String teachid) {
		String sqlPerms = "insert into ustudy.perms values(?,?,?)";
		String msg = null;
		for (UElem u: perms) {
			int num = jTea.update(new PreparedStatementCreator(){
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement psmt = conn.prepareStatement(sqlPerms, Statement.RETURN_GENERATED_KEYS);
					psmt.setNull(1, java.sql.Types.NULL);
					psmt.setString(2, u.getValue());
					psmt.setString(3, "addi_" + teachid);
					return psmt;
				}
			});
			if (num != 1) {
				msg = "saveAddiPerms(), return value from additional permissions insert is " + num;
				logger.warn(msg);
				throw new RuntimeException(msg);
			}
			
			logger.debug("saveAddiPerms(), Additional permissions saved -> " + u.getValue() + ": addi_" + teachid);
		}

		return perms.size();
		
	}
	
	private int saveSubjects(List<UElem> subs, String teachid) {
		String sqlSub = "insert into ustudy.teachersub values(?,?,?)";
		String msg = null;
		for (UElem u: subs) {
			int num = jTea.update(new PreparedStatementCreator(){
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement psmt = conn.prepareStatement(sqlSub, Statement.RETURN_GENERATED_KEYS);
					psmt.setNull(1, java.sql.Types.NULL);
					psmt.setString(2, u.getValue());
					psmt.setString(3, teachid);
					return psmt;
				}
			});
			if (num != 1) {
				msg = "saveSubjects(), return value from subject insert is " + num;
				logger.warn(msg);
				throw new RuntimeException(msg);
			}
			
			logger.debug("saveSubjects(), subjects saved -> " + u.getValue() + ":" + teachid);
		}
		
	    return subs.size();
	}
	
	private int saveGrades(List<UElem> grades, String teachid) {
		String sqlG = "insert into ustudy.teachergrade values(?,?,?)";
		String msg = null;
		for (UElem u: grades) {
			int num = jTea.update(new PreparedStatementCreator(){
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement psmt = conn.prepareStatement(sqlG, Statement.RETURN_GENERATED_KEYS);
					psmt.setNull(1, java.sql.Types.NULL);
					psmt.setString(2, u.getValue());
					psmt.setString(3, teachid);
					return psmt;
				}
			});
			if (num != 1) {
				msg = "saveGrades(), return value from grade insert is " + num;
				logger.warn(msg);
				throw new RuntimeException(msg);
			}
			
			logger.debug("saveGrades(), grades saved -> " + u.getValue() + ":" + teachid);
		}

		return grades.size();
	}
	
	private int saveClasses(List<UElem> clss, String teachid) {
		String sqlCls = "insert into ustudy.teacherclass values(?,?,?)";
		String msg = null;
		int num = 0;
		for (UElem u: clss) {
			num = jTea.update(new PreparedStatementCreator(){
				@Override
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
					PreparedStatement psmt = conn.prepareStatement(sqlCls, Statement.RETURN_GENERATED_KEYS);
					psmt.setNull(1, java.sql.Types.NULL);
					psmt.setString(2, u.getValue());
					psmt.setString(3, teachid);
					return psmt;
				}
			});
			if (num != 1) {
				msg = "saveClasses(), return value from class insert is " + num;
				logger.warn(msg);
				throw new RuntimeException(msg);
			}
			
			logger.debug("saveClasses(), classes saved -> " + u.getValue() + ":" + teachid);
		}

	    return clss.size();

	}
	
	private String getOrgId() {
		Session ses = SecurityUtils.getSubject().getSession();
		return ses.getAttribute("orgid").toString();
	}
	
	private String getOrgType() {
		Session ses = SecurityUtils.getSubject().getSession();
		return ses.getAttribute("orgtype").toString();
	}
	
}
