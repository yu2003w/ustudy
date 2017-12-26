package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ClassDao;
import com.ustudy.exam.dao.ExamSubjectDao;
import com.ustudy.exam.dao.GradeDao;
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.SchoolDao;
import com.ustudy.exam.dao.TaskAllocationDao;
import com.ustudy.exam.dao.TeacherDao;
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.model.Grade;
import com.ustudy.exam.model.QuesAnswer;
import com.ustudy.exam.model.School;
import com.ustudy.exam.service.TaskAllocationService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@SuppressWarnings("rawtypes")
public class TaskAllocationServiceImpl implements TaskAllocationService {

	private static final Logger logger = LogManager.getLogger(TaskAllocationServiceImpl.class);

	@Resource
	private QuesAnswerDao quesAnswerDaoImpl;

	@Resource
	private ExamSubjectDao examSubjectDaoImpl;

	@Resource
	private TaskAllocationDao taskAllocationDaoImpl;

	@Resource
	private TeacherDao teacherDaoImpl;
	
	@Resource
	private SchoolDao schoolDaoImpl;
	
	@Resource
	private GradeDao gradeDaoImpl;
	
	@Resource
	private ClassDao classDaoImpl;

	public JSONArray getQuestions(Long egsId) throws Exception {
		logger.info("getQuestions -> egsId=" + egsId);
		JSONArray array = new JSONArray();
		List<QuesAnswer> quesAnswers = quesAnswerDaoImpl.getQuesAnswers(egsId);
		for (QuesAnswer quesAnswer : quesAnswers) {
			JSONObject object = new JSONObject();
			object.put("id", quesAnswer.getId());
			if (quesAnswer.getStartno() == quesAnswer.getEndno()) {
				object.put("questionName", "" + quesAnswer.getStartno());
			} else {
				object.put("questionName", quesAnswer.getStartno() + "-" + quesAnswer.getEndno());
			}
			object.put("type", quesAnswer.getType());

			array.add(object);
		}
		return array;
	}

	public JSONArray getQuestions(Long examId, Long gradeId, Long subjectId) throws Exception {
		logger.info("getQuestions -> examId=" + examId + ",gradeId=" + gradeId + ",subjectId=" + subjectId);
		JSONArray array = new JSONArray();
		List<ExamSubject> examSubjects = examSubjectDaoImpl.getExamSubjectByExamIdAndGradeIdAndSubjectId(examId, gradeId, subjectId);
		if (null != examSubjects && examSubjects.size() > 0) {
			Long egsId = examSubjects.get(0).getId();
			array = getQuestions(egsId);
		}

		return array;
	}

	public JSONObject getSchool(String schoolId) throws Exception {
		logger.info("getSchools -> schoolId=" + schoolId);

		JSONObject object = new JSONObject();

		School school = schoolDaoImpl.getSchoolById(schoolId);
		if (null != school) {
			object.put("id", school.getId());
			object.put("schoolName", school.getSchname());
			object.put("schoolId", school.getSchid());
			object.put("schoolType", school.getType());
			object.put("province", school.getProvince());
			object.put("city", school.getCity());
			object.put("district", school.getDistrict());
			object.put("schoolOwner", teacherDaoImpl.getTeacherBySchoolIdType(schoolId, "org_owner"));
			object.put("examOwner", teacherDaoImpl.getTeacherBySchoolIdType(schoolId, "leader"));

			Map<String, List<Grade>> gradeMap = getGreadsBySchoolId(schoolId);
			
			JSONArray departments = new JSONArray();
			if(null != gradeMap && gradeMap.size()>0){
				for (String dn : gradeMap.keySet()) {
					JSONObject department = new JSONObject();
					department.put("departmentName", dn);
					
					JSONArray grades = new JSONArray();
					
					List<Grade> gradeList = gradeMap.get(dn);
					if(null != gradeList && gradeList.size()>0){
						for (Grade g : gradeList) {
							Map<String, Map> teacherMap = getTeachersBySchoolId(schoolId);
							Map<Long, List<Map>> gradesubMap = getGreadsubsBySchoolId(schoolId);
							Map<Long, List<com.ustudy.exam.model.Class>> classMap = getClassBySchoolId(schoolId);
							
							JSONObject grade = new JSONObject();
							grade.put("id", g.getId());
							grade.put("gradeName", g.getGradeName());
							grade.put("gradeType", true);
							grade.put("gradeOwner", teacherMap.get(g.getGradeOwner()));
							grade.put("classNum", g.getClassesNum());
							
							JSONArray subjects = new JSONArray();					
							List<Map> gradesubs = gradesubMap.get(g.getId());
							if(null != gradesubs && gradesubs.size()>0){
								for(Map gradesub : gradesubs){
									JSONObject subject = new JSONObject();
									Object name = gradesub.get("name");
									if(null != name){
										subject.put("subject", name.toString());
									}
									Object owner = gradesub.get("owner");
									if(null != owner){
										subject.put("teacher", teacherMap.get(owner.toString()));
									}
									
									subjects.add(subject);
								}
							}
							grade.put("subjects", subjects);
							
							JSONArray classes = new JSONArray();
							List<com.ustudy.exam.model.Class> gradeClassMap = classMap.get(g.getId());
							if(null != gradeClassMap && gradeClassMap.size()>0){
								Map<Long, List<Map>> classubMap = getClassubBySchoolId(schoolId);
								for(com.ustudy.exam.model.Class gradeClass : gradeClassMap){
									JSONObject classe = new JSONObject();
									classe.put("id", gradeClass.getId());
									classe.put("className", gradeClass.getClsName());
									classe.put("classType", gradeClass.getClsType());
									classe.put("classOwner", teacherMap.get(gradeClass.getClsOwner().toString()));
									
									JSONArray subjects_ = new JSONArray();
									List<Map> classubs = classubMap.get(gradeClass.getId());
									if(null != classubs && classubs.size()>0){
										for(Map sub : classubs){
											JSONObject subject_ = new JSONObject();
											Object name = sub.get("name");
											if(null != name){
												subject_.put("subject", name.toString());
											}
											Object owner = sub.get("owner");
											if(null != owner){
												subject_.put("teacher", teacherMap.get(owner.toString()));
											}
											
											subjects_.add(subject_);
										}
									}
									classe.put("subjects", subjects_);                            
									
									classes.add(classe);
								}
							}
							grade.put("classes", classes);
							
							grades.add(grade);
						}
					}
					department.put("grades", grades);
					
					departments.add(department);
				}
			}

			object.put("departments", departments);
		}

		return object;
	}

	public JSONObject getGrade(Long gradeId) throws Exception {
		logger.info("getGrade -> gradeId=" + gradeId);
		JSONObject object = new JSONObject();
		
		Grade grade = gradeDaoImpl.getGradeById(gradeId);
		if (null != grade) {
			object.put("id", grade.getId());
			object.put("gradeName", grade.getGradeName());
			object.put("gradeType", true);
			object.put("classNum", grade.getClassesNum());
			
			List<Map> teachers = teacherDaoImpl.getTeachersBySchoolInGradeName(grade.getSchid(), grade.getGradeName());
			Map<String, Map> teacherMap = getTeachersBySchoolInGradeName(teachers);
			object.put("gradeOwner", teacherMap.get(grade.getGradeOwner()));
			
			JSONArray subjects = new JSONArray();
			List<Map>  gradeSubjects = gradeDaoImpl.getGradesubByGradeId(gradeId);
			if(null != gradeSubjects && gradeSubjects.size()>0){
				for(Map gradesub : gradeSubjects){
					JSONObject subject = new JSONObject();
					Object name = gradesub.get("name");
					if(null != name){
						subject.put("subject", name.toString());
					}
					Object owner = gradesub.get("owner");
					if(null != owner){
						subject.put("teacher", teacherMap.get(owner.toString()));
					}
					
					subjects.add(subject);
				}
			}
			object.put("subjects", subjects);
			
			JSONArray classes = new JSONArray();
			List<com.ustudy.exam.model.Class>  gradeClasses = classDaoImpl.getClassByGradeId(gradeId);
			if(null != gradeClasses && gradeClasses.size()>0){
				Map<Long, List<Map>> gradeClassubs = getClassubByGradeId(gradeId);
				for (com.ustudy.exam.model.Class cla : gradeClasses) {
					JSONObject claObject = new JSONObject();
					claObject.put("id", cla.getId());
					claObject.put("className", cla.getClsName());
					claObject.put("classType", cla.getClsType());
					claObject.put("classOwner", teacherMap.get(cla.getClsOwner()));
					
					JSONArray classSub = new JSONArray();
					List<Map> classSubs = gradeClassubs.get(cla.getId());
					if(null != classSubs && classSubs.size()>0){
						for (Map classsub : classSubs) {
							JSONObject subject = new JSONObject();
							
							Object name = classsub.get("name");
							if(null != name){
								subject.put("subject", name.toString());
							}
							Object owner = classsub.get("owner");
							if(null != owner){
								subject.put("teacher", teacherMap.get(owner.toString()));
							}
							
							classSub.add(subject);
						}
					}
					
					claObject.put("subjects", classSub);					
					classes.add(claObject);
				}
			}
			object.put("classes", classes);
			
			object.put("groups", gradeDaoImpl.getGradeGroupsByGradeId(gradeId));
			object.put("teachers", teachers);
		}
		return object;
	}
	
	private Map<String, Map> getTeachersBySchoolId(String schoolId){
		Map<String, Map> resaltMap = new HashMap<>(); 
		List<Map> teachers = teacherDaoImpl.getTeachersBySchoolId(schoolId);
		for (Map map : teachers) {
			resaltMap.put(map.get("id").toString(), map);
		}
		return resaltMap;
	}
	
	private Map<String, Map> getTeachersBySchoolInGradeName(List<Map> teachers){
		Map<String, Map> resaltMap = new HashMap<>(); 
		if(null != teachers && teachers.size()>0){
			for (Map map : teachers) {
				resaltMap.put(map.get("id").toString(), map);
			}
		}
		return resaltMap;
	}
	
	private Map<String, List<Grade>> getGreadsBySchoolId(String schoolId){
		Map<String, List<Grade>> resaltMap = new HashMap<>(); 
		List<Grade> grades = gradeDaoImpl.getGradesBySchoolId(schoolId);
		for (Grade grade : grades) {
			List<Grade> list = resaltMap.get(grade.getDepartment());
			if(null == list){
				list = new ArrayList<>();
			}
			list.add(grade);
			resaltMap.put(grade.getDepartment(), list);
		}
		return resaltMap;
	}
	
	private Map<Long, List<Map>> getGreadsubsBySchoolId(String schoolId){
		Map<Long, List<Map>> resaltMap = new HashMap<>(); 
		List<Map> gradesubs = gradeDaoImpl.getGradesubBySchoolId(schoolId);
		for (Map grade : gradesubs) {
			List<Map> list = resaltMap.get(Long.valueOf((Integer)grade.get("id")));
			if(null == list){
				list = new ArrayList<>();
			}
			list.add(grade);
			resaltMap.put(Long.valueOf((Integer) grade.get("id")), list);
		}
		return resaltMap;
	}
	
	private Map<Long, List<com.ustudy.exam.model.Class>> getClassBySchoolId(String schoolId){
		Map<Long, List<com.ustudy.exam.model.Class>> resaltMap = new HashMap<>(); 
		List<com.ustudy.exam.model.Class> classes = classDaoImpl.getClassBySchoolId(schoolId);
		for (com.ustudy.exam.model.Class cla : classes) {
			List<com.ustudy.exam.model.Class> list = resaltMap.get(cla.getGradeId());
			if(null == list){
				list = new ArrayList<>();
			}
			list.add(cla);
			resaltMap.put(cla.getGradeId(), list);
		}
		return resaltMap;
	}
	
	private Map<Long, List<Map>> getClassubBySchoolId(String schoolId){
		Map<Long, List<Map>> resaltMap = new HashMap<>(); 
		List<Map> classes = classDaoImpl.getClassubBySchoolId(schoolId);
		for (Map cla : classes) {
			List<Map> list = resaltMap.get(Long.valueOf((Integer) cla.get("id")));
			if(null == list){
				list = new ArrayList<>();
			}
			list.add(cla);
			resaltMap.put(Long.valueOf((Integer) cla.get("id")), list);
		}
		return resaltMap;
	}
	
	private Map<Long, List<Map>> getClassubByGradeId(Long gradeId){
		Map<Long, List<Map>> resaltMap = new HashMap<>(); 
		List<Map> classes = classDaoImpl.getClassubByGradeId(gradeId);
		for (Map cla : classes) {
			List<Map> list = resaltMap.get(Long.valueOf((Integer) cla.get("id")));
			if(null == list){
				list = new ArrayList<>();
			}
			list.add(cla);
			resaltMap.put(Long.valueOf((Integer) cla.get("id")), list);
		}
		return resaltMap;
	}

	public List<Map> getGradeNotaskTeachers(Long gradeId) throws Exception {
		return teacherDaoImpl.getGradeNotaskTeachers(gradeId);
	}

}
