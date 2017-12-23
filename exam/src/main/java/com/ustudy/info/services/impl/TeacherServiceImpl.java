package com.ustudy.info.services.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.cj.api.jdbc.Statement;
import com.ustudy.info.mapper.SchoolMapper;
import com.ustudy.info.mapper.TeaMapper;
import com.ustudy.info.model.Item;
import com.ustudy.info.model.TeaGrade;
import com.ustudy.info.model.Subject;
import com.ustudy.info.model.Teacher;
import com.ustudy.info.model.UElem;
import com.ustudy.info.services.TeacherService;
import com.ustudy.info.util.InfoUtil;
import com.ustudy.info.util.UElemRowMapper;

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
	
	@Autowired
	private TeaMapper teaM;

	@Autowired
	private SchoolMapper schM;
	
	@Override
	public List<Teacher> getList(int id) {

		String orgT = InfoUtil.retrieveSessAttr("orgType");
		String orgId = InfoUtil.retrieveSessAttr("orgId");
		if (orgT == null || orgT.isEmpty() || orgId == null || orgId.isEmpty()) {
			logger.error("getList(), it seemed that user not logged in");
			throw new RuntimeException("getList(), it seemed user not logged in");
		}
		
		if (id < 0)
			id = 0;
		
		List<Teacher> teaL = teaM.getTeaList(id, orgT, orgId);
		for (Teacher tea : teaL) {
			// Noted: for getList() service, front end doesn't need
			// additional permissions related information
			retrieveProp(tea);
			logger.debug(tea.toString());
		}
		
		return teaL;
	}

	@Override
	public Teacher displayTeacher(int id) {
		Teacher item = teaM.findTeaById(id);
		retrieveProp(item);

		return item;
	}

	private void retrieveProp(Teacher item) {
		// retrieve grades
		List<TeaGrade> grs = teaM.getTeaGrade(item.getTeacId());
		if (grs == null || grs.isEmpty()) {
			logger.error("retrieveProp(), no grades found for " + item.getTeacId());
			throw new RuntimeException("retrieveProp(), no grades found for " + item.getTeacId());
		}

		for (TeaGrade tr: grs) {
			// retrieve subjects
			List<Item> subs = teaM.getTeaSubs(item.getTeacId());
			if (subs == null || subs.isEmpty()) {
				logger.error("retrieveProp(), no subjects found for " + item.getTeacId());
				throw new RuntimeException("retrieveProp(), no subjects found for " + item.getTeacId());
			}
			tr.setSubs(subs);
		}
		
		item.setGrades(grs);

		// retrieve classes
		/*
		sqlD = "select cls_name as value from ustudy.teacherclass left join ustudy.class on "
				+ "teacherclass.class_id = ustudy.class.id where teac_id = ? and ustudy.teacherclass.class_id "
				+ "IS NULL";
		List<UElem> cls = jTea.query(sqlD, new UElemRowMapper(), item.getTeacId());
		item.setClasses(cls);
		
	    // retrieve roles and exclude addi_{teac_id}
		String aRole = "addi_" + item.getTeacId();
		sqlD = "select role_name as value from ustudy.teacherroles where teac_id = ? and role_name != ?";
		List<UElem> rs = jTea.query(sqlD, new UElemRowMapper(), item.getTeacId(), aRole); */
		
		List<Item> rs = teaM.getTeaRoles(item.getTeacId());
		// convert internal role to user readable role name
		for (Item e : rs) {
			e.setName(InfoUtil.getRolemapping().get(e.getName()));
		}
		logger.debug("retrieveProp(), roles -> " + rs.toString());
		item.setRoles(rs);

		// retrieve additional permissions
		String aRole = "addi_" + item.getTeacId();
		String sqlD = "select * from ustudy.perms where role_name = ?";
		List<UElem> ps = jTea.query(sqlD, new UElemRowMapper(), aRole);
		item.setAddiPerms(ps);

	}

	@Override
	public Teacher findTeacherById(String teaid) {
		logger.debug("findTeacherById(), retrieve information for " + teaid);
		return teaM.findTeaByTeaId(teaid); 
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
	public int createTeacher(Teacher item) {
		
		String msg = null;

		String orgT = InfoUtil.retrieveSessAttr("orgType");
		String orgId = InfoUtil.retrieveSessAttr("orgId");
		if (orgT == null || orgT.isEmpty() || orgId == null || orgId.isEmpty()) {
			throw new RuntimeException("createTeacher(), it seemed user not logged in");
		}
		item.setOrgid(orgId);
		item.setOrgtype(orgT);

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (item.getPasswd() != null) {
				md.update(item.getPasswd().getBytes(), 0, item.getPasswd().length());
			} else {
				// if no passwd set for teacher, passwd should be last 6 characters in teacId
				if (item.getTeacId() == null || item.getTeacId().length() < 6) {
					
					logger.error("createTeacher(), teacher id contains less than 6 characters, failed to "
							+ "populate password");
					
					throw new RuntimeException("createTeacher(), failed to set password.");
				}
				String pw = item.getTeacId().substring(item.getTeacId().length() - 6,
						item.getTeacId().length());
				md.update(pw.getBytes(), 0, 6);

			}

			item.setPasswd(String.format("%032x", new BigInteger(1, md.digest())));
			
		} catch (NoSuchAlgorithmException ne) {
			msg = "createTeacher(), failed to initialize MD5 algorithm.";
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
		
		// ready to insert teacher info to database
		int ret = teaM.createTeacher(item);
		
		if (ret < 0 || ret > 2) {
			msg = "createTeacher(), insert failed with ret->" + ret;
			logger.error(msg);
			throw new RuntimeException(msg);
		}

		if (item.getId() < 0) {
			msg = "createTeacher(), invalid id " + item.getId();
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		
		logger.debug("createTeacher(), created ret->" + ret + ", generated id->" + item.getId());

		// save teacher grades
		if (item.getGrades() != null)
			ret = saveTeaGrades(item.getGrades(), item.getTeacId());
		logger.debug("createTeacher()," + ret + " grades populated for " + item.getTeacId());

		// save classes
		if (item.getClasses() != null)
			ret = saveClasses(item.getClasses(), item.getTeacId());
		logger.debug("createTeacher()," + ret + " classes populated for " + item.getTeacId());

		// save roles
		if (item.getRoles() != null) {
			ret = saveRoles(item.getRoles(), item);
		}
		logger.debug("createTeacher(), " + ret + " roles is saved for " + item.getTeacId());

		// save additional permission
		if (item.getAddiPerms() != null)
			ret = saveAddiPerms(item.getAddiPerms(), item.getTeacId());
		logger.debug("createTeacher()," + ret + " additional perms populated for " + item.getTeacId());

		logger.debug("createTeacher(), teacher created->" + item);
		return item.getId();
	}

	@Override
	public int updateTeacher(Teacher item, int id) {
		String updateOrg = "update ustudy.teacher set ";
		Teacher origin = displayTeacher(id);
		Map<String, String> orgDiff = item.compare(origin);
		if (orgDiff.size() == 0) {
			logger.info("No changes in orgowner and no need to update");
			return 0;
		} else {
			// some stuff in school changed, need to populate changed fields
			Set<Map.Entry<String, String>> fields = orgDiff.entrySet();
			boolean first = true;
			for (Map.Entry<String, String> elem : fields) {
				if (first) {
					updateOrg += elem.getKey() + " = '" + elem.getValue() + "'";
					first = false;
				} else
					updateOrg += ", " + elem.getKey() + " = '" + elem.getValue() + "'";
			}
			logger.debug("Update SQL for teacher item " + id + " -->" + updateOrg);
		}
		updateOrg += " where id = ?";
		int num = jTea.update(updateOrg, id);
		return num;
	}

	@Override
	public int delTeas(String ids) {
		List<String> idsList = InfoUtil.parseIds(ids);
		int len = idsList.size();
		if (len == 0)
			return 0;

		String sqlDel = "delete from ustudy.teacher where ";
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				sqlDel += "id = '" + idsList.get(0) + "'";
			} else
				sqlDel += " or id = '" + idsList.get(i) + "'";
		}
		logger.debug("delItemSet(): assembled sql for batch deletion --> " + sqlDel);
		return jTea.update(sqlDel);
	}

	@Override
	public int deleteTeacher(int id) {
		String sqlDel = "delete from ustudy.teacher where id = ?";
		return jTea.update(sqlDel, id);
	}

	@Transactional
	private int saveRoles(List<Item> roles, Teacher item) {

		String msg = null;
		int ret = 0;
		// retrieve system defined roles firstly
		List<Item> roL = schM.getRoles();
		HashMap<String, String> roleMap = new HashMap<String, String>();
		for (Item it: roL) {
			roleMap.put(String.valueOf(it.getId()), it.getName());
		}
		
		// actually, here only one role specified when creating teacher
		for (Item u : roles) {
			ret = teaM.saveRoles(u.getId(), item.getTeacId());
			if (ret < 0 || ret >2) {
				msg = "saveRoles(), save role failed with ret->" + ret;
				logger.warn(msg);
				throw new RuntimeException(msg);
			}

			logger.debug("saveRoles(), Role saved -> " + u.toString() + ":" + item.getTeacId());
			int subId = item.getGrades().get(0).getSubs().get(0).getId();
			int grId = item.getGrades().get(0).getId();
			
			// if role is sleader, need to populate into corresponding departsub
			String rv = roleMap.get(String.valueOf(u.getId()));
			if (rv == null) {
				logger.error("saveRoles(), undefined roles in request ->" + rv);
				throw new RuntimeException("saveRoles(), undefined roles in request ->" + rv);
			}
			if (rv.compareTo("sleader") == 0) {
				String grName = teaM.getGrName(grId);
				List<String> grNameL = new ArrayList<String>();
				grNameL.add(grName);
				List<String> tyL = determineDepartType(grNameL);
				logger.debug("saveRoles(), department info->" + tyL.toString());
				for (String t : tyL) {
					ret = teaM.saveDepartSubOwner(subId, item.getOrgid(), t, item.getTeacId());
					if (ret != 1 || ret != 0) {
						msg = "saveRoles(), populate depart sub owner failed with ret->" + ret;
						logger.warn(msg);
						throw new RuntimeException(msg);
					}
				}
			} else if (rv.compareTo("pleader") == 0) {
				logger.debug("saveRoles(), prepare subject leader " + subId + "," + grId);
				ret = teaM.saveGrSubOwner(subId, grId, item.getTeacId());
			} else if (rv.compareTo("gleader") == 0) {
				// only one grade specified when creating teacher
				ret = teaM.saveGrOwner(grId, item.getTeacId());
			}
		}

		// Noted: a little tricky here, to populate additional permissions for
		// the teacher, need to populate role as "addi_teachid" for the teacher
		/*
		ret = jTea.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement psmt = conn.prepareStatement(sqlRoles, Statement.RETURN_GENERATED_KEYS);
				psmt.setNull(1, java.sql.Types.NULL);
				psmt.setString(2, "addi_" + item.getTeacId());
				psmt.setString(3, item.getTeacId());
				return psmt;
			}
		});
		if (ret != 1) {
			msg = "saveRoles(), return value from additional role insert is " + ret;
			logger.warn(msg);
			throw new RuntimeException(msg);
		} 

		logger.debug("saveRoles(), populated additional roles for " + item.getTeacId());
		if (roles != null)
			ret = roles.size() + 1;
		else
			ret = 1; */
		return ret;

	}

	private int saveAddiPerms(List<UElem> perms, String teachid) {
		String sqlPerms = "insert into ustudy.perms values(?,?,?)";
		String msg = null;
		for (UElem u : perms) {
			int num = jTea.update(new PreparedStatementCreator() {
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

	private int saveSubjects(List<Item> subs, String teacid) {
		
		List<Subject> subL = teaM.getSubs();
		HashMap<String, String> subMap = new HashMap<String, String>();
		for (Subject sub:subL) {
			subMap.put(sub.getSubName(), sub.getSubId());
		}
		
		for (Item u : subs) {
			String sId = subMap.get(String.valueOf(u.getId()));
			if (sId == null || sId.isEmpty()) {
				logger.error("saveSubjects(), invalid sub id->" + sId);
				throw new RuntimeException("saveSubjects(), invalid subject id");
			}
			int ret = teaM.saveTeaSub(sId, teacid);
			if (ret < 0 || ret > 2) {
				logger.error("saveSubjects(), insert failed with ret->" + ret);
				throw new RuntimeException("saveSubjects(), insert failed with ret->" + ret);
			}

			logger.debug("saveSubjects(), subjects saved -> " + u.getId() + ":" + teacid);
		}

		return subs.size();
	}

	private int saveTeaGrades(List<TeaGrade> grades, String teacid) {
		String msg = null;
		for (TeaGrade u : grades) {
			int ret = teaM.saveTeaGr(u.getId(), teacid);
			if (ret != 1) {
				msg = "saveTeaGrades(), insert failed with ret->" + ret + u.toString();
				logger.error(msg);
				throw new RuntimeException(msg);
			}
			
			List<Item> subs = u.getSubs();
			if (subs == null || subs.isEmpty()) {
				msg = "saveTeaGrades(), no subject specified," + u.toString();
				logger.error(msg);
				throw new RuntimeException(msg);
			}
			
			ret = saveSubjects(subs, teacid);
			if (ret != subs.size()) {
				msg = "saveTeaGrades(), num of saved subjects not matched with specifed in request, " + ret
						+ "," + subs.size();
				logger.error(msg);
				throw new RuntimeException(msg);
			}
				
			logger.debug("saveTeaGrades(), grades saved -> " + u.toString() + ":" + teacid);
		}

		return grades.size();
	}

	private int saveClasses(List<UElem> clss, String teachid) {
		String sqlCls = "insert into ustudy.teacherclass values(?,?,?)";
		String msg = null;
		int num = 0;
		for (UElem u : clss) {
			num = jTea.update(new PreparedStatementCreator() {
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

	private List<String> determineDepartType(List<String> gL) {
		List<String> tL = new ArrayList<String>();

		if (gL.contains("高一") || gL.contains("高二") || gL.contains("高三"))
			tL.add("high");
		if (gL.contains("九年级") || gL.contains("八年级") || gL.contains("七年级"))
			tL.add("junior");
		if (gL.contains("六年级") || gL.contains("五年级") || gL.contains("四年级") || gL.contains("三年级")
				|| gL.contains("二年级") || gL.contains("一年级"))
			tL.add("primary");
		logger.debug("determineDepartType(), " + tL.toString());
		return tL;
	}

	@Override
	public String findPriRoleById(String teaid) {
		// front end need login user information including teacher id and
		// highest priority role
		String sqlRole = "select role_name from ustudy.teacherroles where teac_id = ?";
		List<String> rL = jTea.queryForList(sqlRole, String.class, teaid);
		if (rL == null || rL.isEmpty()) {
			logger.warn("findPriRoleById(), Something goes wrong, no roles bind with user " + teaid);
			return null;
		}

		String r = null;
		if (rL.contains("org_owner")) {
			r = "org_owner";
		} else if (rL.contains("leader")) {
			r = "leader";
		} else if (rL.contains("gleader")) {
			r = "gleader";
		} else if (rL.contains("sleader")) {
			r = "sleader";
		} else if (rL.contains("pleader")) {
			r = "pleader";
		} else if (rL.contains("cteacher")) {
			r = "cteacher";
		} else
			r = "teacher";

		return InfoUtil.getRolemapping().get(r);
	}
}
