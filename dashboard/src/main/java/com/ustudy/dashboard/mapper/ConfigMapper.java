package com.ustudy.dashboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ustudy.dashboard.model.Subject;

@Mapper
public interface ConfigMapper {

	@Select("select id as subId, name as subName, child from ustudy.subject order by id")
	public List<Subject> getSubList();
	
}
