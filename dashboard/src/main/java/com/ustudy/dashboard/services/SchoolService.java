package com.ustudy.dashboard.services;

import java.util.List;

import com.ustudy.dashboard.model.OrgBrife;
import com.ustudy.dashboard.model.School;
import com.ustudy.dashboard.model.Subject;

/**
 * @author jared
 *
 */

public interface SchoolService {

	
	/**
	 * @param id  --- retrieve item list from id
	 * @return
	 */
	public List<School> getSchools(int id);
	
	/**
	 * @param data --- to be stored into database
	 * @return  --- index in the datababse
	 */
	public long createSchool(School data);
	
	public void deleteSchool(int id);
	
	public School displaySchool(int id);
	
	public void updateSchool(School data, int id);
	
	public int delSchools(String ids);
	
	public List<OrgBrife> getOrgBrifeList(int id);
	
	// try to retrieve all subjects
	List<Subject> retrieveSubjects();
	
}
