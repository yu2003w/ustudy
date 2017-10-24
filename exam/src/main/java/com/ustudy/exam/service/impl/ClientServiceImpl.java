package com.ustudy.exam.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ClientDao;
import com.ustudy.exam.dao.ExamDao;
import com.ustudy.exam.dao.ExamGradeDao;
import com.ustudy.exam.dao.ExamSubjectDao;
import com.ustudy.exam.dao.QuesareaDao;
import com.ustudy.exam.dao.SchoolDao;
import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamGrade;
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.model.Quesarea;
import com.ustudy.exam.model.School;
import com.ustudy.exam.model.Teacher;
import com.ustudy.exam.service.ClientService;
import com.ustudy.exam.service.TeacherService;
import com.ustudy.exam.utility.Base64Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ClientServiceImpl implements ClientService {
	
	private static final Logger logger = LogManager.getLogger(ClientServiceImpl.class);

	@Resource
	private ClientDao clientDaoImpl;
	
	@Resource
	private ExamDao examDaoImpl;
	
	@Resource
	private ExamSubjectDao examSubjectDaoImpl;
	
	@Resource
	private ExamGradeDao examGradeDaoImpl;
	
	@Resource
	private SchoolDao schoolDaoImpl;
	
	@Resource
	private QuesareaDao quesareaDaoImpl;
	
	@Autowired
	private TeacherService teacherService;
	
	public Map<String, Object> login(String token){
		
		Map<String, Object> result = new HashMap<>();
		
		token = Base64Util.encode(token);
		
		String[] tokens = token.split(":");
		
		if(tokens.length == 2){
			String username = tokens[0];
			String password = tokens[1];
			
			String msg = null;
			boolean status = true;
			Teacher teacher = null;
			
			Subject currentUser = SecurityUtils.getSubject();
			
			if (!currentUser.isAuthenticated()) {

				logger.debug("login() is invoked with username->" + username + ";password->" + password);

				try {
					UsernamePasswordToken usertoken = new UsernamePasswordToken(username, password);
					usertoken.setRememberMe(true);
					currentUser.login(usertoken);
					logger.debug("Token retrieved -> " + token.toString());
				} catch (UnknownAccountException | IncorrectCredentialsException uae) {
					logger.debug(uae.getMessage());
					msg = "Attempt to access with invalid account -> username:" + username;
					status = false;
				} catch (LockedAccountException lae) {
					msg = "Account is locked, username:" + username;
					status = false;
				} catch (AuthenticationException ae) {
					msg = ae.getMessage();
					status = false;
				} catch (Exception e) {
					msg = e.getMessage();
					status = false;
				}

			}
			
			result.put("success", status);
			if (status) {
				logger.debug("user [" + currentUser.getPrincipal() + "] logged in successfully");
				
				try {
					teacher = teacherService.findUserById(currentUser.getPrincipal().toString());
					School schoole = schoolDaoImpl.getSchoolById(teacher.getOrgid());
					if(null != schoole){
						teacher.setOrgname(schoole.getSchname());
					}
					teacher.setRoles(teacherService.getRolesById(teacher.getUid()));
					logger.debug("login()," + teacher.toString());
				} catch (Exception e) {
					logger.warn("login(), session failed -> " + e.getMessage());
					logger.debug(e.getMessage());
				}
				
				result.put("teacher", teacher);
			} else {
				result.put("message", msg);
			}
			
			
		}else {
			result.put("success", false);
			result.put("message", "Token is wrong .");
		}
		
		return result;
	}
	
	public boolean saveTemplates(String id, JSONObject originalData) {
		
		logger.debug("originalData: " + originalData);
		examSubjectDaoImpl.saveOriginalData(id, originalData.toString());
		
		JSONArray pages = originalData.getJSONArray("Page");
		for (int i=0;i< pages.size();i++) {
			JSONObject page = pages.getJSONObject(i);
			JSONArray subjectives = page.getJSONArray("OmrSubjectiveList");
			for (int j=0;j< subjectives.size();j++) {
				JSONObject subjective = subjectives.getJSONObject(j);
				JSONArray regions = subjective.getJSONArray("regionList");
				for (int k=0;k< regions.size();k++) {
					JSONObject region = regions.getJSONObject(j);
					quesareaDaoImpl.insertQuesarea(new Quesarea(page.getInt("pageIndex"), page.getString("fileName"), subjective.getInt("AreaID"), region.getInt("x"), region.getInt("y"), region.getInt("width"), region.getInt("height"), region.getInt("bottom"), region.getInt("right"), subjective.getString("TopicType"), subjective.getInt("StartQid"), subjective.getInt("EndQid")));
				}
			}
		}
		
		
		return true;
	}

	public Map<String, String> getTemplateById(String examId, String gradeId, String subjectId){
		
		logger.debug("examId: " + examId + ",gradeId: " + gradeId + ",subjectId: " + subjectId );
		Map<String, String> result = new HashMap<>();
		
		result.put("ExamQAPicPath", "标准答案图片名称,多个用‘,’分隔");
		result.put("ExamPaperPicPath", "试卷原卷图片名称，多个用‘,’分隔");
		result.put("AnswerSheetPicPath", "试卷答题卡原图 ,多张图片用 ',' 分隔");
		result.put("AnswerSheetXMLPath", "试卷答题卡xml模版地址");
		result.put("AnswerSheetXML", "试卷答题卡xml模版,json格式存储");
		result.put("Id", "模板保存的数据库主键ID");
		
		return result;
	}
	
	public Map<String, String> getTemplateById(String csId){
		
		logger.debug("csId: " + csId);
		Map<String, String> result = new HashMap<>();
		
		result.put("ExamQAPicPath", "标准答案图片名称,多个用‘,’分隔");
		result.put("ExamPaperPicPath", "试卷原卷图片名称，多个用‘,’分隔");
		result.put("AnswerSheetPicPath", "试卷答题卡原图 ,多张图片用 ',' 分隔");
		result.put("AnswerSheetXMLPath", "试卷答题卡xml模版地址");
		result.put("AnswerSheetXML", "试卷答题卡xml模版,json格式存储");
		result.put("Id", "模板保存的数据库主键ID");
		
		return result;
	}

	public List<ExamSubject> getExamSubjects(String examId, String gradeId){
		
		logger.debug("examId: " + examId + ",gradeId: " + gradeId);
		List<ExamSubject> result = examSubjectDaoImpl.getAllExamSubjectByExamIdAndGradeId(examId, gradeId);
		
		return result;
	}
	
	public List<ExamGrade> getExamGrades(String examId, String examStatus){
		
		logger.debug("examId: " + examId + ",examStatus: " + examStatus);
		
		List<ExamGrade> examGrades = examGradeDaoImpl.getExamGrades(examId, examStatus);
		
		return examGrades;
	}
	
	public Map<String, List<Exam>> getExams(String examStatus){
		
		logger.debug("examStatus: " + examStatus);
		
		Map<String, List<Exam>> examGradeResponse = new HashMap<>();
		
		List<Exam> list = examDaoImpl.getExamsByStatus(examStatus);
		
		examGradeResponse.put("ExamGradeResponse", list);
		
		return examGradeResponse;
		
	}
	
}
