package com.ustudy.dashboard.services.imp;

import java.io.StringReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonReader;

import java.util.Set;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.dashboard.model.Subject;
import com.ustudy.dashboard.model.Teacher;
import com.ustudy.dashboard.services.SchoolService;
import com.ustudy.dashboard.util.DashboardUtil;
import com.ustudy.dashboard.mapper.OrgOwnerMapper;
import com.ustudy.dashboard.mapper.SchoolMapper;
import com.ustudy.dashboard.model.Grade;
import com.ustudy.dashboard.model.OrgBrife;
import com.ustudy.dashboard.model.School;

/**
 * @author jared
 *
 */
@Service
public class SchoolServiceImp implements SchoolService {

	private static final Logger logger = LogManager.getLogger(SchoolServiceImp.class);

	@Autowired
	private SchoolMapper schM;
	
	@Autowired
	private OrgOwnerMapper ooM;
	
	@Override
	public List<School> getSchools(int id) {
		if (id < 0)
			id = 0;
		List<School> schs = schM.getSchools(id);
		logger.debug("Fetched " + schs.size() + " items of school");
		return schs;
	}

	/**
	 * Noted: spring framework will handle runtime exceptions, so don't need to
	 * write try {} catch{} here. Just throw out runtime exceptions.
	 */
	@Transactional
	@Override
	public long createSchool(School data) {

		long ret = schM.createSchool(data);
		if (ret < 0 || ret > 2) {
			logger.error("createSchool(), create school failed with ret " + ret);
			throw new RuntimeException("createSchool(), failed with ret " + ret);
		}
		int numOfGr = saveGrades(data.getGrades(), data.getSchoolId());
		logger.info(numOfGr + " grade items saved into database");
		
		// create cleaner account for this school
		ret = createCleaner(data.getSchoolType(), data.getSchoolId());
		logger.debug("createSchool(), cleaner id for school " + data.getSchoolId() + " " + ret);
		return data.getId();
	}
	
	
	private long createCleaner(String orgType, String orgId) {
		// usually school id is composed of 10 digits number
		String tname = orgId;
		String msg = null;
		
		if (tname.length() > 11) {
			logger.error("createCleaner(), length of school id exceeds 11 characters, create cleaner failed.");
			throw new RuntimeException("length of school id exceeds 11 characters, create cleaner failed");
		}
		
		logger.debug("createCleaner(), create cleaner for school " + orgId);
		Teacher item = new Teacher(tname, tname, "学校", orgId);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (item.getPasswd() != null) {
				md.update(item.getPasswd().getBytes(), 0, item.getPasswd().length());
			} else {
				/*
				// if no passwd set for teacher, passwd should be last 6 characters in teacId
				if (item.getTeacId() == null || item.getTeacId().length() < 6) {
					logger.error("createCleaner(), teacher id contains less than 6 characters, failed to "
							+ "populate password");
					throw new RuntimeException("createTeacher(), failed to set password.");
				}
				*/
				// set default password
				String pw = "ustudy123";
				md.update(pw.getBytes(), 0, pw.length());
			}

			item.setPasswd(String.format("%032x", new BigInteger(1, md.digest())));
		} catch (NoSuchAlgorithmException ne) {
			msg = "createCleaner(), failed to initialize MD5 algorithm.";
			logger.warn(msg);
			throw new RuntimeException(msg);
		}
		
		// set creation time
		item.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		int ret = schM.createCleaner(item);
		if (ret < 0 || ret > 2) {
			msg = "createCleaner(), insert failed with ret->" + ret;
			logger.error(msg);
			throw new RuntimeException(msg);
		}

		if (item.getId() < 0) {
			msg = "createCleaner(), invalid id " + item.getId();
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		
		logger.debug("createCleaner(), created ret->" + ret + ", generated id->" + item.getId());
	
		ret = ooM.saveRoles("cleaner", item.getTeacId());
		if (ret <= 0) {
			logger.error("createCleaner(), failed to populate roles for cleaner->" + ret);
			throw new RuntimeException("failed to populate roles for cleaner");
		}

		logger.debug("createCleaner(), role populated for " + item.getTeacId());
	
		return item.getId();
	}

	@Transactional
	@Override
	public void updateSchool(School data, int id) {

		int ret = schM.createSchool(data);
		if (ret < 0 || ret > 2) {
			logger.error("updateSchool(), update school failed with ret " + ret);
			throw new RuntimeException("updateSchool(), failed with ret " + ret);
		}
		
		// For grades, need to compare which should be deleted, 
		// origin grades should not be deleted and "insert into ... on duplicate key update" 
		// delete origin grades may result in grade id changes and related exam information would lost
		List<Grade> oriGr = schM.getGrades(data.getSchoolId());
		HashMap<String, Grade> oGM = new HashMap<String, Grade>();
		for (Grade gr: oriGr) {
			oGM.put(gr.getGradeName(), gr);
		}
		
		List<Grade> grL = data.getGrades();
		List<Grade> fin = new ArrayList<Grade>();
		for (Grade gr: grL) {
			Grade og = oGM.get(gr.getGradeName());
			if (og == null) {
				// newly added grade
				fin.add(gr);
			}
			else {
				fin.add(gr);
				oGM.remove(gr.getGradeName());
			}
		}
		
		if (oGM.size() > 0) {
			logger.debug("updateSchool(), grades needs to be removed->" + oGM.values());
			Set<Entry<String, Grade>> rgr = oGM.entrySet();
			String ids = null;
			boolean first = true;
			
			for (Entry<String, Grade> en: rgr) {
				if (first) {
					ids = String.valueOf(en.getValue().getId());
					first = false;
				}
				else {
					ids += "," + String.valueOf(en.getValue().getId());
				}
			}
			
			if (!ids.isEmpty()) {
				ret = schM.delGrade(ids);
				if (ret < 1) {
					logger.error("updateSchool(), faile to delete grade with ret->" + ret);
					throw new RuntimeException("updateSchool(), faile to delete grade with ret->" + ret);
				}
				logger.debug("updateSchool(), grades to be deleted->" + ids);
			}
		}
		
		logger.debug("updateSchool(), grades needs to added and updated->" + fin.toString());
		if (fin.size() > 0) {
			ret = saveGrades(fin, data.getSchoolId());	
			
			logger.info("updateSchool()," + fin.size() + " grade items saved into database");
		}
		
	}

	@Override
	@Transactional
	public void deleteSchool(int id) {
		int ret = schM.delSchool(String.valueOf(id));
		if (ret != 1) {
			logger.error("deleteSchool(), delete failed with ret->" + ret);
			throw new RuntimeException("deleteSchool(), failed with ret->" + ret);
		}
		
	}

	@Override
	@Transactional
	public int delSchools(String ids) {

		List<String> idsList = DashboardUtil.parseIds(ids);
		int len = idsList.size();
		if (len == 0)
			return 0;

		String sqlDel = null;
		for (int i = 0; i < len; i++) {
			if (i == 0) {
				sqlDel = idsList.get(0);
			} else
				sqlDel += "," + idsList.get(i);
		}
		logger.debug("delSchools(), ids combined for batch deletion --> " + sqlDel);
		return schM.delSchool(sqlDel);
	}

	@Override
	public School displaySchool(int id) {
		School item = schM.getSchoolById(id);
		
		if (item != null)
			assembleGrades(item);

		return item;
	}

	private void assembleGrades(School sch) {
		List<Grade> gNames = schM.getGrades(sch.getSchoolId());
		for (Grade gn : gNames) {
			
			List<Subject> cs = schM.getGradeSub(gn.getId());
			gn.setSubjects(cs);
		}
		sch.setGrades(gNames);
	}

	// Store grades information into database
	private int saveGrades(List<Grade> grades, String schId) {

		String msg = null;
		// grade schema is as below,
		// id, grade_name, classes_num, grade_owner, schid
		// Noted: grade_owner is not used in dashboard, it's used by info center
		HashSet<String> highS = new HashSet<String>();
		HashSet<String> juniorS = new HashSet<String>();
		HashSet<String> priS = new HashSet<String>();
		
		// before processing grade subs, need to subjects dictionary firstly
		// TODO: Before creating school, frontend should retrieve subject list via 
		// api /dashboard/config/sublist/, then put subId in grade array
		List<Subject> subL = schM.getSubs();
		HashMap<String, String> subMap = new HashMap<String, String>();
		for (Subject sub:subL) {
			subMap.put(sub.getSubName(), sub.getSubId());
		}
		
		int ret = -1;
		for (Grade gr : grades) {
			List<Subject> subs = gr.getSubjects();
			HashSet<String> depS = null;
			if (gr.getGradeName().compareTo("高一") == 0 || gr.getGradeName().compareTo("高二") == 0
					|| gr.getGradeName().compareTo("高三") == 0) {
				depS = highS;
			} else if (gr.getGradeName().compareTo("九年级") == 0 || gr.getGradeName().compareTo("八年级") == 0
					|| gr.getGradeName().compareTo("七年级") == 0) {
				depS = juniorS;
			} else if (gr.getGradeName().compareTo("六年级") == 0 || gr.getGradeName().compareTo("五年级") == 0
					|| gr.getGradeName().compareTo("四年级") == 0 || gr.getGradeName().compareTo("三年级") == 0
					|| gr.getGradeName().compareTo("二年级") == 0 || gr.getGradeName().compareTo("一年级") == 0) {
				depS = priS;
			}

			ret = schM.createGrade(gr, schId);
			// insert grade related information into ustudy.grade firstly
			// need to retrieve auto generated key
			if (ret < 0 || ret > 2) {
				msg = "saveGrades(), create grade failed with ret->" + ret;
				logger.error(msg);
				throw new RuntimeException(msg);
			}
			if (gr.getId() < 0) {
				msg = "saveGrades(), invalid grade id->" + gr.getId();
				logger.warn(msg);
				throw new RuntimeException(msg);
			}
			
			// need to update grade related subjects, delete firstly then insert again
			// when update school and update existed grades, need to do delete firstly
			// TODO: DO update instead of delete, insert
			ret = schM.delGradeSubs(gr.getId());
			logger.debug("updateSchool(), clear " + ret + " subjects for grade->" + gr.toString());
			
			for (Subject sub : subs) {
				String subId = subMap.get(sub.getSubName());
				if (subId == null || subId.isEmpty() || Integer.valueOf(subId) < 0) {
					msg = "saveGrades(), subId->" + subId + " invalid for " + sub.getSubName();
					logger.error(msg);
					throw new RuntimeException(msg);
				}
				ret = schM.createGradeSub(subId, gr.getId());
				if (ret < 0 || ret > 2) {
					msg = "saveGrades(), create grade subject failed with ret->" + ret;
					logger.error(msg);
					throw new RuntimeException(msg);
				}
				
				depS.add(sub.getSubName());
			}

			logger.debug("Num of subjects for " + schId + "," + gr.getGradeName() + " is " + subs.size());
			ret = saveClasses(gr);
			logger.debug(schId + " of " + gr.getGradeName() + " has " + ret + " classes");
		}

		// before save depart sub, need to delete firstly for update school and grades
		ret = schM.delDepartSub(schId);
		logger.debug("saveGrades(), clear depart sub with ret->" + ret);
		
		saveDepSub(highS, "high", schId, subMap);
		saveDepSub(juniorS, "junior", schId, subMap);
		saveDepSub(priS, "primary", schId, subMap);
		return grades.size();
	}

	private int saveClasses(Grade g) {
		
		for (int i = 0; i < g.getNum(); i++) {
			String clsN = g.getGradeName() + "（" + String.valueOf(i + 1) + "）班";
			int ret = schM.createClass(g.getId(), clsN);
			if (ret < 0 || ret > 2) {
				String msg = "saveClasses(), create grade subject failed with ret->" + ret;
				logger.error(msg);
				throw new RuntimeException(msg);
			}
		}
		return g.getNum();
	}

	private int saveDepSub(HashSet<String> subS, String type, String schId, HashMap<String, String> subMap) {

		int ret = 0;
		String msg = null;
		
		for (String sub : subS) {
			String subId = subMap.get(sub);
			if (subId == null || subId.isEmpty() || Integer.valueOf(subId) < 0) {
				msg = "saveDepSub(), subId->" + subId + " invalid for " + sub;
				logger.error(msg);
				throw new RuntimeException(msg);
			}
			ret = schM.createDepartSub(subId, type, schId);
			if (ret < 0 || ret > 2) {
				msg = "saveDepSub(), create department subject failed with ret->" + ret;
				logger.error(msg);
				throw new RuntimeException(msg);
			}
		}
		logger.debug("saveDepSub(), number of departent subject records for" + "school " + schId + ":" + type + "->"
				+ subS.size());
		return subS.size();
	}

	@Override
	public List<OrgBrife> getOrgBrifeList(int id) {
		if (id < 0)
			id = 0;
		List<OrgBrife> obL = schM.getSchBrife(id);
		return obL;
	}

	@Override
	public List<Subject> retrieveSubjects() {
		List<Subject> subL = schM.getAllSubjects();
		logger.debug("retrieveSubjects(), " + subL.toString());
		Map<Integer, String> subD = new HashMap<Integer, String>();
		for (Subject sub: subL) {
			if (sub.getChild() == null || sub.getChild().length() == 0) {
				subD.put(Integer.valueOf(sub.getSubId()), sub.getSubName());
			}
		}
		
		for (Subject sub: subL) {
			if (sub.getChild() != null && sub.getChild().length() > 0) {
				// need to parse child subjects
				JsonReader reader = Json.createReader(new StringReader(sub.getChild()));
				JsonArray ids = reader.readArray();
				reader.close();
				TreeMap<Integer, String> childSubs = new TreeMap<Integer, String>();
				for (int i = 0; i < ids.size(); i++) {
					JsonNumber sid = ids.getJsonNumber(i);
					childSubs.put(Integer.valueOf(sid.intValue()), subD.get(sid.intValue()));
				}
				sub.setChildSubs(childSubs);
			}
		}
		return subL;
	}

}
