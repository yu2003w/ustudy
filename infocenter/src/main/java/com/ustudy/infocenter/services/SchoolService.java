package com.ustudy.infocenter.services;

import java.util.List;

import com.ustudy.infocenter.model.ClassInfo;
import com.ustudy.infocenter.model.Grade;
import com.ustudy.infocenter.model.School;
import com.ustudy.infocenter.model.SubjectLeader;
import com.ustudy.infocenter.model.TeacherSub;

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
	
	public List<TeacherSub> getDepTeac(String depName);
	
	public Grade getGradeInfo(String id);
	
	public int updateGradeInfo(Grade item);
	
	public List<TeacherSub> getGradeTeac(String gradeId);
	
	public ClassInfo getClassInfo(String id);
	
	public int updateClassInfo(ClassInfo item);
	
	public List<TeacherSub> getClassTeac(String id);
	
}
