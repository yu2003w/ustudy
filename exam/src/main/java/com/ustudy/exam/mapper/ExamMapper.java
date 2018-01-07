package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ustudy.Item;
import com.ustudy.exam.model.ExamGrBrife;

@Mapper
public interface ExamMapper {

	@Select("select exam.id as id, exam.exam_name as examName, group_concat(distinct examgradesub.grade_id, '-',"
			+ " grade.grade_name order by examgradesub.grade_id) as grs from ustudy.exam join ustudy.examschool on "
			+ "exam.id = examschool.examid join examgradesub on exam.id = examgradesub.examid join grade "
			+ "on grade.id = examgradesub.grade_id where examschool.schid = #{sid} group by exam.id")
	public List<ExamGrBrife> getExamGrBrife(String sid);
	
	@Select("select id, cls_name as name from ustudy.class where grade_id=#{gid}")
	public List<Item> getClassByGrId(int gid);
	
}
