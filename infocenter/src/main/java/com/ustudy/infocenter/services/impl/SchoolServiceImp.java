package com.ustudy.infocenter.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.infocenter.model.ClassInfo;
import com.ustudy.infocenter.model.Department;
import com.ustudy.infocenter.model.Grade;
import com.ustudy.infocenter.model.OwnerBrife;
import com.ustudy.infocenter.model.School;
import com.ustudy.infocenter.model.SubjectLeader;
import com.ustudy.infocenter.model.SubjectTeac;
import com.ustudy.infocenter.model.TeacherBrife;
import com.ustudy.infocenter.services.SchoolService;
import com.ustudy.infocenter.util.ClassInfoRowMapper;
import com.ustudy.infocenter.util.GradeRowMapper;
import com.ustudy.infocenter.util.InfoUtil;
import com.ustudy.infocenter.util.OwnerRowMapper;
import com.ustudy.infocenter.util.SchoolRowMapper;
import com.ustudy.infocenter.util.SubjectTeacRowMapper;

@Service
public class SchoolServiceImp implements SchoolService {

	private static final String SQL_GRADE_SUB = "select sub_name, sub_owner, teacname "
			+ "from gradesub join teacher on teacher.teacid = gradesub.sub_owner where grade_id = ?";
	
	private static final Logger logger = LogManager.getLogger(SchoolServiceImp.class);
	
	@Autowired
	private JdbcTemplate schS;
	
	@Override
	public School getSchool() {
		String orgT = getOrgInfo("orgType"), orgId = getOrgInfo("orgId");
		
		String sqlSch = "select * from ustudy.school where school_id = ?";
		
		School item = schS.queryForObject(sqlSch, new SchoolRowMapper(), orgId);
		
		// populate school owner and exam owner
		if (!populateOwner(item, orgT, orgId))
			logger.info("getSchool(), failed to populate school/exam owner, maybe not set yet");
		
		populateDeparts(item, orgId);
		return item;
	}
	
	@Transactional
	private boolean populateOwner(School item, String orgT, String orgId) {
		// for school owner and examination owner, information could be retrieved from 
		// ustudy.orgowner
		String sqlS = "select loginname, name, role from ustudy.orgowner where orgtype = ? and orgid = ?";
		List<OwnerBrife> oL = schS.query(sqlS, new OwnerRowMapper(), orgT, orgId); 
		for (OwnerBrife e: oL) {
			if (e.getRole().compareTo("校长") == 0) {
				item.setOwner(new TeacherBrife(e.getLoginname(), e.getName()));
			} else if (e.getRole().compareTo("考务老师") == 0) {
				item.setExam(new TeacherBrife(e.getLoginname(), e.getName()));
			} else {
				logger.warn("populateOwner(), unsupported role" + e.toString());
				return false;
			}			
		}	
		return true;
	}
	
	@Transactional
	private List<SubjectLeader> populateDepartSub(final String orgId, final String dType) {
		String sqlG = null; 
		if (dType.compareTo("high") == 0) {
			sqlG = "select id from grade where grade.school_id = ? and (grade.grade_name"
				+ "= '高一' or grade.grade_name = '高二' or grade.grade_name= '高三')";
		} else if (dType.compareTo("junior") == 0) {
			sqlG = "select id from grade where grade.school_id = ? and (grade.grade_name"
					+ "= '七年级' or grade.grade_name = '八年级' or grade.grade_name= '九年级')";
		} else if (dType.compareTo("primary") == 0) {
			sqlG = "select id from grade where grade.school_id = ? and (grade.grade_name = '六年级'"
					+ "or grade.grade_name = '五年级' or grade.grade_name = '四年级' or grade.grade_name= '三年级'"
					+ "or grade.grade_name = '二年级' or grade.grade_name = '一年级')";
		}
		
		// get Grade Ids for specified department
		List<String> gIds = schS.query(sqlG, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int row) throws SQLException {
				String id = new String(rs.getString("id"));
				return id;
			}
		}, orgId);
		
		if (gIds == null || gIds.isEmpty()) {
			logger.warn("populateDepartSub(), no grade id found for " + orgId + " " + dType);
			throw new RuntimeException("No grade id found");
		}
		
		// get subject name list for specified grades
		String sqlSub = "select sub_name from gradesub where grade_id = ";
		boolean first = true;
		for (String id: gIds) {
			if (first) {
				sqlSub += id;
				first = false;
			}
			else {
				sqlSub += " or grade_id = " + id;
			}
		}
		
		List<String> subL = schS.query(sqlSub, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int row) throws SQLException {
				String id = new String(rs.getString("sub_name"));
				return id;
			}
		});
		
		String sqlT = "select value, teacid, teacname from departsub JOIN teacher on "
				+ "departsub.teac_id = teacher.teacid where departsub.school_id = ? and departsub.type = ?";
		List<SubjectTeac> soL = schS.query(sqlT, new SubjectTeacRowMapper(), orgId, dType);
		
		ConcurrentHashMap<String, List<TeacherBrife>> ret = null;
		if (soL != null && !soL.isEmpty()) {
			ret = new ConcurrentHashMap<String, List<TeacherBrife>>();
			for (SubjectTeac st: soL) {
				if (!ret.contains(st.getSub())) {
					List<TeacherBrife> tL = new ArrayList<TeacherBrife>();
					tL.add(st.getTeac());
					ret.put(st.getSub(), tL);
				}
				else {
					ret.get(st.getSub()).add(st.getTeac());
				}
			}
			
			// subjects for which leader is not assigned, need to populate here
			// populate department subject leaders, set teacher list as empty
			for (String s: subL) {
				if (!ret.contains(s))
					ret.put(s, null);
			}
		}

		List<SubjectLeader> lead = new ArrayList<SubjectLeader>();
		if (ret != null && !ret.isEmpty())
			ret.forEach((k, v) -> lead.add(new SubjectLeader(k, v))); 
		
		logger.debug("populateDepartSub(), populate department subject leaders successful for " +
				dType + " in school -> " + orgId);
		return lead;
	}
	
	private boolean populateGrade(Grade gr) {
		
		String sqlCls = "select class.id, cls_name, cls_type, cls_owner, teacname from class join teacher on "
				+ "teacher.teacid = class.cls_owner where grade_id = ?";
		String sqlClsSub = "select sub_name, sub_owner, teacname from classsub join teacher on "
				+ "teacher.teacid = classsub.sub_owner where cls_id = ?";
		
		// populate <subject> + <prepare lesson teacher>
		List<SubjectTeac> grsubL = schS.query(SQL_GRADE_SUB, new SubjectTeacRowMapper(), gr.getId());
		gr.setSubs(grsubL);
		// populate class information
		List<ClassInfo> grclsL = schS.query(sqlCls, new ClassInfoRowMapper(), gr.getId());
		for (ClassInfo cls : grclsL) {
			if (!gr.isType() && cls.getClassType().compareTo("none") != 0) {
				gr.setType(true);
			}
			List<SubjectTeac> cSub = schS.query(sqlClsSub, new SubjectTeacRowMapper(), cls.getId());
			cls.setSubs(cSub);
		}
		gr.setcInfo(grclsL);
		
		return true;
	}
	
	@Transactional
	private boolean populateDeparts(School item, String orgId) {
		String sqlGr = "select id, grade_name, classes_num, grade_owner from grade where school_id = ?";
		
		List<Grade> grL = schS.query(sqlGr, new GradeRowMapper(), orgId);
		List<Grade> highL = new ArrayList<Grade>();
		List<Grade> junL = new ArrayList<Grade>();
		List<Grade> priL = new ArrayList<Grade>();
		List<Grade> othL = new ArrayList<Grade>();
		logger.debug("populateDeparts(), departments for school " + orgId + "->\n" + grL.toString());
		for (Grade gr: grL) {
			populateGrade(gr);
			if (gr.isHigh())
				highL.add(gr);
			else if(gr.isJunior())
				junL.add(gr);
			else if (gr.isPrimary())
				priL.add(gr);
			else
				othL.add(gr);	
		}
		// populate department information
		List<Department> departs = new ArrayList<Department>();
		if (!highL.isEmpty()) {
			Department hd = new Department("高中部", highL);
			hd.setSubLeader(populateDepartSub(orgId, "high"));
			departs.add(hd);
		}
		if (!junL.isEmpty()) {
			Department jd = new Department("初中部", junL);
			jd.setSubLeader(populateDepartSub(orgId, "junior"));
			departs.add(jd);
		}
			
		if (!priL.isEmpty()) {
			Department pd = new Department("小学部", highL);
			pd.setSubLeader(populateDepartSub( orgId, "primary"));
			departs.add(pd);
		}

		if (!othL.isEmpty()) {
			Department od = new Department("其他", highL);
			od.setSubLeader(populateDepartSub(orgId, "other"));
			departs.add(od);
		}
		
		return true;
	}

	@Override
	@Transactional
	public List<SubjectLeader> getDepSubs(String depName) {
		List<SubjectLeader> subL = populateDepartSub(getOrgInfo("orgId"), depName);
		return subL;
	}

	@Override
	public int updateDepSubs(List<SubjectLeader> subs, String dType) {
		logger.debug("updateDepSubs(), updateDepSubs ->" + subs.toString());
		String orgId = getOrgInfo("orgId"), msg = null;

		int cnt = 0;
		// table schema is id, value, teac_id, type, school_id
		String sqlC = "insert into ustudy.departsub values(?,?,?,?,?)";
		for (SubjectLeader sl:subs) {
			// subject name
			String sn = sl.getSubject();
			for (TeacherBrife tea: sl.getOwners()) {
				int num = schS.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement psmt = conn.prepareStatement(sqlC);
						psmt.setNull(1, java.sql.Types.NULL);
						psmt.setString(2, sn);
						psmt.setString(3, tea.getTeacid());
						psmt.setString(4, dType);
						psmt.setString(5, orgId);
						return psmt;
					}
				});
				if (num != 1) {
					msg = "updateDepSubs(), return value from insert is " + num;
					logger.warn(msg);
					throw new RuntimeException(msg);
				}
				cnt++;
			}
		}
		logger.debug("updateDepSubs(), number of inserted records is " + cnt);
		return cnt;
	}

	@Override
	public List<TeacherBrife> getDepTeac(String depName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Grade getGradeInfo(String id) {
		String sqlGr = "select * from ustudy.grade where id = ?";
		Grade info = schS.queryForObject(sqlGr, new GradeRowMapper(), id);
		populateGrade(info);
		return info;
	}

	@Override
	public int updateGradeInfo(Grade item) {
		logger.debug("updateGradeInfo(), new grade ->" + item.toString());
		String sqlG = "update ustudy.grade set grade_name = ?, classes_num = ?, "
				+ "grade_owner = ? where id = ?";
		int cnt = schS.update(sqlG, item.getName(), item.getClassNum(), 
				item.getGradeO().getTeacid(), item.getId());
		return cnt;
	}

	@Transactional
	private List<SubjectTeac> getGradeSubs(String id) {
		List<SubjectTeac> subL = schS.query(SQL_GRADE_SUB, new SubjectTeacRowMapper(), id);
		return subL;
	}

	@Override
	@Transactional
	public List<TeacherBrife> getGradeTeac(String id) {
		List<TeacherBrife> teaL = null;
		return teaL;
	}
	
	@Override
	@Transactional
	public ClassInfo getClassInfo(String id) {
		String sqlC = "select * from ustudy.class where id = ?";
		ClassInfo info = schS.queryForObject(sqlC, new ClassInfoRowMapper(), id);
		
		// need to populate class subjects information
		info.setSubs(getClassSubs(id));
		return info;
	}

	@Override
	@Transactional
	public int updateClassInfo(ClassInfo item) {
		logger.debug("updateClassInfo(), new item->" + item.toString());
		String sqlCls = "update class set cls_name = ?, cls_type = ?, cls_owner = ? where id =?";
		int cnt = schS.update(sqlCls, item.getClassName(), item.getClassType(),
				item.getClaOwner().getTeacid(), item.getId());
		return cnt;
	}

	@Transactional
	private List<SubjectTeac> getClassSubs(String id) {
		String sqlT = "select sub_name, sub_owner, teacname from classsub join teacher on "
				+ "classsub.sub_owner = teacher.teacid where class.cls_id = ?";
		List<SubjectTeac> stL = schS.query(sqlT, new SubjectTeacRowMapper(), id);
		
		return stL;
	}
	
	@Override
	@Transactional
	public List<TeacherBrife> getClassTeac(String id) {
		List<TeacherBrife> teaL = null;
		return teaL;
	}
	
	private String getOrgInfo(String key) {
		String orgV = null;
		try {
			orgV = InfoUtil.retrieveSessAttr(key);
			if (orgV == null || orgV.isEmpty()) {
				logger.warn("getOrgInfo(), it seemed that user did not log in");
				throw new RuntimeException("getOrgInfo(), orgValue is not proper for " + key);
			}
		} catch (Exception e) {
			String msg = "getOrgInfo(), failed to retrieve org value for " + key;
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
		logger.debug("getOrgInfo(), " + key + "->" + orgV);
		return orgV;
	}
}
