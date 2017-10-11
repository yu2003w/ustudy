package com.ustudy.exam.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	public Map saveExamTemplates(@RequestBody String templates, HttpServletResponse resp) {

		logger.debug("saveTemplate().");
		logger.debug("templates: " + templates);

		Map result = new HashMap<>();

		result.put("success", cs.saveTemplates(templates));

		return result;
	}
	
	/**
	 * 获取模板
	 * @param CSID 考试年级科目ID
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getExamTemplate/{CSID}", method = RequestMethod.POST)
	public Map getExamTemplate(@PathVariable String CSID, HttpServletResponse resp) {

		logger.debug("getTemplates().");
		logger.debug("CSID: " + CSID);

		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", cs.getTemplates(CSID));

		return result;
	}
	
	/**
	 * 获取考试年级科目
	 * @param EGID 考试ID
	 * @param GDID 年级ID
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getExamSubject/{EGID}/{GDID}", method = RequestMethod.POST)
	public Map getSubject(@PathVariable String EGID, @PathVariable String GDID, HttpServletResponse resp) {

		logger.debug("getSubject().");
		logger.debug("EGID: " + EGID + ",GDID: " + GDID);

		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", cs.getSubject(EGID, GDID));

		return result;
	}
	
	/**
	 * 获取考试年级
	 * @param EGID 考试ID
	 * @param GDID 可进行扫描的考试状态
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getExamGrade/{egID}/{markingStatus}", method = RequestMethod.POST)
	public Map getExamGrade(@PathVariable String egID, @PathVariable String markingStatus, HttpServletResponse resp) {

		logger.debug("getSubject().");
		logger.debug("egID: " + egID + ",markingStatus: " + markingStatus);

		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", cs.getExamGrade(egID, markingStatus));

		return result;
	}
	
	/**
	 * 获取可扫描考试列表
	 * @param MarkingStatus 考试状态 允许进行扫描的考试状态
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getExams/{MarkingStatus}", method = RequestMethod.POST)
	public Map getExams(@PathVariable String MarkingStatus, HttpServletResponse resp) {

		logger.debug("getExams().");
		logger.debug("MarkingStatus: " + MarkingStatus);

		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", cs.getExams(MarkingStatus));

		return result;
	}
	
	/**
	 * 获取权限接口
	 * @param tokenstr 用户登录后的token信息
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/getPermissions/{tokenstr}", method = RequestMethod.GET)
	public Map getPermissionList(@PathVariable String tokenstr, HttpServletResponse resp) {
		
		logger.debug("getPermissionList().");
		logger.debug("tokenstr: " + tokenstr);

		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", cs.getPermissionList(tokenstr));
		
		return result;
	}
	
	/**
	 * 更新接口
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/login/{username}/{password}", method = RequestMethod.POST)
	public Map login(@PathVariable String username, @PathVariable String password, HttpServletResponse resp) {
		
		logger.debug("login().");
		logger.debug("username: " + username + ",password: " + password);

		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", cs.login(username, password));
		
		return result;
	}
	
	/**
	 * 更新接口
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public Map update(HttpServletResponse resp) {
		
		logger.debug("update().");

		Map result = new HashMap<>();

		result.put("success", true);
		result.put("data", cs.update());
		
		return result;
	}
	
}
