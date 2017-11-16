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
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.QuesareaDao;
import com.ustudy.exam.dao.SchoolDao;
import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamGrade;
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.model.Grade;
import com.ustudy.exam.model.QuesAnswer;
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
	
	@Autowired
	private QuesAnswerDao quesAnswerDaoImpl;
	
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
	
	public boolean saveTemplates(Long id, String data) {
		
		JSONObject originalData = JSONObject.fromObject(data);
		logger.debug("originalData: " + originalData);
		String xmlServerPath = "";
		if(null != originalData.get("xmlServerPath")){
			xmlServerPath = originalData.getString("xmlServerPath");
		}
		examSubjectDaoImpl.saveOriginalData(id, xmlServerPath, data.toString());
		Map<String, Long> quesAnswersId = getQuesAnswer(originalData);
		saveQuesareas(quesAnswersId, originalData);
		return true;
	}
	
	private Map<String, Long> getQuesAnswer(JSONObject originalData){
		
		List<QuesAnswer> quesAnswers = new ArrayList<>();
		
		if(null != originalData.get("TemplateInfo") && null != originalData.get("CSID") ){
			Long examGradeSubId = originalData.getLong("CSID");
			JSONObject templateInfo = originalData.getJSONObject("TemplateInfo");
			if(null != templateInfo.get("pages")){
				JSONArray pages = templateInfo.getJSONArray("pages");
				for (int i=0; i<pages.size(); i++) {
					JSONObject page = pages.getJSONObject(i);
					if(null != page.get("OmrSubjectiveList")){
						JSONArray subjectives = page.getJSONArray("OmrSubjectiveList");
						for (int j=0; j<subjectives.size(); j++) {
							JSONObject subjective = subjectives.getJSONObject(j);
							
							String type = null;
							int startno = 1;
							int endno = 1;
							int quesno = 0;
							if(null != subjective.get("TopicType")){
								type = subjective.getString("TopicType");
							}
							if(null != subjective.get("StartQid")){
								startno = subjective.getInt("StartQid");
							}
							if(null != subjective.get("EndQid")){
								endno = subjective.getInt("EndQid");
							}
							if(startno == endno){
								quesno = startno;
							}
							
							quesAnswers.add(new QuesAnswer(quesno, startno, endno, type, examGradeSubId));
						}
					}
					if(null != page.get("OmrObjectives") && !page.get("OmrObjectives").equals(null)){
						JSONArray objectives = page.getJSONArray("OmrObjectives");
						for (int j=0; j<objectives.size(); j++) {
							JSONObject objective = objectives.getJSONObject(j);
							
							String type = null;
							int startno = 1;
							int endno = 0;
							int quesno = 0;
							if(null != objective.get("topicType")){
								type = objective.getString("topicType");
							}
							
							if(null != objective.get("objectiveItems")){
								JSONArray objectiveItems = objective.getJSONArray("objectiveItems");
								for (int k=0; k<objectiveItems.size(); k++) {
									JSONObject item = objectiveItems.getJSONObject(k);
									if(null != item.get("num") && null != item.getJSONObject("num").get("number")){
										int number = item.getJSONObject("num").getInt("number");
										if(number < 1){
											number = 1;
										}
										if(startno > number){
											startno = number;
										}
										if(endno < number){
											endno = number;
										}
									}
								}
							}
							if(startno == endno){
								quesno = startno;
							}
							
							quesAnswers.add(new QuesAnswer(quesno, startno, endno, type, examGradeSubId));
							
						}
					}
				}
			}
		}
		
		quesAnswerDaoImpl.initQuesAnswers(quesAnswers);
		
		return getQuesAnswersId(quesAnswers);
	}
	
	private Map<String, Long> getQuesAnswersId(List<QuesAnswer> quesAnswers){
		Map<String, Long> quesAnswersId = new HashMap<>();
		
		for (QuesAnswer answer : quesAnswers) {
			quesAnswersId.put(answer.getStartno() + "-" + answer.getEndno(), answer.getId());
		}
		
		return quesAnswersId;
	}
	
	private boolean saveQuesareas(Map<String, Long> quesAnswersId, JSONObject originalData){
		if(null != originalData.get("TemplateInfo") && null != originalData.get("CSID") ){
			Long csId = originalData.getLong("CSID");
			JSONObject templateInfo = originalData.getJSONObject("TemplateInfo");
			if(null != templateInfo.get("pages")){
				JSONArray pages = templateInfo.getJSONArray("pages");
				for (int i=0; i<pages.size(); i++) {
					JSONObject page = pages.getJSONObject(i);
					if(null != page.get("OmrSubjectiveList")){
						JSONArray subjectives = page.getJSONArray("OmrSubjectiveList");
						for (int j=0; j<subjectives.size(); j++) {
							JSONObject subjective = subjectives.getJSONObject(j);
							quesareaDaoImpl.insertQuesareas(getQuesareas(quesAnswersId, csId, page, subjective));
						}
					}
				}				
			}
		}
		
		return true;
	}
	
	private List<Quesarea> getQuesareas(Map<String, Long> quesAnswersId, Long csId, JSONObject page, JSONObject subjective){
		
		List<Quesarea> resault = new ArrayList<>();
		
		int pageno = 0;
		String fileName = null;
		if(null != page.get("pageIndex")){
			pageno = page.getInt("pageIndex");
		}
		if(null != page.get("fileName")){
			fileName = page.getString("fileName");
		}
		
		int areaId = 0;
		String questionType = null;
		int startQuestionNo = 1;
		int endQuestionNo = 0;
		if(null != subjective.get("AreaID")){
			areaId = subjective.getInt("AreaID");
		}
		if(null != subjective.get("TopicType")){
			questionType = subjective.getString("TopicType");
		}
		if(null != subjective.get("StartQid")){
			startQuestionNo = subjective.getInt("StartQid");
		}
		if(null != subjective.get("EndQid")){
			endQuestionNo = subjective.getInt("EndQid");
		}
		long quesid = quesAnswersId.get(startQuestionNo + "-" + endQuestionNo);
		
		if(null != subjective.get("regionList")){
			JSONArray regions = subjective.getJSONArray("regionList");								
			for (int i=0;i< regions.size();i++) {
				JSONObject region = regions.getJSONObject(i);
				int posx = 0;
				int posy = 0;
				int width = 0;
				int height = 0;
				int bottom = 0;
				int right = 0;
				
				if(null != region.get("x")){
					posx = region.getInt("x");
				}
				if(null != region.get("y")){
					posy = region.getInt("y");
				}
				if(null != region.get("width")){
					width = region.getInt("width");
				}
				if(null != region.get("height")){
					height = region.getInt("height");
				}
				if(null != region.get("bottom")){
					bottom = region.getInt("bottom");
				}
				if(null != region.get("right")){
					right = region.getInt("right");
				}
				
				resault.add(new Quesarea(pageno, fileName, areaId, posx, posy, width, height, bottom, right, questionType, startQuestionNo, endQuestionNo, csId, quesid));
			}
		}
		
		return resault;
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
			result.put("ExamQAPicPath", examSubject.getBlankAnswerPaper());
			result.put("ExamPaperPicPath", examSubject.getBlankQuestionsPaper());
			result.put("AnswerSheetPicPath", examSubject.getBlankAnswerPaper());
			result.put("AnswerSheetXMLPath", examSubject.getXmlServerPath());
			result.put("AnswerSheetXML", examSubject.getOriginalData());
			result.put("Id", "" + examSubject.getId());
		}
		
		return result;
	}

	public JSONArray getExamSubjects(Long examId, Long gradeId){
		
		JSONArray resault = new JSONArray();
		logger.debug("examId: " + examId + ",gradeId: " + gradeId);
		
		List<ExamSubject> subjects = examSubjectDaoImpl.getAllExamSubjectByExamIdAndGradeId(examId, gradeId);
		if(null != subjects && subjects.size()>0){
			for (ExamSubject subject : subjects) {
				JSONObject object = JSONObject.fromObject(subject);
				object.remove("blankAnswerPaper");
				object.remove("blankQuestionsPaper");
				object.remove("xmlServerPath");
				object.remove("originalData");
				resault.add(object);
			}
		}
		
		return resault;
	}

	public JSONArray getExamSubjectStatus(Long examId, String templateStatus, Long gradeId, String markingStatus){
		
		JSONArray resault = new JSONArray();
		
		logger.debug("examId: " + examId + ",templateStatus: " + templateStatus + ",gradeId: " + gradeId + ",markingStatus: " + markingStatus);
		
		List<ExamSubject> examSubjects = examSubjectDaoImpl.getExamSubjectStatus(examId, templateStatus, gradeId, markingStatus);
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
	
	public JSONArray getQuestionType() {
		
		JSONArray questionType = new JSONArray();
		
		JSONObject object1 = new JSONObject();
		object1.put("code", "单选题");
		object1.put("name", "单选题");
		object1.put("type", 1);
		questionType.add(object1);
		
		JSONObject object2 = new JSONObject();
		object2.put("code", "多选题");
		object2.put("name", "多选题");
		object2.put("type", 1);
		questionType.add(object2);
		
		JSONObject object3 = new JSONObject();
		object3.put("code", "判断题");
		object3.put("name", "判断题");
		object3.put("type", 1);
		questionType.add(object3);
		
		JSONObject object4 = new JSONObject();
		object4.put("code", "填空题");
		object4.put("name", "填空题");
		object4.put("type", 2);
		questionType.add(object4);
		
		JSONObject object5 = new JSONObject();
		object5.put("code", "解答题");
		object5.put("name", "解答题");
		object5.put("type", 2);
		questionType.add(object5);
		
		JSONObject object6 = new JSONObject();
		object6.put("code", "证明题");
		object6.put("name", "证明题");
		object6.put("type", 2);
		questionType.add(object6);
		
		JSONObject object7 = new JSONObject();
		object7.put("code", "作文题");
		object7.put("name", "作文题");
		object7.put("type", 2);
		questionType.add(object7);
		
		JSONObject object8 = new JSONObject();
		object8.put("code", "论述题");
		object8.put("name", "论述题");
		object8.put("type", 2);
		questionType.add(object8);
		
		return questionType;
	}
	
}
