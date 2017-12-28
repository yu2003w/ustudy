package com.ustudy.dashboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.dashboard.model.Grade;
import com.ustudy.dashboard.model.OrgBrife;
import com.ustudy.dashboard.model.School;
import com.ustudy.dashboard.model.Subject;
import com.ustudy.dashboard.model.Teacher;

@Mapper
public interface SchoolMapper {

	@Select("select id, schid as schoolId, schname as schoolName, type as schoolType, province, city, district "
			+ "from ustudy.school where id > #{id} limit 10000")
	public List<School> getSchools(@Param("id") int id);
	
	@Select("select schid as orgId, schname as orgName, 'school' as orgType from ustudy.school where id "
			+ "> ? limit 10000")
	public List<OrgBrife> getSchBrife(@Param("id") int id);
	
	@Select("select id, schid as schoolId, schname as schoolName, type as schoolType, province, city, district "
			+ "from ustudy.school where id=#{id}")
	public School getSchoolById(@Param("id") int id);
	
	@Insert("insert into ustudy.school (schid, schname, type, province, city, district) values(#{schoolId}, "
			+ "#{schoolName}, #{schoolType}, #{province}, #{city}, #{district}) on duplicate key update "
			+ "type=#{schoolType}, province=#{province}, city=#{city}, district=#{district}")
	@Options(useGeneratedKeys=true)
	public int createSchool(School item);
	
	@Delete("delete from ustudy.school where FIND_IN_SET (id, #{ids})")
	public int delSchool(@Param("ids") String ids);
	
	@Insert("insert into ustudy.grade(grade_name, classes_num, schid) values (#{gr.gradeName}, #{gr.num}, "
			+ "#{schid}) on duplicate key update grade_name=#{gr.gradeName}, classes_num=#{gr.num}, "
			+ "schid=#{schid}")
	@Options(useGeneratedKeys=true, keyProperty="gr.id")
	public int createGrade(@Param("gr") Grade gr, @Param("schid") String schid);
	
	@Insert("insert into ustudy.gradesub(sub_id, grade_id) values (#{subId}, #{gradeId}) on duplicate key "
			+ "update sub_id=#{subId}, grade_id=#{gradeId}")
	public int createGradeSub(@Param("subId") String subId, @Param("gradeId") int gradeId);
	
	@Select("select id as subId, name as courseName from ustudy.subject")
	public List<Subject> getSubs();
	
	@Insert("insert into ustudy.class (grade_id, cls_name) values(#{grId}, #{clsN}) on duplicate key update "
			+ "grade_id=#{grId}, cls_name=#{clsN}")
	public int createClass(@Param("grId") int grId, @Param("clsN") String clsName);
	
	@Insert("insert into ustudy.departsub (sub_id, type, schid) values(#{subId}, #{type}, #{schId}) on "
			+ "duplicate key update sub_id=#{subId}, type=#{type}, schid=#{schId}")
	public int createDepartSub(@Param("subId") String subId, @Param("type") String type, 
			@Param("schId") String schId);
	
	@Delete("delete from ustudy.departsub where schid=#{sid}")
	public int delDepartSub(@Param("sid") String schId);
	
	@Select("select id, grade_name as gradeName, classes_num as num from ustudy.grade where schid=#{sId}")
	public List<Grade> getGrades(@Param("sId") String schId);
	
	@Select("select sub_id as subId, name as courseName from ustudy.gradesub join ustudy.subject on "
			+ "ustudy.gradesub.sub_id = ustudy.subject.id where ustudy.gradesub.grade_id=#{gid}")
	public List<Subject> getGradeSub(@Param("gid") int gid);
	
	@Delete("delete from ustudy.grade where FIND_IN_SET (id, #{ids})")
	public int delGrade(String ids);
	
	@Delete("delete from ustudy.gradesub where grade_id=#{qid}")
	public int delGradeSubs(int gid);
	
	@Insert("insert into ustudy.teacher(teacid, teacname, passwd, orgtype, orgid, ctime, ll_time) "
			+ "values(#{teacId}, #{teacName}, #{passwd}, #{orgType}, #{orgId}, #{createTime}, #{llTime}) on "
			+ "duplicate key update teacname=#{teacName}, passwd=#{passwd}, orgtype=#{orgType}, "
			+ "orgid=#{orgId}, ctime=#{createTime}, ll_time=#{llTime}")
	@Options(useGeneratedKeys=true)
	public int createCleaner(Teacher tea);
	
}
