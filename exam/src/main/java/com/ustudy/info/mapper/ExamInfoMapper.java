package com.ustudy.info.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import com.ustudy.info.model.ExamInfo;


@Mapper
public interface ExamInfoMapper {

	@Insert("insert into ustudy.exam (exam_name, exam_date, type, status) values (#{examName}, #{examDate}, "
			+ "#{type}, #{status}) on duplicate key update set exam_name=#{examName}, exam_date=#{examDate}, "
			+ "type=#{type}, status=#{status}, id=LAST_INSERT_ID(id)")
	@Options(useGeneratedKeys=true)
	public int createExamInfo(ExamInfo ex);
	
	@Insert("insert into ustudy.examschool(examid, schid) values(#{eid}, #{sid}) on duplicate key update "
			+ "set examid=#{eid}, schId=#{sid}")
	public int createExamSchool(@Param("eid") int eid, @Param("sid") String sid);
	
	@Insert("insert into ustudy.examgradesub(examid, grade_id, sub_id) values(#{eid}, #{gid}, #{sid}) on "
			+ "duplicate key update set examid=#{eid}, grade_id=#{gid}, sub_id=#{sid}")
	public int createExamGradeSub(@Param("eid") int examId, @Param("gid") int gradeId, @Param("sid") int subId);
	
}
