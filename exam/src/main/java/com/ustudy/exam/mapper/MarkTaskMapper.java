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
import com.ustudy.exam.model.cache.FirstMarkImgRecord;
import com.ustudy.exam.model.cache.MarkTaskCache;
import com.ustudy.exam.model.cache.PaperScoreCache;
import com.ustudy.exam.model.statics.TeaStatics;
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
	 * when retrieving student paper based on question id request, 1, grab only exam
	 * basic information 2, combine question information into summary
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

	@Select("select ustudy.paper.id as paperid, ustudy.paper.paper_img as img from ustudy.question "
			+ "join ustudy.paper on ustudy.question.exam_grade_sub_id = ustudy.paper.exam_grade_sub_id "
			+ "where ustudy.question.id = #{qid}")
	public List<MarkTaskCache> getPapersByQuesId(@Param("qid") String quesid);

	@Select("select paperid, score, teacid from ustudy.answer where quesid=#{qid} and isviewed=true "
			+ "and isfinal=false")
	public List<PaperScoreCache> getViewedPapersByQuesId(@Param("qid") String quesid);

	@Select("select paperid, score, teacid from ustudy.answer where quesid=#{qid} and isviewed=true "
			+ "and isfinal=true")
	public List<PaperScoreCache> getFinalViewedPapersByQuesId(@Param("qid") String quesid);

	@Select("select paperid, score, teacid from ustudy.answer where quesid=#{qid} and isviewed=true "
			+ "and teacid=#{tid}")
	public List<PaperScoreCache> getPaperScoreByQuesId(@Param("qid") String quesid, @Param("tid") String tid);

	@Select("select quesno, score as fullscore from ustudy.question_step where quesid = #{qid}")
	public List<SingleAnswer> getQuesDiv(@Param("qid") String quesid);

	@Select("select mark_mode from ustudy.question where id = #{qid}")
	public String getMarkMode(@Param("qid") String qid);

	@Select("select file_name as quesImg, pageno, posx, posy, width, height from ustudy.quesarea where "
			+ "quesid = #{qid} order by pageno")
	public List<ImgRegion> getPaperRegion(@Param("qid") String quesid);

	@Select("select teacid, pageno, mark_img as img from ustudy.answer join ustudy.answer_img on "
			+ "ustudy.answer.id = ustudy.answer_img.ans_id where quesid=#{qid} and paperid=#{pid} and "
			+ "ustudy.answer.isfinal = false order by pageno")
	public List<FirstMarkImgRecord> getFirstMarkImgs(@Param("qid") String quesid, @Param("pid") String paperid);

	@Select("select mflag, problem_paper as isProblemPaper, isviewed as isMarked, score, "
			+ "(select paper_img from ustudy.paper where paper.id = paperid) as paperImg, quesid, "
			+ "paperid as paperId from ustudy.answer where quesid=#{qid} and paperid=#{pid} and teacid=#{tid}")
	public BlockAnswer getAnswer(@Param("qid") String quesid, @Param("pid") String pid, @Param("tid") String teacid);

	@Insert("insert into ustudy.answer (quesid, paperid, teacid, mflag, problem_paper, isviewed, isfinal, "
			+ "score) values (#{ba.quesid}, #{ba.paperId}, #{tid}, #{ba.mflag}, #{ba.isProblemPaper}, true, "
			+ "#{ba.isfinal}, #{ba.score}) on duplicate key update mflag=#{ba.mflag}, isfinal=#{ba.isfinal}, "
			+ "problem_paper=#{ba.isProblemPaper}, score=#{ba.score}")
	@Options(useGeneratedKeys = true, keyProperty = "ba.id")
	public int insertAnswer(@Param("ba") BlockAnswer ba, @Param("tid") String teacid);

	@Insert("insert into ustudy.answer_img (mark_img, ans_mark_img, pageno, ans_id) values (#{ir.markImg}, "
			+ "#{ir.ansMarkImg}, #{ir.pageno}, #{ansid}) on duplicate key update mark_img=#{ir.markImg}, "
			+ "ans_mark_img=#{ir.ansMarkImg}")
	public int insertAnsImg(@Param("ir") ImgRegion ir, @Param("ansid") int ansid);

	@Insert("insert into ustudy.answer_step(quesno, score, answer_id) values(#{sa.quesno},#{sa.score},#{aid}) "
			+ "on duplicate key update score=#{sa.score}")
	public int insertAnswerStep(@Param("sa") SingleAnswer sa, @Param("aid") int aid);

	@Select("select id from question where exam_grade_sub_id = (select id from examgradesub "
			+ "where examid=#{examId} and grade_id=#{gradeId} and sub_id=#{subjectId})")
	public List<String> getQuesIdsByExamGradeSub(ExamGradeSub egs);

	@Select("select id from ustudy.question where exam_grade_sub_id=#{id}")
	public List<String> getQuesIdsByExamGradeSubId(@Param("id") String egsid);

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

	@Update("update ustudy.question set assign_mode=#{type}, mark_mode=#{markMode}, duration=#{timeLimit}, "
			+ "teac_owner=#{ownerId} where id=#{questionId}")
	public int updateQuestionMeta(MarkTask mt);

	@Delete("delete from ustudy.marktask where teacid = #{teacid} and quesid = #{quesid} and "
			+ "markrole = #{markrole}")
	public int deleteMetaMarkTaskByTeacher(@Param("teacid") String teac, @Param("quesid") String ques,
			@Param("markrole") String role);

	@Delete("delete from ustudy.marktask where quesid = #{qid} and markrole = #{markrole}")
	public int deleteMetaMarkTaskByQues(@Param("qid") String qid, @Param("markrole") String role);

	// get information for statics
	@Select("select count(*) as marked, sum(score) as score, quesid from answer where isviewed=true and "
			+ "teacid=#{tid} group by quesid")
	public List<TeaStatics> getMarkStaticsByTeaId(@Param("tid") String tid);

}
