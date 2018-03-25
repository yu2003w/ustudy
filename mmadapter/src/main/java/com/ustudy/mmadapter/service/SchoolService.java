package com.ustudy.mmadapter.service;

import java.util.List;

import com.ustudy.mmadapter.model.School;

public interface SchoolService {

	List<School> getSchoolsByKey(String key);
	
}
