package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.exam.model.Teacher;

@Mapper
public interface TeacherMapper {

	@Select("select teacid as uid, teacname as uname, orgtype, orgid from ustudy.teacher where teacid = #{uid}")
	public Teacher findUserById(@Param("uid") String uid);
	
	@Select("select role_name from ustudy.teacherroles where teac_id = #{uid}")
	public List<String> getRolesById(@Param("uid") String uid);
	
}
