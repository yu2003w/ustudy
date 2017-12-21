package com.ustudy.info.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.info.model.Subject;
import com.ustudy.info.model.Teacher;

@Mapper
public interface TeaMapper {

	@Insert("insert into ustudy.teacher(teacid, teacname, passwd, orgtype, orgid, ctime, ll_time) values(#{teacId},"
			+ "#{teacName}, #{passwd}, #{orgtype}, #{orgid}, #{cTime}, #{llTime}) on duplicate key update "
			+ "teacname=#{teacName}, passwd=#{passwd}, orgtype=#{orgtype}, orgid=#{orgid}, ctime=#{cTime}, "
			+ "ll_time=#{llTime}")
	@Options(useGeneratedKeys=true)
	public int createTeacher(Teacher tea);
	
	@Select("select id, teacid as teacId, teacname as teacName, passwd, orgtype, orgid, ctime as cTime, "
			+ "ll_time as llTime from ustudy.teacher where teacid=#{tid}")
	public Teacher findTeaByTeaId(String tid);
	
	@Select("select id, teacid as teacId, teacname as teacName, passwd, orgtype, orgid, ctime as cTime, "
			+ "ll_time as llTime from ustudy.teacher where id=#{id}")
	public Teacher findTeaById(int id);
	
	@Select("select id, teacid as teacId, teacname as teacName, passwd, orgtype, orgid, ctime as cTime, "
			+ "ll_time as llTime from ustudy.teacher where orgid=#{oid} and orgtype=#{otype} and id > #{id} "
			+ "limit 1000")
	public List<Teacher> getTeaList(@Param("id") int id, @Param("otype") String orgType, @Param("oid") String orgId);
	
	@Insert("insert into ustudy.teachersub(sub_id, teac_id) values(#{sid}, #{tid})")
	public int saveTeaSub(@Param("sid") String subId, @Param("tid") String teacid);
	
	@Insert("insert into ustudy.teachergrade(grade_id, teac_id) values(#{gid}, #{tid})")
	public int saveTeaGr(@Param("gid") String gid, @Param("tid") String teacid);
	
	@Select("select id as subId, name as subName from ustudy.subject")
	public List<Subject> getSubs();
	
}
