package com.ustudy.info.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.info.model.Grade;
import com.ustudy.info.model.OwnerBrife;
import com.ustudy.info.model.School;
import com.ustudy.info.model.SubjectTeac;

@Mapper
public interface SchoolMapper {

	@Select("select * from ustudy.school where schid=#{id}")
	public School getSchoolById(@Param("id") String schid);
	
	@Select("select loginname, name, role from ustudy.orgowner where orgtype=#{type} and orgid=#{id}")
	public List<OwnerBrife> getOrgOwners(@Param("type") String orgT, @Param("id") String orgId);
	
	@Select("select sub_name, sub_owner, teacname from departsub join teacher on departsub.sub_owner = teacher.teacid where departsub.schid = ? and departsub.type = ?")
	public List<SubjectTeac> getSubjectTeachers(@Param("id") String orgId, @Param("type") String type);
	
	@Select("select grade.id, grade_name, classes_num, grade_owner, teacname from grade join teacher on "
			+ "grade.grade_owner = teacher.teacid where schid=#{schid}")
	public List<Grade> getGradesBySchId(@Param("schid") String schid);
	
}
