package com.ustudy.exam.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.ClientMapper;
import com.ustudy.exam.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientMapper cm;
	
	@Override
	public boolean saveTemplates(String templates) {
		
		//cm.saveTemplates();		
		System.out.println("templates: " + templates);
		
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

	public List<Map<String, String>> getSubject(String EGID, String GDID){
		
		System.out.println("EGID: " + EGID + ",GDID: " + GDID);
		List<Map<String, String>> result = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			Map<String, String> subject = new HashMap<>();
			subject.put("Id", "科目ID");
			subject.put("Name", "科目名称");
			subject.put("UploadBathCount", "每次上传的试卷份数");
			
			result.add(subject);
		}
		
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
	
	public Map<String, List<Map<String, String>>> getExams(String MarkingStatus){
		
		System.out.println("MarkingStatus: " + MarkingStatus);
		
		Map<String, List<Map<String, String>>> examGradeResponse = new HashMap<>();
		
		List<Map<String, String>> list = new ArrayList<>();
		
		for(int i=0;i<5;i++){
			Map<String, String> result = new HashMap<>();		
			result.put("Id", "考试数据 ID " + i);
			result.put("ExamName", "考试名称  " + i);
			
			list.add(result);
		}
		
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
