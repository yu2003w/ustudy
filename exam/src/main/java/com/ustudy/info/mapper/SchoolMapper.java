package com.ustudy.info.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.info.model.Grade;
import com.ustudy.info.model.Item;
import com.ustudy.info.model.OwnerBrife;
import com.ustudy.info.model.School;
import com.ustudy.info.model.SubjectTeac;

@Mapper
public interface SchoolMapper {

	@Select("select id, schid as schoolId, schname as schoolName, type as schoolType, province, city, district "
			+ "from ustudy.school where schid=#{id}")
	public School getSchoolById(@Param("id") String schid);
	
	@Select("select loginname, name, role from ustudy.orgowner where orgtype=#{type} and orgid=#{id}")
	public List<OwnerBrife> getOrgOwners(@Param("type") String orgT, @Param("id") String orgId);
	
	@Select("select ustudy.subject.name as sub, sub_owner as teacid, teacname from departsub left join teacher "
			+ "on departsub.sub_owner = teacher.teacid left join ustudy.subject on ustudy.subject.id = "
			+ "ustudy.departsub.sub_id where departsub.schid=#{id} and departsub.type=#{type} and "
			+ "departsub.sub_owner IS NULL")
	public List<SubjectTeac> getSubjectTeachers(@Param("id") String orgId, @Param("type") String type);
	
	@Select("select grade.id, grade_name as name, classes_num as classNum, grade_owner as gradeO from "
			+ "ustudy.grade left join ustudy.teacher on grade.grade_owner = teacher.teacid where "
			+ "ustudy.grade.schid=#{schid} and ustudy.grade.grade_owner IS NULL")
	public List<Grade> getGradesBySchId(@Param("schid") String schid);
	
	@Select("select id, name from ustudy.rolevalue")
	public List<Item> getRoles();
	
	@Select("select id, grade_name as name from ustudy.grade where schid=#{schId}")
	public List<Item> getGrades(String schId);
	
	@Select("select sub_id as id, name from ustudy.gradesub join ustudy.subject on "
			+ "ustudy.subject.id = ustudy.gradesub.sub_id where grade_id=#{grId}")
	public List<Item> getGradeSub(int grId);
	
}
