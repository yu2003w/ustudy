package com.ustudy.exam.controller;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
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

import com.ustudy.exam.model.Teacher;
import com.ustudy.exam.service.ClientService;

@RestController
@RequestMapping(value = "/client")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ClientController {

	private static final Logger logger = LogManager.getLogger(ClientController.class);

	@Autowired
	private ClientService cs;

	/**
	 * 保存模板
	 * @param templates 
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/saveExamTemplate", method = RequestMethod.POST)
	public Map saveExamTemplate(@RequestBody String templates, HttpServletResponse resp) {

		logger.debug("saveTemplate().");
		logger.debug("templates: " + templates);
		
		Map result = cs.login("");
		
		if(!(boolean)result.get("success")){
			return result;
		}

		result = new HashMap<>();

		result.put("success", cs.saveTemplates(templates));

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
	public Map getExamTemplate(@PathVariable String examId, @PathVariable String gradeId, @PathVariable String subjectId, @RequestBody String token, HttpServletResponse resp) {

		logger.debug("getTemplates().");
		logger.debug("token: " + token);
		logger.debug("examId: " + examId + ",gradeId: " + gradeId + ",subjectId: " + subjectId);

		Map result = cs.login(token);
		
		if(!(boolean)result.get("success")){
			return result;
		}

		result = new HashMap<>();

		result.put("success", true);
		result.put("data", cs.getTemplateById(examId, gradeId, subjectId));

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
	public Map getExamSubjects(@PathVariable String examId, @PathVariable String gradeId, @RequestBody String token, HttpServletResponse resp) {

		logger.debug("getSubject().");
		logger.debug("token: " + token);
		logger.debug("examId: " + examId + ",gradeId: " + gradeId);

		Map result = cs.login(token);
		
		if(!(boolean)result.get("success")){
			return result;
		}

		result = new HashMap<>();

		result.put("success", true);
		result.put("data", cs.getExamSubjects(examId, gradeId));

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
	public Map getExamGrades(@PathVariable String examId, @PathVariable String examStatus, @RequestBody String token, HttpServletResponse resp) {

		logger.debug("getSubject().");
		logger.debug("token: " + token);
		logger.debug("examId: " + examId + ",examStatus: " + examStatus);

		Map result = cs.login(token);
		
		if(!(boolean)result.get("success")){
			return result;
		}

		result = new HashMap<>();

		result.put("success", true);
		result.put("data", cs.getExamGrades(examId, examStatus));

		return result;
	}
	
	/**
	 * 获取可扫描考试列表
	 * @param MarkingStatus 考试状态 允许进行扫描的考试状态
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getExams/{examStatus}", method = RequestMethod.POST)
	public Map getExams(@PathVariable String examStatus, @RequestBody String token, HttpServletResponse resp) {

		logger.debug("getExams().");
		logger.debug("token: " + token);
		logger.debug("examStatus: " + examStatus);

		Map result = cs.login(token);
		
		if(!(boolean)result.get("success")){
			return result;
		}

		result = new HashMap<>();

		result.put("success", true);
		result.put("data", cs.getExams(examStatus));

		return result;
	}
	
	/**
	 * 获取权限接口
	 * @param tokenstr 用户登录后的token信息
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getPermissions", method = RequestMethod.POST)
	public Map getPermissions(@RequestBody String token, HttpServletResponse resp) {
		
		logger.debug("getPermissionList().");
		logger.debug("token: " + token);

		Map map = cs.login(token);

		Map result = cs.login(token);
		
		if(!(boolean)result.get("success")){
			return result;
		}

		result = new HashMap<>();
		
		result.put("success", (boolean)map.get("success"));
		if((boolean)map.get("success")){
			Teacher teacher = (Teacher)map.get("teacher");
			result.put("data", teacher.getRoles());
		}else{
			result.put("message", map.get("message"));
		}
		
		return result;
	}
	
	/**
	 * 更新接口
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map login(@RequestBody String token, HttpServletResponse resp) {
		
		logger.debug("login().");
		logger.debug("token: " + token);
		
		return cs.login(token);
	}
	
	/**
	 * 更新接口
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public Map update(HttpServletRequest request, HttpServletResponse response) {
		
		String currentVersionNo = request.getParameter("currentVersionNo");
		
		logger.debug("update().");
		logger.debug("currentVersionNo: " + currentVersionNo);

		Map result = new HashMap<>();
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
		
		result.put("success", true);
		result.put("data", data);
		
		return result;
	}
	
}
