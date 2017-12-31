package com.ustudy.exam.controller;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.UResp;
import com.ustudy.exam.model.Teacher;
import com.ustudy.exam.service.ClientService;
import com.ustudy.exam.service.ExamStudentService;
import com.ustudy.exam.service.ExamSubjectService;
import com.ustudy.exam.service.StudentAnswerService;
import com.ustudy.exam.service.StudentPaperService;

import net.sf.json.JSONObject;

@RestController
@RequestMapping(value = "/client")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ClientController {

	private static final Logger logger = LogManager.getLogger(ClientController.class);

	@Autowired
	private ClientService cs;
	
	@Autowired
	private ExamSubjectService ess;
	
	@Autowired
	private StudentAnswerService sas;
	
	@Autowired
	private ExamStudentService ests;
	
	@Autowired
	private StudentPaperService sps;

	/**
	 * 保存模板
	 * @param templates 
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/saveExamTemplate/{egsId}", method = RequestMethod.POST)
	public UResp saveExamTemplate(@PathVariable Long egsId, @RequestBody String parameters, HttpServletRequest request, HttpServletResponse responseonse) {

		logger.info("saveTemplate(egsId: " + egsId + ",parameters: " + parameters + ").");
		
		String token = request.getHeader("token");
		UResp result = cs.login(token);		
		if(!result.isRet()){
			return result;
		}
		
		result = new UResp();
		try {
			result.setRet(cs.saveTemplates(egsId, parameters));
		} catch (Exception e) {
			result.setRet(false);
			result.setMessage(e.getMessage());
			logger.error(e);
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * 获取模板
	 * @param examId 考试ID
	 * @param gradeId 年级ID
	 * @param subjectId 科目ID
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getExamTemplate/{examId}/{gradeId}/{subjectId}", method = RequestMethod.POST)
	public UResp getExamTemplate(@PathVariable Long examId, @PathVariable Long gradeId, @PathVariable Long subjectId, HttpServletRequest request, HttpServletResponse response) {

		logger.info("getTemplates(examId: " + examId + ",gradeId: " + gradeId + ",subjectId: " + subjectId+ ").");

		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if(!result.isRet()){
			return result;
		}

		result = new UResp();

		result.setRet(true);
		result.setData(cs.getTemplateById(examId, gradeId, subjectId));

		return result;
	}
	
	/**
	 * 获取模板
	 * @param egsId 考试、年级、科目
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getExamTemplate/{egsId}", method = RequestMethod.POST)
	public UResp getExamTemplateByEgsId(@PathVariable Long egsId, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("getTemplates(egsId: " + egsId + ").");

		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if(!result.isRet()){
			return result;
		}

		result = new UResp();

		result.setRet(true);
		result.setData(cs.getTemplateById(egsId));

		return result;
	}
	
	/**
	 * 获取考试年级科目
	 * @param EGID 考试ID
	 * @param GDID 年级ID
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getExamSubjects/{examId}/{gradeId}", method = RequestMethod.POST)
	public UResp getExamSubjects(@PathVariable Long examId, @PathVariable Long gradeId, HttpServletRequest request, HttpServletResponse response) {

		logger.info("getSubject(examId: " + examId + ",gradeId: " + gradeId + ").");

		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if(!result.isRet()){
			return result;
		}

		result = new UResp();

		result.setRet(true);
		result.setData(cs.getExamSubjects(examId, gradeId));

		return result;
	}
	
	/**
	 * 获取考试年级科目
	 * @param EGID 考试ID
	 * @param GDID 年级ID
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/exam/subject/status/{examId}/{templateStatus}/{gradeId}/{markingStatus}", method = RequestMethod.GET)
	public UResp getExamSubjectStatus(@PathVariable Long examId, @PathVariable String templateStatus, @PathVariable Long gradeId, @PathVariable String markingStatus, HttpServletRequest request, HttpServletResponse response) {

		logger.info("getSubject(examId: " + examId + ",templateStatus: " + templateStatus + ",gradeId: " + gradeId + ",markingStatus: " + markingStatus + ").");

		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if(!result.isRet()){
			return result;
		}

		result = new UResp();
		result.setRet(true);
		result.setData(cs.getExamSubjectStatus(examId, templateStatus, gradeId, markingStatus));

		return result;
	}
	
	/**
	 * 获取考试年级
	 * @param examId 考试ID
	 * @param examStatus 可进行扫描的考试状态
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getExamGrades/{examId}/{examStatus}", method = RequestMethod.POST)
	public UResp getExamGrades(@PathVariable Long examId, @PathVariable String examStatus, HttpServletRequest request, HttpServletResponse response) {

		logger.info("getSubject(examId: " + examId + ",examStatus: " + examStatus + ").");

		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if(!result.isRet()){
			return result;
		}

		result = new UResp();
		result.setRet(true);
		result.setData(cs.getExamGrades(examId, examStatus));

		return result;
	}
	
	/**
	 * 获取可扫描考试列表
	 * @param MarkingStatus 考试状态 允许进行扫描的考试状态
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getExams/{examStatus}", method = RequestMethod.POST)
	public UResp getExams(@PathVariable String examStatus, HttpServletRequest request, HttpServletResponse response) {

		logger.info("getExams(examStatus: " + examStatus + ").");

		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if(!result.isRet()){
			return result;
		}

		result = new UResp();
		result.setRet(true);
		result.setData(cs.getExams(examStatus));

		return result;
	}
	
	/**
	 * 获取权限接口
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getPermissions", method = RequestMethod.POST)
	public UResp getPermissions(HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("getPermissionList().");

		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if (!result.isRet()) {
			return result;
		} else {
			Teacher teacher = (Teacher)result.getData();
			String[] role = new String[1];
			role[0] = teacher.getRole();
			result.setData(role);
			return result;
		}
		
	}
	
	/**
	 * 更新接口
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public UResp login(HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("login().");
		String token = request.getHeader("token");
		
		return cs.login(token);
	}
	
	/**
	 * 更新接口
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public UResp update(HttpServletRequest request, HttpServletResponse responseonse) {
		
		String currentVersionNo = request.getParameter("currentVersionNo");
		
		logger.info("update(currentVersionNo: " + currentVersionNo + ").");

		boolean beAvailableUpdates = false;
		String ip = "127.0.0.1";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String downLoadPath = request.getRequestURL().toString().replace("localhost", ip);
		
		File clientPath = new File(request.getSession().getServletContext().getRealPath("client"));
		
		if (clientPath.exists() && clientPath.isDirectory()) {
			File[] clients = clientPath.listFiles();
			for (File client : clients) {
				if (client.isFile()) {
					String name = client.getName();
					if (name.lastIndexOf(".")>0) {
						name = name.substring(0, name.lastIndexOf("."));
					}
					
					if (!currentVersionNo.equals(name)) {
						beAvailableUpdates = true;
						downLoadPath = downLoadPath.replace("update", "");
						downLoadPath += client.getName();
					}
				}
			}
		}
		
		Map data = new HashMap<>();
		data.put("BeAvailableUpdates", beAvailableUpdates);
		data.put("DownLoadPath", downLoadPath);
		
		UResp result = new UResp();
		result.setRet(true);
		result.setData(data);
		
		return result;
	}
	
	/**
	 * 保存空白答题卡数据
	 * @param parameters 参数
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/save/answerPaper", method = RequestMethod.POST)
	public UResp saveBlankAnswerPaper(@RequestBody String parameters, HttpServletRequest request, HttpServletResponse responseonse) {
		
		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if(!result.isRet()){
			return result;
		}
		
		JSONObject data  = JSONObject.fromObject(parameters);

		result = new UResp();		
		if(ess.saveBlankAnswerPaper(data.getLong("id"), data.getString("fileName"))){
			result.setRet(true);
		} else {
			result.setRet(false);
		}
		
		return result;
		
	}
	
	/**
	 * 保存空白试卷数据
	 * @param parameters 参数
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/save/questionsPaper", method = RequestMethod.POST)
	public UResp saveBlankQuestionsPaper(@RequestBody String parameters, HttpServletRequest request, HttpServletResponse responseonse) {
		
		String token = request.getHeader("token");
		UResp result = cs.login(token);		
		if(!result.isRet()){
			return result;
		}
		
		result = new UResp();		
		JSONObject data  = JSONObject.fromObject(parameters);
		if(ess.saveBlankQuestionsPaper(data.getLong("id"), data.getString("fileName"))){
			result.setRet(true);
		} else {
			result.setRet(false);
		}
		
		return result;
		
	}
	
	/**
	 * 保存空白答题卡
	 * @param parameters 参数
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/save/stuAnswer", method = RequestMethod.POST)
	public UResp saveStudentAnswer(@RequestBody String parameters, HttpServletRequest request, HttpServletResponse responseonse) {
		
		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if(!result.isRet()){
			return result;
		}

		result = new UResp();
		
		JSONObject data  = JSONObject.fromObject(parameters);
		if(ess.saveBlankAnswerPaper(data.getLong("id"), data.getString("fileName"))){
			result.setRet(true);
		} else {
			result.setRet(false);
		}
		
		return result;
		
	}
	
	/**
	 * 根据考试、年级信息获取学生信息
	 * @param examId
	 * @param gradeId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getStudentsInfo/{examId}/{gradeId}", method = RequestMethod.POST)
	public UResp getStudentsInfo(@PathVariable Long examId, @PathVariable Long gradeId, HttpServletRequest request, HttpServletResponse responseonse) {
		
		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if(!result.isRet()){
			return result;
		}
		
		result = new UResp();
		result.setRet(true);
		result.setData(ests.getStudentInfoByExamGrade(examId, gradeId));
		
		return result;
		
	}
	
	/**
	 * 保存学生考试信息
	 * @param examId
	 * @param egsId
	 * @param parameters
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/save/answers/{examId}/{egsId}", method = RequestMethod.POST)
	public UResp saveStudentsAnswers(@PathVariable Long examId, @PathVariable Long egsId, @RequestBody String parameters, HttpServletRequest request, HttpServletResponse responseonse) {
		
		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if(!result.isRet()){
			return result;
		}
		
		JSONObject data  = JSONObject.fromObject(parameters);
		
		result = new UResp();
		result.setRet(sas.saveStudentsAnswers(examId, egsId, data));
		
		return result;
		
	}
	
	/**
	 * 获取所有考卷名称
	 * @param egsId
	 * @param request
	 * @param responseonse
	 * @return
	 */
	@RequestMapping(value = "/exam/papers/{egsId}", method = RequestMethod.GET)
	public UResp getStudentPapers(@PathVariable Long egsId, HttpServletRequest request, HttpServletResponse responseonse) {
		
		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if(!result.isRet()){
			return result;
		}
		
		result = new UResp();
		result.setRet(true);
		result.setData(sps.getStudentPapers(egsId));
		
		return result;
		
	}
	
	@RequestMapping(value = "/delete/papers/{egsId}/{batchNum}", method = RequestMethod.DELETE)
	public UResp deleteStudentsPapers(@PathVariable Long egsId, @PathVariable Integer batchNum, HttpServletRequest request, HttpServletResponse responseonse) {
		
		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if(!result.isRet()){
			return result;
		}
		
		result = new UResp();
		result.setRet(sas.deletePapers(egsId, batchNum));
		
		return result;
		
	}
	
	@RequestMapping(value = "/log", method = RequestMethod.POST)
	public UResp addLog(@RequestBody String logs, HttpServletRequest request, HttpServletResponse responseonse) {
		
		UResp result = new UResp();		
		result.setRet(false);
		try {
			if(cs.addLog(request, logs)){
				result.setRet(true);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/log", method = RequestMethod.GET)
	public UResp getLog(HttpServletRequest request, HttpServletResponse responseonse) {
		
		UResp result = new UResp();
		result.setRet(false);
		
		try {
			List<String> logFiles = cs.getLog(request);
			result.setRet(true);
			result.setData(logFiles);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/log", method = RequestMethod.DELETE)
	public UResp deleteLog(HttpServletRequest request, HttpServletResponse responseonse) {
		
		UResp result = new UResp();
		result.setRet(false);
		try {
			if(cs.deleteLog(request)){
				result.setRet(true);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setMessage(e.getMessage());
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/question/type", method = RequestMethod.GET)
	public UResp getQuestionType(HttpServletRequest request, HttpServletResponse responseonse) {
		
		String token = request.getHeader("token");
		UResp result = cs.login(token);
		if(!result.isRet()){
			return result;
		}
		
		result = new UResp();
		result.setRet(true);
		result.setData(cs.getQuestionType());
		
		return result;
		
	}
	
}
