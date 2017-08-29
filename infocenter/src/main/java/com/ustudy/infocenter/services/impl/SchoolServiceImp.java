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

	private static final Logger logger = LogManager.getLogger(SchoolServiceImp.class);
	
	@Autowired
	private JdbcTemplate schS;
	
	@Override
	public School getSchool() {
		String orgT = null, orgId = null;
		getOrgInfo(orgT, orgId);
		
		String sqlSch = "select * from ustudy.school where school_id = ?";
		School item = schS.queryForObject(sqlSch, new SchoolRowMapper(), orgId);
		
		// populate school owner and exam owner
		if (!populateOwner(item, orgT, orgId))
			logger.info("getSchool(), failed to populate school/exam owner, maybe not set yet");
		
		populateDeparts(item, orgId);
		return item;
	}
	
	@Transactional
	private boolean populateOwner(School item, final String orgT, final String orgId) {
		// for school owner and examination owner, information could be retrieved from 
		// ustudy.orgowner
		String sqlS = "select loginname, name, role from ustudy.orgowner where orgtype = ？ and orgid = ?";
		List<OwnerBrife> oL = schS.query(sqlS, new OwnerRowMapper(), orgT, orgId); 
		for (OwnerBrife e: oL) {
			if (e.getRole().compareTo("org_owner") == 0) {
				item.setOwner(new TeacherBrife(e.getLoginname(), e.getName()));
			} else if (e.getRole().compareTo("leader") == 0) {
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
		String sqlT = "select value, teacid, teacname from ustudy.departsub JOIN ustudy.teacher where "
				+ "ustudy.departsub.teac_id = ustudy.teacher.teacid and ustudy.departsub.school_id = ? "
				+ "and ustudy.departsub.type = ?";
		List<SubjectTeac> soL = schS.query(sqlT, new SubjectTeacRowMapper(), orgId, dType);
		ConcurrentHashMap<String, List<TeacherBrife>> ret = new ConcurrentHashMap<String, List<TeacherBrife>>();
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
		List<SubjectLeader> lead = new ArrayList<SubjectLeader>();
		ret.forEach((k, v) -> lead.add(new SubjectLeader(k, v)));
		logger.debug("populateDepartSub(), populate department subject leaders successful for " +
				dType + " in school -> " + orgId);
		return lead;
	}
	
	@Transactional
	private boolean populateDeparts(School item, String orgId) {
		String sqlGr = "select ustudy.grade.id, grade_name, classes_num, grade_owner, "
				+ "teacname from ustudy.grade join ustudy.teacher where "
				+ "ustudy.grade.grade_owner = ustudy.teacher.teacid and school_id = ?";
		String sqlGrSub = "select sub_name, sub_owner, teacname from ustudy.gradesub join ustudy.teacher where "
				+ "ustudy.teacher.teacid = ustudy.gradesub.sub_owner and grade_id = ?";
		String sqlCls = "select ustudy.class.id, cls_name, cls_type, cls_owner, teacname from ustudy.class join ustudy.teacher where "
				+ "ustudy.teacher.teacid = ustudy.class.cls_owner and grade_id = ?";
		String sqlClsSub = "select sub_name, sub_owner, teacname from ustudy.classsub join ustudy.teacher where "
				+ "ustudy.teacher.teacid = ustudy.classsub.sub_owner and cls_id = ?";
		
		List<Grade> grL = schS.query(sqlGr, new GradeRowMapper(), orgId);
		List<Grade> highL = new ArrayList<Grade>();
		List<Grade> junL = new ArrayList<Grade>();
		List<Grade> priL = new ArrayList<Grade>();
		List<Grade> othL = new ArrayList<Grade>();
		
		for (Grade gr: grL) {
			// populate <subject> + <prepare lesson teacher>
			List<SubjectTeac> grsubL = schS.query(sqlGrSub, new SubjectTeacRowMapper(), gr.getId());
			gr.setSubs(grsubL);
			// populate class information
			List<ClassInfo> grclsL = schS.query(sqlCls, new ClassInfoRowMapper(), gr.getId());
			for (ClassInfo cls: grclsL) {
				if (!gr.isType() && cls.getClassType().compareTo("none") != 0) {
					gr.setType(true);
				}
				List<SubjectTeac> cSub = schS.query(sqlClsSub, new SubjectTeacRowMapper(), cls.getId());
				cls.setSubs(cSub);
			}
			gr.setcInfo(grclsL);
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
		if (highL.size() > 0) {
			Department hd = new Department("高中部", highL);
			hd.setSubLeader(populateDepartSub(orgId, "high"));
			departs.add(hd);
		}
		if (junL.size() > 0) {
			Department jd = new Department("初中部", junL);
			jd.setSubLeader(populateDepartSub(orgId, "junior"));
			departs.add(jd);
		}
			
		if (priL.size() > 0) {
			Department pd = new Department("小学部", highL);
			pd.setSubLeader(populateDepartSub( orgId, "primary"));
			departs.add(pd);
		}

		if (othL.size() > 0) {
			Department od = new Department("其他", highL);
			od.setSubLeader(populateDepartSub(orgId, "other"));
			departs.add(od);
		}
		
		return true;
	}

	@Override
	@Transactional
	public List<SubjectLeader> getDepSubs(String depName) {
		String orgT = null, orgId = null;
		getOrgInfo(orgT, orgId);
		List<SubjectLeader> subL = populateDepartSub(orgId, depName);
		return subL;
	}

	@Override
	public int updateDepSubs(List<SubjectLeader> subs, String dType) {
		String orgT = null, orgId = null, msg = null;
		getOrgInfo(orgT, orgId);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateGradeInfo(Grade item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TeacherBrife> getGradeTeac(String gradeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassInfo getClassInfo(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateClassInfo(ClassInfo item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TeacherBrife> getClassTeac(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	private void getOrgInfo(String orgT, String orgId) {
		try {
			orgT = InfoUtil.retrieveSessAttr("orgType");
			orgId = InfoUtil.retrieveSessAttr("orgId");
			if (orgT == null || orgId == null || orgT.isEmpty() || orgId.isEmpty()) {
				logger.warn("getOrgInfo(), it seemed that user did not log in");
				throw new RuntimeException("getOrgInfo(), orgType or orgId is not proper");
			}
				
		} catch (Exception e) {
			String msg = "getOrgInfo(), failed to retrieve proper orgType or orgId";
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
		logger.debug("getOrgInfo(), retrieve information for " + orgT + ":" + orgId);
	}
}
