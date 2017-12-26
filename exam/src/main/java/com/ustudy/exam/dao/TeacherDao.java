package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface TeacherDao {

	Map getTeacherBySchoolIdType(@Param("schoolId")String schoolId, @Param("type")String type);
	
	List<Map> getTeachersBySchoolId(String schoolId);
	
	List<Map> getTeachersBySchoolInGradeName(@Param("schoolId")String schoolId, @Param("gradeName")String gradeName);
	
	List<Map> getGradeNotaskTeachers(Long gradeId);
	
}
