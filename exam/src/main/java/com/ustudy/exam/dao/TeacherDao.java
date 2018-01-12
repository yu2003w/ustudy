package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface TeacherDao {

	Map<String, Object> getTeacherBySchoolIdType(@Param("schoolId")String schoolId, @Param("type")String type);
	
	List<Map<String, Object>> getTeachersBySchoolId(String schoolId);
	
	List<Map<String, Object>> getTeachersBySchoolInGradeName(@Param("schoolId")String schoolId, @Param("gradeName")String gradeName);
	
	List<Map<String, Object>> getGradeNotaskTeachers(Long gradeId);
	
	List<Map<String, Object>> getSchoolTeachers(String schId);
	
	List<Map<String, Object>> getSchoolOwner(String schId);
	
	List<Map<String, Object>> getSchoolLeader(String schId);
	
}
