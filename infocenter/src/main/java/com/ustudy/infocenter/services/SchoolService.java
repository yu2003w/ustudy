package com.ustudy.infocenter.services;

import java.util.List;

import com.ustudy.infocenter.model.ClassInfo;
import com.ustudy.infocenter.model.Grade;
import com.ustudy.infocenter.model.School;
import com.ustudy.infocenter.model.SubjectLeader;
import com.ustudy.infocenter.model.TeacherBrife;

/**
 * @author jared
 *
 * This interface is to retrieve school information for specific user and 
 * allow user to modify some attributes of the school.
 */
public interface SchoolService {

	public School getSchool();
	
	// only retrieve subjects information for the department
	public List<SubjectLeader> getDepSubs(String depName);
	
	public int updateDepSubs(List<SubjectLeader> subs, String dType);
	
	public List<TeacherBrife> getDepTeac(String depName);
	
	public Grade getGradeInfo(String id);
	
	public int updateGradeInfo(Grade item);
	
	public List<TeacherBrife> getGradeTeac(String gradeId);
	
	public ClassInfo getClassInfo(String id);
	
	public int updateClassInfo(ClassInfo item);
	
	public List<TeacherBrife> getClassTeac(String id);
	
}
