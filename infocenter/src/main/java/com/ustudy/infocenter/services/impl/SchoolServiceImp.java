package com.ustudy.infocenter.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.infocenter.model.ClassInfo;
import com.ustudy.infocenter.model.Department;
import com.ustudy.infocenter.model.Grade;
import com.ustudy.infocenter.model.OwnerBrife;
import com.ustudy.infocenter.model.School;
import com.ustudy.infocenter.model.SubjectOwner;
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
		try {
			orgT = InfoUtil.retrieveSessAttr("orgType");
			orgId = InfoUtil.retrieveSessAttr("orgId");
			if (orgT == null || orgId == null || orgT.isEmpty() || orgId.isEmpty()) {
				logger.warn("getSchool(), it seemed that user did not log in");
				throw new RuntimeException("getSchool(), orgType or orgId is not proper");
			}
				
		} catch (Exception e) {
			String msg = "getSchool(), failed to retrieve proper orgType or orgId";
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
		logger.debug("getSchool(), retrieve information for " + orgT + ":" + orgId);
		
		String sqlSch = "select * from ustudy.school where school_id = ?";
		School item = schS.queryForObject(sqlSch, new SchoolRowMapper(), orgId);
		
		// populate school owner and exam owner
		if (!populateOwner(item, orgT, orgId))
			logger.info("getSchool(), failed to populate school/exam owner, maybe not set yet");
		
		populateSchoolSub(item, orgId);
		
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
	private boolean populateSchoolSub(School item, final String orgId) {
		String sqlT = "select value, teacid, teacname from schoolsub JOIN teacher where "
				+ "schoolsub.teac_id = teacher.teacid and schoolsub.school_id = ?";
		List<SubjectTeac> soL = schS.query(sqlT, new SubjectTeacRowMapper(), orgId);
		ConcurrentHashMap<String, List<TeacherBrife>> ret = new ConcurrentHashMap<String, List<TeacherBrife>>();
		for (SubjectTeac st: soL) {
			if (!ret.contains(st.getSub())) {
				List<TeacherBrife> tL = new ArrayList<TeacherBrife>();
				tL.add(st.getTeac());
				ret.put(st.getSub(), tL);
			}
			else {
				List<TeacherBrife> tL = ret.get(st.getSub());
				tL.add(st.getTeac());
				ret.put(st.getSub(), tL);
			}
		}
		List<SubjectOwner> so = new ArrayList<SubjectOwner>();
		ret.forEach((k, v) -> so.add(new SubjectOwner(k, v)));
		item.setSubOs(so);
		logger.debug("populateSchoolSub(), populate school subject owners successful for school -> " + orgId);
		return true;
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
		if (highL.size() > 0)
			departs.add(new Department("高中部", highL));
		if (junL.size() > 0)
			departs.add(new Department("初中部", highL));
		if (priL.size() > 0)
			departs.add(new Department("小学部", highL));
		if (othL.size() > 0)
			departs.add(new Department("其他", highL));
		
		return true;
	}

}
