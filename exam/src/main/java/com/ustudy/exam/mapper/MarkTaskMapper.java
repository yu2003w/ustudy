package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.exam.model.MetaScoreTask;
import com.ustudy.exam.model.QuesMarkSum;
import com.ustudy.exam.model.SingleAnswer;
import com.ustudy.exam.model.BlockAnswer;
import com.ustudy.exam.model.MarkTask;
import com.ustudy.exam.model.MarkTaskBrife;

@Mapper
public interface MarkTaskMapper {

	@Select("select * from ustudy.teacherscoretask where teacid = #{tid}")
	public List<MetaScoreTask> getMetaScoreTask(@Param("tid") String teacid);
	
	@Select("select scoretype from ustudy.teacherscoretask where teacid = #{tid} and quesid = #{qid}")
	public String getMarkType(@Param("tid") String teacid, @Param("qid") String quesid);
	
	//@Select("select exam_id as examId, exam_name as examName, sub_name as subject, grade_name as grade from "
	//		+ "ustudy.examgradesub where id = (select exam_grade_sub_id from ustudy.quesanswer where id = #{qid}")
	@Select("select examid as examId, exam_name as examName, sub_name as subject, grade_name as grade, quesno, "
			+ "startno, endno, posx, posy, height, length, type as questionType, mark_mode as scoreMode from "
			+ "ustudy.examgradesub join ustudy.quesanswer on ustudy.examgradesub.id = "
			+ "ustudy.quesanswer.exam_grade_sub_id where ustudy.examgradesub.id = (select exam_grade_sub_id "
			+ "from ustudy.quesanswer where id = #{qid})")
	public MarkTask getScoreTask(@Param("qid") String quesid);
	
	@Select("select examid as examId, exam_name as examName, sub_name as subject, grade_name as grade, quesno, "
			+ "startno, endno, type as quesType from ustudy.examgradesub join ustudy.quesanswer on "
			+ "ustudy.examgradesub.id = ustudy.quesanswer.exam_grade_sub_id where ustudy.examgradesub.id "
			+ "= (select exam_grade_sub_id from ustudy.quesanswer where id = #{qid}) and ustudy.quesanswer.id = #{qid}")
	public MarkTaskBrife getMarkTaskBrife(@Param("qid") String quesid);
	
	
	/*
	 * when retrieving student paper based on question id request, 
	 * 1, grab only exam basic information
	 * 2, combine question information into summary
	 * 
	 */
	@Select("select examid as examId, exam_name as examName, sub_name as subject, grade_name as grade from "
			+ "ustudy.examgradesub join ustudy.quesanswer on ustudy.examgradesub.id = "
			+ "ustudy.quesanswer.exam_grade_sub_id where ustudy.examgradesub.id = "
			+ "(select exam_grade_sub_id from ustudy.quesanswer where id = #{qid}) and ustudy.quesanswer.id = #{qid}")
	public MarkTaskBrife getMetaTaskInfo(@Param("qid") String queid);
	
	@Select("select id as quesid, quesno, startno, endno, type as questionType, mark_mode as markMode, score as "
			+ "fullscore from ustudy.quesanswer where id = #{qid}")
	public QuesMarkSum getQuesSum(@Param("qid") String queid);
	
	@Select("select ustudy.stupaper.id from ustudy.quesanswer join ustudy.stupaper on "
			+ "ustudy.quesanswer.exam_grade_sub_id = ustudy.stupaper.exam_grade_sub_id "
			+ "where ustudy.quesanswer.id = #{qid}")
	public List<String> getPapersByQuesId(@Param("qid") String quesid);
	
	@Select("select quesno, score as fullscore from quesanswerdiv where quesid = #{qid}")
	public List<SingleAnswer> getQuesDiv(@Param("qid") String quesid);
	
	@Select("select isviewed as isMarked, mflag, isviewed as isProbP, answerImg, comment11 as markImg, quesid,"
			+ " paperid as paperId from ustudy.stuanswer where quesid = #{qid} and paperid = #{pid};")
	public BlockAnswer getStuAnswer(@Param("qid") String quesid, @Param("pid") String pid);
	
}
