package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.exam.model.analysis.EgsScoreAnaly;
import com.ustudy.exam.model.analysis.ExamBrifeAnaly;
import com.ustudy.exam.model.analysis.QuesObjScoreAnaly;
import com.ustudy.exam.model.analysis.QuesSubScoreAnaly;

@Mapper
public interface AnalysisMapper {

	@Select("select id, examName, group_concat(schname, ';', grs) as schoolDetails from "
			+ "(select id, examName, schname, "
			+ "group_concat(grade_name, '-', classes_num, ':', subs separator '#') as grs from"
			+ "(select exam.id, exam.exam_name as examName, school.schid, school.schname, grade.grade_name, "
			+ "grade.classes_num, group_concat(egs.id, '-', subject.name separator '@') as subs from exam "
			+ "left join examgradesub as egs on egs.examid = exam.id "
			+ "left join grade on grade.id = egs.grade_id "
			+ "left join subject on egs.sub_id = subject.id "
			+ "left join examschool on examschool.examid = exam.id "
			+ "left join school on examschool.schid = school.schid "
			+ "group by exam.id, examschool.schid, egs.grade_id) gsub group by id, schid) ggr "
			+ "group by id")
	public List<ExamBrifeAnaly> getExamBrifeList();
	
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
	
	@Select("SELECT if((question.quesno > 0), question.quesno, concat(question.startno, '-',question.endno)) as quesname, "
			+ "question.score, truncate(avg(ans.score), 1) as aveScore FROM ustudy.answer as ans "
			+ "left join paper on ans.paperid = paper.id "
			+ "left join examgradesub as egs on paper.exam_grade_sub_id = egs.id "
			+ "left join question on (question.exam_grade_sub_id = egs.id and question.id = ans.quesid) "
			+ "where egs.id = #{egsId} group by question.quesno, question.score, quesname "
			+ "order by question.quesno ")
	public List<QuesSubScoreAnaly> calQuesSubReport(@Param("egsId") long egsid, @Param("clsId") long clsId);
	
	@Select("select count(*) as exCount, truncate(avg(score), 2) as aveScore, max(score) as maxScore, "
			+ "min(score) as minScore, (select group_concat(num, '-', score) from "
			+ "(select count(*) as num, score from subscore where exam_grade_sub_id = #{egsId} group by score) "
			+ "scores) as aggrscore from subscore where exam_grade_sub_id = #{egsId}")
	public List<EgsScoreAnaly> calEgsScoreRepport(@Param("egsId") long egsid, @Param("clsId") long clsId);
	
}
