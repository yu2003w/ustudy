package com.ustudy.info.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ustudy.info.model.Item;
import com.ustudy.info.model.Subject;
import com.ustudy.info.model.Teacher;

@Mapper
public interface TeaMapper {

	@Insert("insert into ustudy.teacher(teacid, teacname, passwd, orgtype, orgid, ctime, ll_time) "
			+ "values(#{teacId}, #{teacName}, #{passwd}, #{orgtype}, #{orgid}, #{cTime}, #{llTime}) on "
			+ "duplicate key update teacname=#{teacName}, passwd=#{passwd}, orgtype=#{orgtype}, "
			+ "orgid=#{orgid}, ctime=#{cTime}, ll_time=#{llTime}")
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
	public int saveTeaSub(@Param("sid") int subId, @Param("tid") String teacid);
	
	@Insert("insert into ustudy.teachergrade(grade_id, teac_id) values(#{gid}, #{tid})")
	public int saveTeaGr(@Param("gid") int gid, @Param("tid") String teacid);
	
	@Select("select id as subId, name as subName from ustudy.subject")
	public List<Subject> getSubs();
	
	@Insert("insert into ustudy.teacherroles(role_id, teac_id) values (#{rid}, #{tid})")
	public int saveRoles(@Param("rid") int id, @Param("tid") String teacid);
	
	@Update("update ustudy.grade set grade_owner=#{gid} where ustudy.grade.id=#{gid}")
	public int saveGrOwner(@Param("gid") int id, @Param("tid") String teacid);
	
	@Update("update ustudy.gradesub set sub_owner=#{tid} where sub_id=#{sid} and grade_id=#{gid}")
	public int saveGrSubOwner(@Param("sid") int subId, @Param("gid") int gradeId, @Param("tid") String teacid);
	
	@Select("select grade_name from ustudy.grade where id=#{gid}")
	public String getGrName(@Param("gid") int id);
	
	@Update("update ustudy.departsub set sub_owner=#{tid} where type=#{type} and schid=#{schid} and "
			+ "sub_id=#{sid}")
	public int saveDepartSubOwner(@Param("sid") int subId, @Param("schid") String schId, 
			@Param("type") String type, @Param("tid") String teacid);
	
	@Select("select ustudy.grade.id, ustudy.grade.grade_name as name from ustudy.grade join "
			+ "ustudy.teachergrade on ustudy.grade.id = ustudy.teachergrade.grade_id where "
			+ "ustudy.teachergrade.teac_id=#{tid}")
	public List<Item> getTeaGrade(String tid);
	
	@Select("select ustudy.subject.id, ustudy.subject.name from ustudy.subject join ustudy.teachersub on "
			+ "ustudy.subject.id = ustudy.teachersub.sub_id where ustudy.teachersub.teac_id=#{tid}")
	public List<Item> getTeaSubjects(String tid);
	
	@Select("select ustudy.rolevalue.id, ustudy.rolevalue.name from ustudy.rolevalue join "
			+ "ustudy.teacherroles on ustudy.rolevalue.id = ustudy.teacherroles.role_id where "
			+ "ustudy.teacherroles.teac_id=#{tid}")
	public List<Item> getTeaRoles(String tid);
	
}
