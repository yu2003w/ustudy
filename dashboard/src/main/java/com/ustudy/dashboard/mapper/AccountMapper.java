package com.ustudy.dashboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ustudy.dashboard.model.Account;

public interface AccountMapper {

	@Select("select users.id, loginname as userId, fullname as userName, rolevalue.name as userType, "
			+ "ctime as creationTime, ll_time as lastLoginTime, status as userStatus, province, city, "
			+ "district from users join roles on roles.user_name = users.loginname "
			+ "join rolevalue on roles.role_id = rolevalue.id where loginname = #{loginname}")
	public Account getUserByLoginName(String loginname);
	
	@Select("select users.id, loginname as userId, fullname as userName, rolevalue.name as userType, "
			+ "ctime as creationTime, ll_time as lastLoginTime, status as userStatus, province, city, "
			+ "district from users join roles on roles.user_name = users.loginname "
			+ "join rolevalue on roles.role_id = rolevalue.id where users.id > #{id} limit 10000")
	public List<Account> getAccountList(int id);
	
	@Select("select users.id, loginname as userId, fullname as userName, rolevalue.name as userType, "
			+ "ctime as creationTime, ll_time as lastLoginTime, status as userStatus, province, city, "
			+ "district from users join roles on roles.user_name = users.loginname "
			+ "join rolevalue on roles.role_id = rolevalue.id where id = #{id}")
	public Account getUserById(int id);
	
	@Insert("insert into ustudy.users(loginname, fullname, passwd, ctime, ll_time, status, province, city, "
			+ "district) values(#{loginname}, #{fullname}, #{passwd}, #{createTime}, #{lastLoginTime}, "
			+ "#{status}, #{province}, #{city}, #{district}) on duplicate key update fullname = #{fullname}, "
			+ "passwd = #{passwd}, province = #{province}, city = #{city}, district = #{district}, "
			+ "id=LAST_INSERT_ID(id)")
	@Options(useGeneratedKeys = true)
	public int createUser(Account u);
	
	@Insert("insert into ustudy.roles(role_id, user_name) values(#{rid}, #{uname})")
	public int populateRoles(@Param("rid") int role, @Param("uname") String loginname);
	
	@Select("select id from ustudy.rolevalue where rolevalue.name = #{role}")
	public int getRoleId(String role);
	
	@Delete("delete from ustudy.roles where user_name = #{uname}")
	public int clearRolesForUser(String uname);
	
	@Delete("delete from ustudy.users where id = #{id}")
	public int deleteAccount(int id);
	
	@Delete("delete from ustudy.users where FIND_IN_SET (id, #{ids})")
	public int deleteAccounts(@Param("ids") String ids);
	
	@Update("update ustudy.users set ll_time = #{timestamp} where id = #{id}")
	public int updateLLTime(@Param("timestamp") String timestamp, @Param("id") int id);
	
}
