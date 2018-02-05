package com.ustudy.dashboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ustudy.dashboard.model.Subject;

@Mapper
public interface ConfigMapper {

	@Select("select id as subId, name as subName from ustudy.subject")
	public List<Subject> getSubList();
	
}
