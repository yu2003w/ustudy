package com.ustudy.exam.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.ustudy.exam.dao.GradeDao;
import com.ustudy.exam.dao.QuesareaDao;
import com.ustudy.exam.dao.SchoolDao;
import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamGrade;
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.model.Grade;
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
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
	private GradeDao gradeDaoImpl;
	
	@Resource
	private QuesareaDao quesareaDaoImpl;
	
	@Autowired
	private TeacherService teacherService;
	
	public Map<String, Object> login(String token){
		
		Map<String, Object> result = new HashMap<>();
		
		if(null != token && token.trim().length()>0){
			token = Base64Util.encode(token);			
		}else {
			result.put("success", false);
			result.put("message", "用户名、密码未提供");
			return result;
		}
		
		String[] tokens = token.split(":");
		
		if(tokens.length == 2){
			String username = tokens[0];
			String password = tokens[1];
			
			String msg = null;
			boolean status = false;
			Teacher teacher = null;
			
			Subject currentUser = SecurityUtils.getSubject();
			
			if (!currentUser.isAuthenticated()) {

				logger.debug("login() is invoked with username->" + username + ";password->" + password);

				try {
					UsernamePasswordToken usertoken = new UsernamePasswordToken(username, password);
					usertoken.setRememberMe(true);
					currentUser.login(usertoken);
					status = true;
					logger.debug("Token retrieved -> " + token.toString());
				} catch (UnknownAccountException | IncorrectCredentialsException e) {
					logger.error(e.getMessage());
					logger.error("Attempt to access with invalid account -> username:" + username);
					msg = "用户名或密码有误";
				} catch (LockedAccountException e) {
					logger.error(e.getMessage());
					logger.error("Account is locked, username:" + username);
					msg = "账号被锁定";
				} catch (AuthenticationException e) {
					logger.error(e.getMessage());
					msg = "账号被锁定";
				} catch (Exception e) {
					logger.error(e.getMessage());
					msg = "账号认证失败";
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
					teacher.setRole(teacherService.findPriRoleById(teacher.getUid()));
					logger.debug("login()," + teacher.toString());
					
					result.put("teacher", teacher);
				} catch (Exception e) {
					logger.warn("login(), session failed -> " + e.getMessage());
					result.put("success", status);
					result.put("message", "用户信息有误");
				}
			} else {
				result.put("message", msg);
			}
			
			
		}else {
			result.put("success", false);
			result.put("message", "用户名、密码有误");
		}
		
		return result;
	}
	
	public boolean saveTemplates(Long id, JSONObject originalData) {
		
		logger.debug("originalData: " + originalData);
		String xmlServerPath = "";
		if(null != originalData.get("xmlServerPath")){
			xmlServerPath = originalData.getString("xmlServerPath");
		}
		examSubjectDaoImpl.saveOriginalData(id, xmlServerPath, originalData.toString());
		
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

	public Map<String, String> getTemplateById(Long examId, Long gradeId, Long subjectId){
		
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
	
	public Map<String, String> getTemplateById(Long csId){
		
		logger.debug("csId: " + csId);
		Map<String, String> result = new HashMap<>();
		
		ExamSubject examSubject = examSubjectDaoImpl.getExamSubjectById(csId);
		
		if(null != examSubject){
			result.put("ExamQAPicPath", "");
			result.put("ExamPaperPicPath", examSubject.getBlankQuestionsPaper());
			result.put("AnswerSheetPicPath", examSubject.getBlankAnswerPaper());
			result.put("AnswerSheetXMLPath", examSubject.getXmlServerPath());
			result.put("AnswerSheetXML", examSubject.getOriginalData());
			result.put("Id", "" + examSubject.getId());
		}
		
		return result;
	}

	public List<ExamSubject> getExamSubjects(Long examId, Long gradeId){
		
		logger.debug("examId: " + examId + ",gradeId: " + gradeId);
		List<ExamSubject> result = examSubjectDaoImpl.getAllExamSubjectByExamIdAndGradeId(examId, gradeId);
		
		return result;
	}

	public JSONArray getExamSubjectStatus(Long examId, String templateStatus, Integer gradeCode, String markingStatus){
		
		JSONArray resault = new JSONArray();
		
		logger.debug("examId: " + examId + ",templateStatus: " + templateStatus + ",gradeCode: " + gradeCode + ",markingStatus: " + markingStatus);
		String gradeName = getGradeName(gradeCode);
		
		List<ExamSubject> examSubjects = examSubjectDaoImpl.getExamSubjectStatus(examId, templateStatus, gradeName, markingStatus);
		Exam exam = examDaoImpl.getExamsById(examId);
		List<School> schools = schoolDaoImpl.getSchoolsByExamId(examId);
		
		if(null != examSubjects && examSubjects.size()>0 && null != exam && null != schools && schools.size()>0){
			School school = schools.get(0);
			String sType = school.getType();
			
			for (ExamSubject examSubject : examSubjects) {
				JSONObject object = new JSONObject();
				
				object.put("id", examSubject.getId());
				object.put("csid", examSubject.getId());
				object.put("egid", examId);
				object.put("sType", sType);
				object.put("gradeCode", examSubject.getGradeId());
				
				Grade grade = gradeDaoImpl.getGradeById(examSubject.getGradeId());
				object.put("classNum", grade.getClassesNum());
				object.put("gradeName", grade.getGradeName());
				
				object.put("subjectID", examSubject.getSubId());
				object.put("subjectName", examSubject.getSubName());
				
				object.put("examName", exam.getExamName());
				
				object.put("templatStatus", examSubject.getTemplate());
				object.put("answerScoreStatus", examSubject.getAnswerSeted());
				object.put("teachTaskStatus", examSubject.getTaskDispatch());
				object.put("tcaStatus", examSubject.getExamAnswer());
				object.put("uploadBathCount", examSubject.getUploadBathCount());
				
				JSONObject examGroup = new JSONObject();
				examGroup.put("id", exam.getId());
				examGroup.put("egid", exam.getId());
				examGroup.put("status", exam.getStatus());
				examGroup.put("examName", exam.getExamName());				
				object.put("exam_group", examGroup);
				
				resault.add(object);
			}
		}
		
		return resault;
	}
	
	public List<ExamGrade> getExamGrades(Long examId, String examStatus){
		
		logger.debug("examId: " + examId + ",examStatus: " + examStatus);
		
		List<ExamGrade> examGrades = examGradeDaoImpl.getExamGrades(examId, examStatus);
		
		return examGrades;
	}
	
	public List<Exam> getExams(String examStatus){
		
		logger.debug("examStatus: " + examStatus);
		
		return examDaoImpl.getExamsByStatus(examStatus);
		
	}
	
	private String getGradeName(Integer gradeCode){
		String gradeName = "";
		switch (gradeCode) {
		case 1:
			gradeName = "一年级";
			break;
		case 2:
			gradeName = "二年级";
			break;
		case 3:
			gradeName = "三年级";
			break;
		case 4:
			gradeName = "四年级";
			break;
		case 5:
			gradeName = "五年级";
			break;
		case 6:
			gradeName = "六年级";
			break;
		case 7:
			gradeName = "七年级";
			break;
		case 8:
			gradeName = "八年级";
			break;
		case 9:
			gradeName = "九年级";
			break;
		case 10:
			gradeName = "高一";
			break;
		case 11:
			gradeName = "高二";
			break;
		case 12:
			gradeName = "高三";
			break;
		default:
			break;
		}
		
		return gradeName;
	}

	public boolean addLog(HttpServletRequest request, String logs) throws Exception {
		
		File logPath = new File(request.getSession().getServletContext().getRealPath("client/log/"));
		
		File currentLogFile = null;
		
		if (logPath.exists() && logPath.isDirectory()) {
			File[] logFiles = logPath.listFiles();
			for (File log : logFiles) {
				if (log.isFile() && log.length() <= 1024*1024*10) {
					currentLogFile = log;
				}
			}
		}
		
		if(null == currentLogFile){
			currentLogFile = new File(logPath, "log_" + System.currentTimeMillis() + ".log"); 
		}
		
		FileWriter writer = new FileWriter(currentLogFile, true);
		writer.write(SDF.format(new Date()) + "  " + logs + "\n");
		writer.close();
		
		return true;
	}

	public List<String> getLog(HttpServletRequest request) throws Exception{
		
		List<String> logFiles = new ArrayList<>();
		
		String ip = "127.0.0.1";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String requestURL = request.getRequestURL().toString().replace("localhost", ip);
		if(!requestURL.endsWith("/")){
			requestURL += "/";
		}
		
		File logPath = new File(request.getSession().getServletContext().getRealPath("client/log/"));
		
		if (logPath.exists() && logPath.isDirectory()) {
			File[] logs = logPath.listFiles();
			for (File log : logs) {
				if (log.isFile()) {
					logFiles.add(requestURL + log.getName());
				}
			}
		}
		
		return logFiles;
	}

	public boolean deleteLog(HttpServletRequest request) throws Exception{
		
		File logPath = new File(request.getSession().getServletContext().getRealPath("client/log/"));
		
		if (logPath.exists() && logPath.isDirectory()) {
			File[] logs = logPath.listFiles();
			for (File log : logs) {
				if (log.isFile()) {
					log.delete();
				}
			}
		}
		
		return true;
	}
	
}
