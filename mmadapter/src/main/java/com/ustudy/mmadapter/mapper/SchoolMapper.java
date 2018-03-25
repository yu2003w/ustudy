package com.ustudy.mmadapter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ustudy.mmadapter.model.School;

@Mapper
public interface SchoolMapper {

	@Select("select school.schname as schoolName, school.schid as schoolId from school where "
			+ "school.schname like '%#{key}%' limit 10;")
	List<School> getSchoolsByKey(String key);
	
}
