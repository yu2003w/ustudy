package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ClientDao;
import com.ustudy.exam.dao.ExamDao;
import com.ustudy.exam.dao.ExamSubjectDao;
import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.model.Teacher;
import com.ustudy.exam.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Resource
	private ClientDao clientDaoImpl;
	
	@Resource
	private ExamDao examDaoImpl;
	
	@Resource
	private ExamSubjectDao examSubjectDaoImpl;
	
	@Override
	public boolean saveTemplates(String templates) {
		
		//cm.saveTemplates();
		System.out.println("templates: " + templates);
		Teacher teacher = clientDaoImpl.getTeacher(1);
		System.out.println(teacher.getUname());
		
		return true;
	}

	public Map<String, String> getTemplates(String CSID){
		
		System.out.println("CSID: " + CSID);
		Map<String, String> result = new HashMap<>();
		
		result.put("ExamQAPicPath", "标准答案图片名称,多个用‘,’分隔");
		result.put("ExamPaperPicPath", "试卷原卷图片名称，多个用‘,’分隔");
		result.put("AnswerSheetPicPath", "试卷答题卡原图 ,多张图片用 ',' 分隔");
		result.put("AnswerSheetXMLPath", "试卷答题卡xml模版地址");
		result.put("AnswerSheetXML", "试卷答题卡xml模版,json格式存储");
		result.put("Id", "模板保存的数据库主键ID");
		
		return result;
	}

	public List<ExamSubject> getSubject(String EGID, String GDID){
		
		System.out.println("EGID: " + EGID + ",GDID: " + GDID);
		List<ExamSubject> result = examSubjectDaoImpl.getExamSubjects(EGID, GDID);
		
		return result;
	}
	
	public Map<String, List<Map<String, String>>> getExamGrade(String egID, String markingStatus){
		
		System.out.println("egID: " + egID + ",markingStatus: " + markingStatus);
		
		Map<String, List<Map<String, String>>> examGradeResponse = new HashMap<>();
		
		List<Map<String, String>> list = new ArrayList<>();
		
		for(int i=0;i<5;i++){
			Map<String, String> result = new HashMap<>();		
			result.put("GradeCode", "年级编码 1年级 - 12年级 " + i);
			result.put("GradeName", "年级名称 如：一年级 初一年级 高一年级 " + i);
			result.put("GradeClassName", "考试年级 班级名称 冗余设计，暂时可不用 " + i);
			
			list.add(result);
		}
		
		examGradeResponse.put("ExamGradeResponse", list);
		
		return examGradeResponse;
	}
	
	public Map<String, List<Exam>> getExams(String markingStatus){
		
		System.out.println("MarkingStatus: " + markingStatus);
		
		Map<String, List<Exam>> examGradeResponse = new HashMap<>();
		
		List<Exam> list = examDaoImpl.getExams(markingStatus);
		
		examGradeResponse.put("ExamGradeResponse", list);
		
		return examGradeResponse;
		
	}
	
	public Map<String, List<Map<String, String>>> getPermissionList(String tokenstr){
		
		System.out.println("tokenstr: " + tokenstr);
		
		Map<String, List<Map<String, String>>> permissionList = new HashMap<>();
		
		List<Map<String, String>> list = new ArrayList<>();
		
		for(int i=0;i<5;i++){
			Map<String, String> result = new HashMap<>();		
			result.put("Name", "权限名称 " + i);
			result.put("displayName", "权限说明  " + i);
			list.add(result);
		}
		
		permissionList.put("PermissionList", list);
		
		return permissionList;
	}
	
	public Map<String, Object> login(String username, String password){
		
		System.out.println("username: " + username + ",password: " + password);
		
		Map<String, Object> userinfo = new HashMap<>();
		userinfo.put("SchoolName", "用户对应的学校名称");
		userinfo.put("SID", "学校唯一ID int");
		userinfo.put("UID", "用户唯一ID int");
		userinfo.put("UserName", "用户名");
		userinfo.put("token", "用户登录成功后的token信息");
		
		List<Map<String, String>> permissions = new ArrayList<>();
		
		for(int i=0;i<5;i++){
			Map<String, String> permission = new HashMap<>();		
			permission.put("PermissionName", "扫描 " + i);
			permissions.add(permission);
		}
		
		userinfo.put("PermissionType", permissions);
		
		return userinfo;
		
	}
	
	public Map<String, String> update(){
		Map<String, String> result = new HashMap<>();		
		result.put("BeAvailableUpdates", "是否有可用更新 bool");
		result.put("DownLoadPath", "更新下载地址");
		
		return result;
	}
	
}
