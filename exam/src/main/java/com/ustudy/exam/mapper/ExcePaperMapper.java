package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.exam.model.ExcePaperSum;

@Mapper
public interface ExcePaperMapper {

	@Select("select exam_name as examName, examgradesub.id as egsId, grade.grade_name as gradeName, "
			+ "subject.name as subName, (select count(*) from ustudy.paper where paper_status=#{ps} and "
			+ "error_status=#{es} and paper.exam_grade_sub_id = examgradesub.id) as num from ustudy.exam join "
			+ "ustudy.examschool on ustudy.exam.id = ustudy.examschool.examid join ustudy.examgradesub on "
			+ "ustudy.examgradesub.examid = ustudy.exam.id join ustudy.subject on ustudy.examgradesub.sub_id = "
			+ "ustudy.subject.id join ustudy.grade on ustudy.grade.id = examgradesub.grade_id where "
			+ "ustudy.examschool.schid=#{schid}")
	public List<ExcePaperSum> getPapersBySchool(@Param("ps") int ps, @Param("es") int es, 
			@Param("schid") String schid);
	
}
