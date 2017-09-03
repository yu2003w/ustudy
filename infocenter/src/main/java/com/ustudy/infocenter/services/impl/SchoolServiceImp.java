package com.ustudy.infocenter.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
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

	private static final String SQL_GRADE_SUB = "select sub_name, sub_owner from gradesub where grade_id = ?";
	
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
	private List<SubjectLeader> populateDepartSub(String orgId, String dType) {
		
		String sqlT = "select sub_name, sub_owner from departsub where "
				+ "departsub.school_id = ? and departsub.type = ?";
		List<SubjectTeac> soL = schS.query(sqlT, new SubjectTeacRowMapper(), orgId, dType);
		
		logger.debug("populateDepartSub(), " + soL.toString());
		ConcurrentHashMap<String, List<TeacherBrife>> ret = null;
		if (soL != null && !soL.isEmpty()) {
			ret = new ConcurrentHashMap<String, List<TeacherBrife>>();
			for (SubjectTeac st: soL) {
				if (!ret.contains(st.getSub())) {
					List<TeacherBrife> tL = new ArrayList<TeacherBrife>();
					if (st.getTeac().getTeacname() != null) {
						tL.add(st.getTeac());
					}
					ret.put(st.getSub(), tL);
				}
				else {
					if (st.getTeac().getTeacname() != null)
						ret.get(st.getSub()).add(st.getTeac());
				}
			}
		}
		logger.debug("populateDepartSub()," + ret.toString());
		List<SubjectLeader> lead = new ArrayList<SubjectLeader>();
		if (ret != null && !ret.isEmpty())
			ret.forEach((k, v) -> lead.add(new SubjectLeader(k, v))); 
		
		logger.debug("populateDepartSub(), populate department subject leaders for " +
				dType + " in school -> " + orgId);
		logger.debug("populateDepartSub()," + lead.toString());
		return lead;
	}
	
	private boolean populateGrade(Grade gr) {
		
		String sqlCls = "select id, cls_name, cls_type, cls_owner from class where grade_id = ?";
		String sqlClsSub = "select sub_name, sub_owner from classsub where cls_id = ?";
		
		// populate <subject> + <prepare lesson teacher>
		if (gr.getId() == null || gr.getId().isEmpty()) {
			logger.warn("populateGrade(), grade id is not correct.");
			return false;
		}
		List<SubjectTeac> grsubL = schS.query(SQL_GRADE_SUB, new SubjectTeacRowMapper(), gr.getId());
		logger.debug("populateGrade(), grade subject ->" + grsubL.toString());
		gr.setSubs(grsubL);
		// populate class information
		List<ClassInfo> grclsL = schS.query(sqlCls, new ClassInfoRowMapper(),gr.getId());
		
		if (grclsL != null && !grclsL.isEmpty()) {
			logger.debug("populateGrade(), class info ->" + grclsL.toString());
			for (ClassInfo cls : grclsL) {
				if (!gr.isType() && cls.getClassType().compareTo("none") != 0) {
					gr.setType(true);
				}
				List<SubjectTeac> cSub = schS.query(sqlClsSub, new SubjectTeacRowMapper(), cls.getId());
				cls.setSubs(cSub);
			}
		} else
			logger.debug("populateGrade(), no classes configured for grade " + gr.getId() + ":" + gr.getName());
		
		logger.debug("populateGrade(), classes configured for " + gr.getName() + " as ->" + grclsL);
		gr.setcInfo(grclsL);
		return true;
	}
	
	@Transactional
	private void populateDeparts(School item, String orgId) {
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
		
		item.setDeparts(departs);
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
