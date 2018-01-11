package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.exam.model.statics.EgsMeta;
import com.ustudy.exam.model.statics.QuesMarkMetrics;

@Mapper
public interface MarkProgressMapper {

	@Select("select exam.id as examId, exam_name as examName, examschool.schid as schoolId, "
			+ "school.schname as schoolName, examgradesub.id as egsId, examgradesub.grade_id as gradeId, "
			+ "grade.grade_name as gradeName, examgradesub.sub_id as subId, subject.name as subName "
			+ "from ustudy.exam "
			+ "join examgradesub on examgradesub.examid = exam.id "
			+ "join examschool on examschool.examid = exam.id "
			+ "join school on school.schid = examschool.schid "
			+ "join grade on grade.id = examgradesub.grade_id "
			+ "join subject on subject.id = examgradesub.sub_id "
			+ "where examschool.schid = #{sid} and examgradesub.status != '2' order by gradeId")
	public List<EgsMeta> getExamMetaInfo(String sid);
	
	@Select("select question.id as quesid, startno, endno "
			+ "(select count(*) from examinee where examinee.paper_status != '1' and examinee.examid = #{eid}) * "
			+ "(select if (strcmp(question.mark_mode,'双评'), 1, 2)) as total, "
			+ "(select count(*) from ustudy.answer where isviewed=1 and answer.quesid = question.id) as marked "
			+ "from question where exam_grade_sub_id = #{egsid}")
	public List<QuesMarkMetrics> getQuesMarkMetricsByEgsId(@Param("eid") int eid, @Param("egsid") int egsid);
	
}
