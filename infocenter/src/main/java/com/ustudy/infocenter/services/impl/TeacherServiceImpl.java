package com.ustudy.infocenter.services.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

			String orgT = InfoUtil.retrieveSessAttr("orgType");
			String orgId = InfoUtil.retrieveSessAttr("orgId");
			if ( orgT == null || orgT.isEmpty() ||
					orgId == null || orgId.isEmpty()) {
				throw new RuntimeException("getList(), it seemed user not logged in");
			}
			
			if (id < 0)
				id = 0;
			
			teaL = jTea.query(sqlOrg, new TeacherRowMapper(), id, orgId, orgT);
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
		
		// retrieve roles and exclude addi_{teac_id}
		String aRole = "addi_" + item.getTeacId();
		sqlD = "select * from ustudy.teacherroles where teac_id = ? and value != ?";
		List<UElem> rs = jTea.query(sqlD, new UElemRowMapper(), item.getTeacId(), aRole);
		// convert internal role to user readable role name
		for (UElem e: rs) {
			e.setValue(InfoUtil.getRolemapping().get(e.getValue()));
		}
		logger.debug("retrieveProp(), roles -> " + rs.toString());
		item.setRoles(rs);
		
		// retrieve additional permissions
		sqlD = "select * from ustudy.perms where role_name = ?";
		List<UElem> ps = jTea.query(sqlD, new UElemRowMapper(), aRole);
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
	public boolean updateLLTime(final String id) {
		String sqlLL = "update teacher set ll_time = ? where id = ?";
		int num = jTea.update(sqlLL, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), id);
		if (num != 1) {
			logger.debug("updateLLTime(), set last login time failed for teacher " + id);
			return false;
		}
		return true;
	}
	
	@Override
	@Transactional
	public int createItem(Teacher item) {
		logger.debug("createItem(), " + item);
		// Noted: Schema for table ustudy.teacher is as below,
		// id, teacid, teacname, passwd, orgType, orgId, ctime, lltime
		String sqlTeac = "insert into ustudy.teacher values(?,?,?,?,?,?,?,?)";

		// insert record into ustudy.teacher firstly, also auto generated keys is required.
		KeyHolder keyH = new GeneratedKeyHolder();
		int id = -1;    // auto generated id
		String msg = null;
	
		String orgT = InfoUtil.retrieveSessAttr("orgType");
		String orgId = InfoUtil.retrieveSessAttr("orgId");
		if ( orgT == null || orgT.isEmpty() ||
				orgId == null || orgId.isEmpty()) {
			throw new RuntimeException("createItem(), it seemed user not logged in");
		}
		
		// need to retrieve auto id of teacher item which is returned back in header location
		int num = jTea.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(sqlTeac, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.INTEGER);
				psmt.setString(2, item.getTeacId());
				psmt.setString(3, item.getTeacName());
				
				// Noted: password should be set as last 6 digits of teacher id which is phone number
				try {
					MessageDigest md = MessageDigest.getInstance("MD5");
					if (item.getPasswd() != null) {
						md.update(item.getPasswd().getBytes(), 0, item.getPasswd().length());
					} else {
						// if no passwd set for teacher, passwd should be last 6 characters in teacId
						if (item.getTeacId() == null || item.getTeacId().length() < 6) {
							logger.warn("createItem(), teacher id contains less than 6 "
									+ "characters, failed to populate password");
							throw new RuntimeException("createItem(), failed to set password.");
						}
						String pw = item.getTeacId().substring(item.getTeacId().length() - 6, item.getTeacId().length());
						md.update(pw.getBytes(), 0, 6);
							
					}
					
					item.setPasswd(String.format("%032x", new BigInteger(1, md.digest())));
				} catch (NoSuchAlgorithmException ne) {
					String emsg = "createItem(), failed to initialize MD5 algorithm.";
					logger.warn(emsg);
					throw new RuntimeException(emsg);
				}
				
				psmt.setString(4, item.getPasswd());
				
				// Noted: need to populate current user's orgtype, orgid
				psmt.setString(5, orgT);
				psmt.setString(6, orgId);
				
				// teacher creation time should be set to current time
				psmt.setString(7, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
				psmt.setNull(8, java.sql.Types.DATE);
				
				return psmt;
			}
		}, keyH);
		if (num != 1) {
			msg = "createItem(), return value for teacher insert is " + num;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
	
		id = keyH.getKey().intValue();
		if (id < 0) {
			msg = "createItem() failed with invalid id " + id;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
		
		// save roles
		num = saveRoles(item.getRoles(), item);
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

	@Transactional
	private int saveRoles(List<UElem> roles, Teacher item) {
		String sqlRoles = "insert into ustudy.teacherroles values(?,?,?)";
		String sqlDepSub = "insert into departsub values(?,?,?,?,?)";
		String sqlGSub = "update gradesub set sub_owner = ? where grade_id = (select id from grade where school_id "
				+ "= ? and grade_name = (select value from teachergrade where teac_id = ?)) and sub_name = ?";
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
						psmt.setString(3, item.getTeacId());
						return psmt;
					}
				});
				if (num != 1) {
					msg = "saveRoles(), return value from role insert is " + num;
					logger.warn(msg);
					throw new RuntimeException(msg);
				}
				
				logger.debug("saveRoles(), Role saved -> " + u.getValue() + ":" + item.getTeacId());
				// if role is sleader, need to populate into corresponding departsub
				if (InfoUtil.getRolemapping().get(u.getValue()).compareTo("sleader") == 0) {
					List<String> tyL = determineDepartType(item.getGrades());
					for (String t: tyL) {
						num = jTea.update(sqlDepSub, null, item.getSubjects().get(0), item.getTeacId(), t, item.getOrgid());
						if (num != 1) {
							msg = "saveRoles(), return value from department subject insert is " + num;
							logger.warn(msg);
							throw new RuntimeException(msg);
						}
					}
				} else if (InfoUtil.getRolemapping().get(u.getValue()).compareTo("pleader") == 0) {
					jTea.update(sqlGSub, item.getTeacId(), InfoUtil.retrieveSessAttr("orgId"), 
							item.getTeacId(), item.getSubjects().get(0));
				}
				
			}
		}
		
		
		// Noted: a little tricky here, to populate additional permissions for the teacher,
		// need to populate role as "addi_teachid" for the teacher
		num = jTea.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(sqlRoles, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.NULL);
				psmt.setString(2, "addi_" + item.getTeacId());
				psmt.setString(3, item.getTeacId());
				return psmt;
			}
		});
		if (num != 1) {
			msg = "saveRoles(), return value from additional role insert is " + num;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
		
		logger.debug("saveRoles(), populated additional roles for " + item.getTeacId());
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
	
	private List<String> determineDepartType(List<UElem> gL) {
		List<String> tL = new ArrayList<String>();
		if (gL.contains(new UElem("高一")) || gL.contains(new UElem("高二")) || gL.contains(new UElem("高三")))
			tL.add("high");
		if (gL.contains(new UElem("九年级")) || gL.contains(new UElem("八年级")) || gL.contains(new UElem("七年级")))
			tL.add("junior");
		if (gL.contains(new UElem("六年级")) || gL.contains(new UElem("五年级")) || gL.contains(new UElem("四年级")) ||
				gL.contains(new UElem("三年级")) || gL.contains(new UElem("二年级")) || gL.contains(new UElem("一年级")))
			tL.add("primary");
		logger.debug("determineDepartType(), " + tL.toString());
		return tL;
	}
}
