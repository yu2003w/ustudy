package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.exam.model.statics.EgsMarkMetrics;
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
	
	@Select("select question.id as quesid, startno, endno, mark_mode as markStyle, "
			+ "group_concat(teacher.teacname, '-', teacher.teacid) as teaL, "
			+ "(select count(*) from examinee where examinee.paper_status = '1' and examinee.examid = #{eid}) * "
			+ "(select if (strcmp(question.mark_mode,'双评'), 1, 2)) + "
			+ "(select if (strcmp(question.mark_mode,'双评'), 0, "
			+ "(SELECT count(if(abs(ans1.score - ans2.score) >=5, 1, null)) "
			+ "from ustudy.answer ans1 cross join ustudy.answer as ans2 where ans1.quesid = ans2.quesid and "
			+ "ans1.paperid = ans2.paperid and ans1.isfinal = ans2.isfinal and ans1.teacid <> ans2.teacid and "
			+ "ans1.isfinal <> 1 and ans1.id < ans2.id and ans1.quesid = question.id))) as total, "
			+ "(select count(*) from ustudy.answer where isviewed=1 and answer.quesid = question.id) "
			+ "as marked from question join marktask on marktask.quesid = question.id "
			+ "join teacher on marktask.teacid = teacher.teacid "
			+ "where exam_grade_sub_id = #{egsid} and question.type not in ('单选题', '多选题', '判断题') "
			+ "group by question.id")
	public List<QuesMarkMetrics> getQuesMarkMetricsByEgsId(@Param("eid") int eid, @Param("egsid") int egsid);
	
	@Select("select (select count(*) from examinee join examgradesub on examgradesub.examid = examinee.examid "
			+ "where examinee.paper_status = '1' and examgradesub.id = #{egsid}) as num, "
			+ "sum((select count(*) from examinee join examgradesub on examgradesub.examid = examinee.examid "
			+ "where examinee.paper_status = '1' and examgradesub.id = #{egsid}) * "
			+ "(select if (strcmp(question.mark_mode,'双评'), 1, 2)) + "
			+ "(select if (strcmp(question.mark_mode,'双评'), 0, "
			+ "(select count(if(abs(ans1.score - ans2.score) >=5, 1, null)) from ustudy.answer ans1 "
			+ "cross join ustudy.answer as ans2 where ans1.quesid = ans2.quesid and ans1.paperid = ans2.paperid "
			+ "and ans1.isfinal = ans2.isfinal and ans1.teacid <> ans2.teacid and ans1.isfinal <> 1 and "
			+ "ans1.id < ans2.id and ans1.quesid = question.id)))) as total, "
			+ "sum((select count(*) from ustudy.answer where isviewed=1 and answer.quesid = question.id)) "
			+ "as marked from question where exam_grade_sub_id = #{egsid} and "
			+ "question.type not in ('单选题', '多选题', '判断题')")
	public EgsMarkMetrics getOverallMarkMetrics(@Param("egsid") int egsid);
}
