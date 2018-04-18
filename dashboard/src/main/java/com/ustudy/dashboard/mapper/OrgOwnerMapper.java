package com.ustudy.dashboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.dashboard.model.OrgOwner;
import com.ustudy.dashboard.model.Teacher;

@Mapper
public interface OrgOwnerMapper {

	@Insert("insert into ustudy.teacherroles(role_id, teac_id) values "
			+ "((select id from rolevalue where rolevalue.name = #{rname}), #{tid})")
	public int saveRoles(@Param("rname") String rolename, @Param("tid") String teacId);
	
	@Delete("delete teacherroles from ustudy.teacherroles left join rolevalue on "
			+ "rolevalue.id = teacherroles.role_id where teac_id = #{teacid} and "
			+ "rolevalue.name in ('org_owner', 'leader')")
	public int cleanRoleValues(String teacid);
	
	@Insert("insert into ustudy.orgowner (name, loginname, passwd, orgtype, orgid, role, ctime) "
			+ "values (#{name}, #{loginname}, #{passwd}, #{orgType}, #{orgId}, #{role}, #{createTime}) "
			+ "on duplicate key update name = #{name}, loginname = #{loginname}, passwd = #{passwd}, "
			+ "orgtype = #{orgType}, orgid = #{orgId}, role = #{role}")
	@Options(useGeneratedKeys = true)
	public int createOrgOwner(OrgOwner owner);
	
	@Insert("insert into ustudy.orgowner (id, name, loginname, passwd, orgtype, orgid, role, ctime) "
			+ "values (#{id}, #{name}, #{loginname}, #{passwd}, #{orgType}, #{orgId}, #{role}, #{createTime}) "
			+ "on duplicate key update name = #{name}, loginname = #{loginname}, passwd = #{passwd}, "
			+ "orgtype = #{orgType}, orgid = #{orgId}, role = #{role}")
	@Options(useGeneratedKeys = true)
	public int updateOrgOwner(OrgOwner owner);
	
	@Select("select id, name, loginname, passwd, orgtype as orgType, orgId as orgId, role, "
			+ "ctime as createTime from ustudy.orgowner where id > #{id} limit 10000")
	public List<OrgOwner> getOwnerList(long id);
	
	@Select("select id, name, loginname, passwd, orgtype as orgType, orgId as orgId, role, "
			+ "ctime as createTime from ustudy.orgowner where id = #{id}")
	public OrgOwner getOwnerById(long id);
	
	@Delete("delete orgowner, teacher from orgowner left join teacher on "
			+ "teacher.teacid = orgowner.loginname where orgowner.id = #{id}")
	public int deleteOwner(long id);
	
	@Delete("delete orgowner, teacher from orgowner left join teacher on "
			+ "teacher.teacid = orgowner.loginname where FIND_IN_SET (orgowner.id, #{ids})")
	public int deleteOwnerList(String ids);
	
	@Insert("insert into ustudy.teacher (teacid, teacname, passwd, orgtype, orgid, ctime, ll_time) "
			+ "values (#{teacId}, #{teacName}, #{passwd}, #{orgType}, #{orgId}, #{createTime}, #{llTime})")
	public int createTeacher(Teacher tea);
	
	@Insert("insert into ustudy.teacher (id, teacid, teacname, passwd, orgtype, orgid) values (#{id}, "
			+ "#{teacId}, #{teacName}, #{passwd}, #{orgType}, #{orgId})")
	public int updateTeacher(Teacher tea);
	
	@Select("select id, teacid as teacId, teacname as teacName, passwd, orgtype as orgType, orgid as orgId, "
			+ "ctime as createTime, ll_time as llTime from ustudy.teacher left join orgowner on "
			+ "orgowner.loginname = teacher.teacid where orgowner.id = #{id}")
	public Teacher getTeaByOwnerId(long id);
	
}
