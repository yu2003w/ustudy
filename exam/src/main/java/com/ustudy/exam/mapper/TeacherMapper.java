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
	
	@Select("select name from ustudy.rolevalue join ustudy.teacherroles on ustudy.rolevalue.id = "
			+ "ustudy.teacherroles.role_id where teac_id = #{uid}")
	public List<String> getRolesById(@Param("uid") String uid);
	
	@Select("select teacname from ustudy.teacher where teacid=#{tid}")
	public String findNameById(@Param("tid") String tid);
	
}
