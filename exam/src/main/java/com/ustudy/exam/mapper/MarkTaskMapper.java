package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.exam.model.MetaScoreTask;
import com.ustudy.exam.model.MarkTask;
import com.ustudy.exam.model.MarkTaskBrife;

@Mapper
public interface MarkTaskMapper {

	@Select("select * from ustudy.teacherscoretask where teacid = #{tid}")
	public List<MetaScoreTask> getMetaScoreTask(@Param("tid") String teacid);
	
	//@Select("select exam_id as examId, exam_name as examName, sub_name as subject, grade_name as grade from "
	//		+ "ustudy.examgradesub where id = (select exam_grade_sub_id from ustudy.quesanswer where id = #{qid}")
	@Select("select examid as examId, exam_name as examName, sub_name as subject, grade_name as grade, quesno, "
			+ "startno, endno, posx, posy, height, length, type as questionType, score_mode as scoreMode from "
			+ "ustudy.examgradesub join ustudy.quesanswer on ustudy.examgradesub.id = "
			+ "ustudy.quesanswer.exam_grade_sub_id where ustudy.examgradesub.id = (select exam_grade_sub_id "
			+ "from ustudy.quesanswer where id = #{qid})")
	public MarkTask getScoreTask(@Param("qid") String quesid);
	
	@Select("select examid as examId, exam_name as examName, sub_name as subject, grade_name as grade, quesno, "
			+ "startno, endno, type as quesType from ustudy.examgradesub join ustudy.quesanswer on "
			+ "ustudy.examgradesub.id = ustudy.quesanswer.exam_grade_sub_id where ustudy.examgradesub.id "
			+ "= (select exam_grade_sub_id from ustudy.quesanswer where id = #{qid}) and ustudy.quesanswer.id = #{qid}")
	public MarkTaskBrife getMarkTaskBrife(@Param("qid") String quesid);
	
}
