package com.ustudy.exam.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.exam.model.User;

@Mapper
public interface UserMapper {

	@Select("select teacid as uid, teacname as uname from ustudy.teacher where teacid = #{uid}")
	public User findUserById(@Param("uid") String uid);
}
