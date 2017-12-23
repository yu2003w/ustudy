package com.ustudy.dashboard.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrgOwnerMapper {

	@Insert("insert into ustudy.teacherroles(role_id, teac_id) values (#{rid}, #{tid})")
	public int saveRoles(@Param("rid") int roleId, @Param("tid") String teacId);
	
	@Select("select id from ustudy.rolevalue where name=#{rv}")
	public int getRoleId(@Param("rv") String roleV);
	
}
