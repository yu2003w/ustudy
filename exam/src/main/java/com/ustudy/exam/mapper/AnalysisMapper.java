package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.exam.model.analysis.QuesObjScoreAnaly;
import com.ustudy.exam.model.analysis.QuesSubScoreAnaly;

@Mapper
public interface AnalysisMapper {

	@Select("select id, group_concat(schid, '-', schname, ';', grs) as sch from "
			+ "(select id, schid, schname, "
			+ "group_concat(grade_id, '-', grade_name, ':', subs separator '#') as grs from "
			+ "(select exam.id, exam.exam_name, examschool.schid, school.schname, egs.grade_id, "
			+ "grade.grade_name, group_concat(egs.sub_id, '-', subject.name separator '$') as subs from exam "
			+ "left join examgradesub as egs on egs.examid = exam.id "
			+ "left join grade on grade.id = egs.grade_id "
			+ "left join subject on egs.sub_id = subject.id "
			+ "left join examschool on examschool.examid = exam.id "
			+ "left join school on examschool.schid = school.schid "
			+ "group by exam.id, examschool.schid, egs.grade_id) sub1 group by id, schid) gr1 group by id")
	public String getExamBrifeList();
	
	@Select("select quesno, score, truncate((sum(ss)/sum(num)), 2) as aveScore, sum(num) as total, "
			+ "group_concat(answer, '-', num) opts from "
			+ "(select obj_answer.quesno, question.score, sum(obj_answer.score) as ss, obj_answer.answer, "
			+ "count(obj_answer.answer) as num from obj_answer "
			+ "left join paper on obj_answer.paperid = paper.id "
			+ "left join examgradesub as egs on paper.exam_grade_sub_id = egs.id "
			+ "left join question on (question.exam_grade_sub_id = egs.id and "
			+ "question.startno <= obj_answer.quesno and question.endno >= obj_answer.quesno) "
			+ "where egs.id = #{egsId} group by quesno, question.id, obj_answer.answer) tb1 group by quesno, score")
	public List<QuesObjScoreAnaly> calQuesObjReport(@Param("egsId")long egsId, @Param("clsId") long clsId);
	
	@Select("")
	public List<QuesSubScoreAnaly> calQuesSubReport(long egsid);
	
}
