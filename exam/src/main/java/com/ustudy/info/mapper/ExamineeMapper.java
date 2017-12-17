package com.ustudy.info.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ustudy.info.model.Examinee;

@Mapper
public interface ExamineeMapper {

	@Insert("insert into ustudy.examinee (exam_code, examid, schid, gradeid, class_id, class_name, name, stuno,"
			+ " room) values(#{stuExamId}, #{examId}, #{schId}, #{gradeId}, #{classId}, #{className}, #{stuName}, "
			+ "#{stuId}, #{roomId}) on duplicate key update schid=#{schId}, gradeid=#{gradeId}, "
			+ "class_id=#{classId}, class_name=#{className}, name=#{stuName}, stuno=#{stuId}, room=#{roomId}")
	public int createExaminee(Examinee exs);
	
	@Delete("delete from ustudy.examinee where id=#{id}")
	public int deleteExaminee(int id);
	
	@Select("select id, exam_code, examid, schid, gradeid, class_id, class_name, name, stuno, root from "
			+ "ustudy.examinee where id=#{id}")
	public Examinee getExamineeById(int id);
	
}
