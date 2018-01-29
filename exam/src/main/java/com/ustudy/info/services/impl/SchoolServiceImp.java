package com.ustudy.info.services.impl;

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

import com.ustudy.Item;
import com.ustudy.info.mapper.SchoolMapper;
import com.ustudy.info.model.ClassInfo;
import com.ustudy.info.model.Department;
import com.ustudy.info.model.Grade;
import com.ustudy.info.model.GradeSubRoles;
import com.ustudy.info.model.OwnerBrife;
import com.ustudy.info.model.School;
import com.ustudy.info.model.SubjectLeader;
import com.ustudy.info.model.SubjectTeac;
import com.ustudy.info.model.SchGradeSub;
import com.ustudy.info.model.TeacherBrife;
import com.ustudy.info.model.TeacherSub;
import com.ustudy.info.services.SchoolService;
import com.ustudy.info.util.InfoUtil;
import com.ustudy.info.util.TeacherSubRowMapper;

@Service
public class SchoolServiceImp implements SchoolService {
	
	private static final Logger logger = LogManager.getLogger(SchoolServiceImp.class);
	
	@Autowired
	private JdbcTemplate schS;
	
	@Autowired
	private SchoolMapper schM;
	
	@Override
	public School getSchool() {
		String orgT = InfoUtil.retrieveSessAttr("orgType");
		String orgId = InfoUtil.retrieveSessAttr("orgId");
		if (orgT == null || orgT.isEmpty() || orgId == null || orgId.isEmpty()) {
			throw new RuntimeException("getSchool(), it seemed user not logged in");
		}
		
		if (orgT.compareTo("学校") != 0) 
			throw new RuntimeException("getSchool(), invalid org type->" + orgT);
		School item = schM.getSchoolById(orgId);
		
		// populate school owner and exam owner
		if (!populateOwner(item, orgT, orgId))
			logger.info("getSchool(), failed to populate school/exam owner, maybe not set yet");
		
		populateDeparts(item, orgId);
		logger.debug("getSchool(), detailed information as below:\n" + item.toString());
		return item;
	}
	
	private boolean populateOwner(School item, String orgT, String orgId) {
		// school owner and examination owner could be retrieved from ustudy.orgowner

		List<OwnerBrife> oL = schM.getOrgOwners(orgT, orgId);
		for (OwnerBrife e: oL) {
			if (e.getRole().compareTo("校长") == 0) {
				item.setOwner(new TeacherBrife(e.getLoginname(), e.getName()));
			} else if (e.getRole().compareTo("考务老师") == 0) {
				item.setExam(new TeacherBrife(e.getLoginname(), e.getName()));
			} else {
				logger.error("populateOwner(), unsupported role" + e.toString());
				return false;
			}			
		}	
		return true;
	}
	
	private List<SubjectLeader> populateDepartSub(String orgId, String dType) {
		logger.debug("populateDepartSub(), populate department subject leaders for " +
				dType + " in school -> " + orgId);
		
		List<SubjectTeac> soL = schM.getSubjectTeachers(orgId, dType);
		
		if (soL == null || soL.isEmpty()) {
			logger.warn("populateDepartSub(), no subject teachers found for school->" + orgId + ", " + dType);
		}
		else
			logger.debug("populateDepartSub(), subject teachers->" + soL.toString());
		
		ConcurrentHashMap<String, List<TeacherBrife>> ret = null;
		if (soL != null && !soL.isEmpty()) {
			ret = new ConcurrentHashMap<String, List<TeacherBrife>>();
			for (SubjectTeac st: soL) {
				if (!ret.contains(st.getSub())) {
					List<TeacherBrife> tL = new ArrayList<TeacherBrife>();
					if (st.getTeac() != null && st.getTeac().getTeacname() != null) {
						tL.add(st.getTeac());
					}
					ret.put(st.getSub(), tL);
				}
				else {
					if (st.getTeac() != null && st.getTeac().getTeacname() != null)
						ret.get(st.getSub()).add(st.getTeac());
				}
			}
			logger.debug("populateDepartSub(), subject teachers->" + ret.toString());
		}
		
		List<SubjectLeader> lead = new ArrayList<SubjectLeader>();
		if (ret != null && !ret.isEmpty())
			ret.forEach((k, v) -> lead.add(new SubjectLeader(k, v)));
		
		logger.debug("populateDepartSub(), subject leader->" + lead.toString());
		return lead;
	}
	
	private boolean populateGrade(Grade gr) {
		
		// populate <subject> + <prepare lesson teacher>
		if (gr.getId() == null || gr.getId().isEmpty()) {
			logger.warn("populateGrade(), grade id is not correct.");
			return false;
		}
		List<SubjectTeac> grsubL = schM.getGrSubs(Integer.valueOf(gr.getId()));
		logger.debug("populateGrade(), grade subject ->" + grsubL.toString());
		gr.setSubs(grsubL);
		
		// populate class information
		List<ClassInfo> grclsL = schM.getClsInfoByGrId(Integer.valueOf(gr.getId()));
		
		if (grclsL != null && !grclsL.isEmpty()) {
			logger.debug("populateGrade(), class info ->" + grclsL.toString());
			for (ClassInfo cls : grclsL) {
				if (!gr.isType() && cls.getClassType().compareTo("none") != 0) {
					gr.setType(true);
				}
				List<SubjectTeac> cSub = schM.getClsSubs(Integer.valueOf(cls.getId()));
				if (cSub == null || cSub.isEmpty())
					cSub = new ArrayList<SubjectTeac>();
				cls.setSubs(cSub);
			}
		} else {
			logger.debug("populateGrade(), no classes configured for grade " + gr.getId() + ":" + gr.getName());
			grclsL = new ArrayList<ClassInfo>();
		}

		logger.debug("populateGrade(), classes configured for " + gr.getName() + " as ->" + grclsL.toString());
		gr.setcInfo(grclsL);
		return true;
	}
	
	private void populateDeparts(School item, String orgId) {
		
		List<Grade> grL = schM.getGradesBySchId(orgId);
		List<Grade> highL = new ArrayList<Grade>();
		List<Grade> junL = new ArrayList<Grade>();
		List<Grade> priL = new ArrayList<Grade>();
		
		for (Grade gr: grL) {
			populateGrade(gr);
			if (gr.isHigh())
				highL.add(gr);
			else if(gr.isJunior())
				junL.add(gr);
			else if (gr.isPrimary())
				priL.add(gr);
			else
				logger.warn("populateDeparts(), unknown type.");
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
		
		logger.debug("populateDeparts(), departments for school " + orgId + "->\n" + grL.toString());
		item.setDeparts(departs);
	}

	@Override
	public List<SubjectLeader> getDepSubs(String depName) {
		List<SubjectLeader> subL = populateDepartSub(getOrgInfo("orgId"), depName);
		return subL;
	}

	@Override
	@Transactional
	public int updateDepSubs(List<SubjectLeader> subs, String dType) {
		logger.debug("updateDepSubs(), updateDepSubs ->" + subs.toString());
		String orgId = getOrgInfo("orgId"), msg = null;

		int cnt = 0;
		// table schema is id, sub_name, sub_owner, type, schid
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
	public List<TeacherSub> getDepTeac(String depName) {
		// retrieve all teacher information for specified department
		String sqlDepTea = "select teachergrade.teac_id as teacherId, teacname as teacherName, teachersub.sub_name"
				+ " as subName from grade join (teachergrade, teacher, teachersub) on (teachergrade.grade_name "
				+ "= grade.grade_name and teacher.teacid = teachergrade.teac_id and teachersub.teac_id = "
				+ "teacher.teacid) where schid = ? and grade_name in (";
		
		if (depName.compareTo("high") == 0) {
			sqlDepTea += "'高一','高二','高三')";
		}
		else if (depName.compareTo("junior") == 0) {
			sqlDepTea += "'九年级','八年级','七年级')";
		} else if (depName.compareTo("primary") == 0) {
			sqlDepTea = "'六年级','五年级','四年级','三年级','二年级','一年级')";
		} else {
			throw new RuntimeException("getDepTeac(), unsupport department type " + depName);
		}
		List<TeacherSub> depTeaL = schS.query(sqlDepTea, new TeacherSubRowMapper(), 
				InfoUtil.retrieveSessAttr("orgId"));
		logger.debug("getDepTeac(), " + depTeaL.toString());
		return depTeaL;
	}

	@Override
	public Grade getGradeInfo(String id) {
		
		Grade info = schM.getGradeInfo(id);
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

	@Override
	public List<TeacherSub> getGradeTeac(String id) {
		String sqlGrTea = "select teacid, teacname from teacher where teacid in (select teac_id "
				+ "from teachergrade join grade on grade.grade_name = teachergrade.grade_name where grade.id = ?)";
		List<TeacherSub> teaL = schS.query(sqlGrTea, new TeacherSubRowMapper(), id);
		return teaL;
	}
	
	@Override
	public ClassInfo getClassInfo(int id) {

		ClassInfo info = schM.getClsInfoById(id);
		
		if (info != null) {
			// need to populate class subjects information
			info.setSubs(getClassSubs(id));
		}
		else {
			logger.error("getClassInfo(), no class found for id->" + id);
		}
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

	private List<SubjectTeac> getClassSubs(int id) {
		
		List<SubjectTeac> stL = schM.getClsSubs(id);
		
		if (stL == null || stL.isEmpty()) {
			logger.warn("getClassSubs(), no subjects set for class ->" + id);
		}
		return stL;
	}
	
	@Override
	public List<Item> getGrClsList(int id) {
		List<Item> clsL = schM.getGradeClass(id);
		return clsL;
	}
	
	@Override
	public List<TeacherSub> getClassTeac(String id) {
		List<TeacherSub> teaL = null;
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

	@Override
	public GradeSubRoles getGrSubRoles(String schid) {
		
		List<Item> roles = schM.getRoles();
		if (roles == null || roles.isEmpty()) {
			logger.error("getGrSubRoles(), fatal errors, no roles found");
			throw new RuntimeException("getGrSubRoles(), fatal errors, no roles found");
		}
		
		// convert role name mappings
		for (Item r: roles) {
			r.setName(InfoUtil.getRolemapping().get(r.getName()));
		}
		
		GradeSubRoles gsr = new GradeSubRoles();
		gsr.setRoles(roles);
		
		List<SchGradeSub> tgL = getGrSubs(schid);	
		gsr.setGrades(tgL);
		
		logger.debug("getGrSubRoles(), GradeSubRoles info populated for school->" + schid);
		return gsr;
	}

	@Override
	public List<SchGradeSub> getGrSubs(String schid) {
		// retrieve grades and related subjects information for school
		List<Item> grades = schM.getGrades(schid);
		if (grades == null || grades.isEmpty()) {
			logger.error("getGrSubs(), no grades found for school->" + schid);
			throw new RuntimeException("getGrSubRoles(), no grades found for school->" + schid);
		}
				
		List<SchGradeSub> tgL = new ArrayList<SchGradeSub>();
				
		// for each grade, populate subjects
		for (Item it: grades) {
			List<Item> gSubs = schM.getGradeSub(it.getId());
			SchGradeSub tg = null;
			if (gSubs == null || gSubs.isEmpty()) {
				logger.warn("getGrSubs(), no subjects set for grade->" + it.getId() + 
						", school->" + schid);
				// no subjects set for this grade, just skip it
				tg = new SchGradeSub(it.getId(), it.getName(), new ArrayList<Item>());
			}
			else
				tg = new SchGradeSub(it.getId(), it.getName(), gSubs);
			
			tgL.add(tg);
		}
		
		logger.debug("getGrSubs(), SchGrSubs->" + schid + "," + tgL.toString());
		return tgL;
	}

}
