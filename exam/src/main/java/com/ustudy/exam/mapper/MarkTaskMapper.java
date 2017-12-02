package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ustudy.exam.model.MetaMarkTask;
import com.ustudy.exam.model.QuesMarkSum;
import com.ustudy.exam.model.SingleAnswer;
import com.ustudy.exam.model.cache.MarkTaskCache;
import com.ustudy.exam.model.BlockAnswer;
import com.ustudy.exam.model.ExamGradeSub;
import com.ustudy.exam.model.ImgRegion;
import com.ustudy.exam.model.MarkTask;
import com.ustudy.exam.model.MarkTaskBrife;

@Mapper
public interface MarkTaskMapper {

	@Select("select * from ustudy.marktask where teacid = #{tid}")
	public List<MetaMarkTask> getMetaMarkTask(@Param("tid") String teacid);
	
	@Select("select marktype from ustudy.marktask where teacid = #{tid} and quesid = #{qid}")
	public String getMarkType(@Param("tid") String teacid, @Param("qid") String quesid);
	
	@Select("select examid as examId, (select exam_name from ustudy.exam where ustudy.exam.id = examid) as "
			+ "examName, (select name from ustudy.subject where ustudy.subject.id = sub_id) as subject, "
			+ "(select grade_name from ustudy.grade where ustudy.grade.id = grade_id) as grade, quesno, "
			+ "startno, endno, type as quesType from ustudy.examgradesub join ustudy.question on "
			+ "ustudy.examgradesub.id = ustudy.question.exam_grade_sub_id where ustudy.examgradesub.id = "
			+ "(select exam_grade_sub_id from ustudy.question where id = #{qid}) and ustudy.question.id = #{qid}")
	public MarkTaskBrife getMarkTaskBrife(@Param("qid") String quesid);
	
	
	/*
	 * when retrieving student paper based on question id request, 
	 * 1, grab only exam basic information
	 * 2, combine question information into summary
	 * 
	 */
	@Select("select examid as examId, (select exam_name from ustudy.exam where ustudy.exam.id = examid) "
			+ "as examName, (select name from ustudy.subject where ustudy.subject.id = sub_id) as subject, "
			+ "(select grade_name from ustudy.grade where ustudy.grade.id = grade_id) as grade from "
			+ "ustudy.examgradesub join ustudy.question on ustudy.examgradesub.id = ustudy.question.exam_grade_sub_id"
			+ " where ustudy.examgradesub.id = (select exam_grade_sub_id from ustudy.question where id = #{qid}) "
			+ "and ustudy.question.id = #{qid}")
	public MarkTaskBrife getMetaTaskInfo(@Param("qid") String queid);
	
	@Select("select id as quesid, quesno, startno, endno, type as questionType, assign_mode as assignMode,"
			+ "mark_mode as markMode, score as fullscore from ustudy.question where id = #{qid}")
	public QuesMarkSum getQuesSum(@Param("qid") String queid);
	
	@Select("select ustudy.paper.id as paperid, ustudy.paper.paper_img as img from ustudy.question join "
			+ "ustudy.paper on ustudy.question.exam_grade_sub_id = ustudy.paper.exam_grade_sub_id "
			+ "where ustudy.question.id = #{qid}")
	public List<MarkTaskCache> getPapersByQuesId(@Param("qid") String quesid);
	
	@Select("select paperid from ustudy.answer where quesid=#{qid} and isviewed=true")
	public List<String> getViewedPapersByQuesId(@Param("qid") String quesid);
	
	@Select("select quesno, score as fullscore from ustudy.question_step where quesid = #{qid}")
	public List<SingleAnswer> getQuesDiv(@Param("qid") String quesid);
	
	@Select("select file_name as quesImg, pageno, posx, posy, width, height from ustudy.quesarea where "
			+ "quesid = #{qid} order by pageno")
	public List<ImgRegion> getPaperRegion(@Param("qid") String quesid);
	
	@Select("select isviewed as isMarked, mflag, (select paper_img from ustudy.paper where paper.id = paperid) as "
			+ "paperImg, quesid, paperid as paperId from ustudy.answer where quesid = #{qid} and paperid = #{pid}")
	public BlockAnswer getAnswer(@Param("qid") String quesid, @Param("pid") String pid);
	
	@Insert("insert into ustudy.answer (quesid, paperid, mflag, score1, score2, score3, isviewed, teacid1, "
			+ "teacid2, teacid3) values (#{quesid}, #{paperId}, #{mflag}, #{score1}, #{score2}, #{score3}, true,"
			+ " #{teacid1}, #{teacid2}, #{teacid3}) on duplicate key update mflag=#{mflag}, score1=#{score1}, "
			+ "score2=#{score2}, score3=#{score3}, teacid1=#{teacid1}, teacid2=#{teacid2}, teacid3=#{teacid3}")
	@Options(useGeneratedKeys=true)
	public int insertAnswer(BlockAnswer ba);
	
	@Insert("insert into ustudy.answer_img (mark_img, ans_mark_img, pageno, ans_id, teacid) values (#{ir.markImg}, "
			+ "#{ir.ansMarkImg}, #{ir.pageno}, #{ansid}, #{teacid}) on duplicate key update mark_img=#{ir.markImg}, "
			+ "ans_mark_img=#{ir.ansMarkImg}")
	public int insertAnsImg(@Param("ir") ImgRegion ir, @Param("ansid") int ansid, @Param("teacid") String teacid);
	
	@Insert("insert into ustudy.answer_step(quesno, score, answer_id) values(#{sa.quesno},#{sa.score},#{aid}) "
			+ "on duplicate key update score=#{sa.score}")
	public int insertAnswerStep(@Param("sa") SingleAnswer sa, @Param("aid") int aid);
	
	@Select("select id from question where exam_grade_sub_id = (select id from examgradesub "
			+ "where examid=#{examId} and grade_id=#{gradeId} and sub_id=#{subjectId})")
	public List<String> getQuesIdsByExamGradeSub(ExamGradeSub egs);
	
	@Select("select distinct teac_owner as ownerId, assign_mode as type, duration as timeLimit, mark_mode as markMode"
			+ " from question join ustudy.marktask on question.id = ustudy.marktask.quesid where "
			+ "question.id=#{qid}")
	public MarkTask getAllMarkTasksByQuesId(@Param("qid") String qid);
	
	@Select("select teacid from ustudy.marktask where quesid = #{qid} and markrole = #{role}")
	public List<String> getTeachersByQidRole(@Param("qid") String qid, @Param("role") String markRole);
	
	@Select("select teacid from ustudy.marktask where quesid = #{qid}")
	public List<String> getTeachersByQid(@Param("qid") String qid);
	
	@Select("select markrole from ustudy.marktask where quesid=#{qid} and teacid=#{teacid}")
	public String getMarkRole(@Param("qid") String quesid, @Param("teacid") String teacid);
	
	// meta mark task related functions
	
	@Insert("insert into ustudy.marktask(teacid, quesid, threshold, marktype, markrole) "
			+ "values(#{teacid}, #{quesid}, #{threshold}, #{marktype}, #{markrole})")
	public int populateMetaMarkTask(MetaMarkTask mmt);
	
	
	@Delete("delete from ustudy.marktask where teacid = #{teacid} and quesid = #{quesid} and "
			+ "markrole = #{markrole}")
	public int deleteMetaMarkTaskByTeacher(@Param("teacid") String teac, @Param("quesid") String ques, 
			@Param("markrole") String role);
	
	@Delete("delete from ustudy.marktask where quesid = #{qid} and markrole = #{markrole}")
	public int deleteMetaMarkTaskByQues(@Param("qid") String qid, @Param("markrole") String role);
	
	@Update("update ustudy.question set duration = #{tl} where id = #{qid}")
	public int setTimeLimit(@Param("tl") int timeLimit, @Param("qid") String qid);
	
}
