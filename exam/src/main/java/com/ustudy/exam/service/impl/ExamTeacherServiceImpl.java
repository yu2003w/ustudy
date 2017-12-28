package com.ustudy.exam.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.TeacherMapper;
import com.ustudy.exam.model.Teacher;
import com.ustudy.exam.service.TeacherService;
import com.ustudy.exam.utility.ExamUtil;

@Service
public class ExamTeacherServiceImpl implements TeacherService {
	
	private static final Logger logger = LogManager.getLogger(ExamTeacherServiceImpl.class);

	@Autowired
	private TeacherMapper userM;

	@Override
	public Teacher findUserById(String id) {
		
		Teacher u = this.userM.findUserById(id);
		return u;
	}

	@Override
	public List<String> getRolesById(String id) {
		List<String> rL = userM.getRolesById(id);
		
		for (String r: rL) {
			r = ExamUtil.getRolemapping().get(r);
		}
		return rL;
	}

	@Override
	public String findPriRoleById(String id) {
		List<String> roles = userM.getRolesById(id);
		String role = this.getHighPriRole(roles);
		return role;
	}
	
	private String getHighPriRole(List<String> rL) {
		
		String r = null;

		if (rL.contains("cleaner")) {
			r = "cleaner";
		}
		else if (rL.contains("org_owner")) {
			r = "org_owner";
		} else if (rL.contains("leader")) {
			r = "leader";
		} else if (rL.contains("gleader")) {
			r = "gleader";
		} else if (rL.contains("sleader")) {
			r = "sleader";
		} else if (rL.contains("pleader")) {
			r = "pleader";
		} else if (rL.contains("cteacher")) {
			r = "cteacher";
		} 
		else
			r = "teacher";

		return ExamUtil.getRolemapping().get(r);
	}

	@Override
	public void setLLTime(String tid) {
		int ret = userM.setLLTime(tid, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		if (ret != 1) {
			logger.error("setLLTime(), failed to set lltime for " + tid);
			throw new RuntimeException("setLLTime(), failed to set lltime for " + tid);
		}

	}

}
