package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.exam.model.MetaScoreTask;
import com.ustudy.exam.model.ScoreTask;

@Mapper
public interface ScoreTaskMapper {

	@Select("select * from ustudy.teacherscoretask where teacid = #{tid}")
	public List<MetaScoreTask> getMetaScoreTask(@Param("tid") String teacid);
	
	//@Select("select exam_id as examId, exam_name as examName, sub_name as subject, grade_name as grade from "
	//		+ "ustudy.examgradesub where id = (select exam_grade_sub_id from ustudy.quesanswer where id = #{qid}")
	@Select("select examid as examId, exam_name as examName, sub_name as subject, grade_name as grade, quesno"
			+ " as questionNum, type as questionType, score_mode as scoreMode from ustudy.examgradesub join "
			+ "ustudy.quesanswer on ustudy.examgradesub.id = ustudy.quesanswer.exam_grade_sub_id where "
			+ "ustudy.examgradesub.id = (select exam_grade_sub_id from ustudy.quesanswer where id = #{qid}")
	public ScoreTask getScoreTask(@Param("qid") String quesid);
	
}
