package com.ustudy.mmadapter.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {

	@Select("")
	public boolean validateStuInfoByStuNo(@Param("sn") String stuName, @Param("sno") String stuNo, 
			@Param("schn") String schName);
	
	@Select("")
	public boolean validateStuInfoByExamNo(@Param("sn") String stuName, @Param("exno") String examNo, 
			@Param("schn") String schName);
	
}
