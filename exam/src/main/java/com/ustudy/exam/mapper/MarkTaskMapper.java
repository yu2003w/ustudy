package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ustudy.exam.model.MetaMarkTask;
import com.ustudy.exam.model.QuesMarkSum;
import com.ustudy.exam.model.SingleAnswer;
import com.ustudy.exam.model.BlockAnswer;
import com.ustudy.exam.model.ExamGradeSub;
import com.ustudy.exam.model.MarkTask;
import com.ustudy.exam.model.MarkTaskBrife;

@Mapper
public interface MarkTaskMapper {

	@Select("select * from ustudy.marktask where teacid = #{tid}")
	public List<MetaMarkTask> getMetaMarkTask(@Param("tid") String teacid);
	
	@Select("select scoretype from ustudy.marktask where teacid = #{tid} and quesid = #{qid}")
	public String getMarkType(@Param("tid") String teacid, @Param("qid") String quesid);
	
	/*
	 * this api is not needed any more.
	 * keep it here in case it is required later.
	@Select("select examid as examId, exam_name as examName, sub_name as subject, grade_name as grade, quesno, "
			+ "startno, endno, posx, posy, height, length, type as questionType, mark_mode as scoreMode from "
			+ "ustudy.examgradesub join ustudy.question on ustudy.examgradesub.id = "
			+ "ustudy.question.exam_grade_sub_id where ustudy.examgradesub.id = (select exam_grade_sub_id "
			+ "from ustudy.question where id = #{qid})")
	public MarkTask getMarkTask(@Param("qid") String quesid);
	*/
	
	@Select("select examid as examId, exam_name as examName, sub_name as subject, grade_name as grade, quesno, "
			+ "startno, endno, type as quesType from ustudy.examgradesub join ustudy.question on "
			+ "ustudy.examgradesub.id = ustudy.question.exam_grade_sub_id where ustudy.examgradesub.id "
			+ "= (select exam_grade_sub_id from ustudy.question where id = #{qid}) and ustudy.question.id = #{qid}")
	public MarkTaskBrife getMarkTaskBrife(@Param("qid") String quesid);
	
	
	/*
	 * when retrieving student paper based on question id request, 
	 * 1, grab only exam basic information
	 * 2, combine question information into summary
	 * 
	 */
	@Select("select examid as examId, exam_name as examName, sub_name as subject, grade_name as grade from "
			+ "ustudy.examgradesub join ustudy.question on ustudy.examgradesub.id = "
			+ "ustudy.question.exam_grade_sub_id where ustudy.examgradesub.id = "
			+ "(select exam_grade_sub_id from ustudy.question where id = #{qid}) and ustudy.question.id = #{qid}")
	public MarkTaskBrife getMetaTaskInfo(@Param("qid") String queid);
	
	@Select("select id as quesid, quesno, startno, endno, type as questionType, mark_mode as markMode, score as "
			+ "fullscore from ustudy.question where id = #{qid}")
	public QuesMarkSum getQuesSum(@Param("qid") String queid);
	
	@Select("select ustudy.stupaper.id from ustudy.question join ustudy.stupaper on "
			+ "ustudy.question.exam_grade_sub_id = ustudy.stupaper.exam_grade_sub_id "
			+ "where ustudy.question.id = #{qid}")
	public List<String> getPapersByQuesId(@Param("qid") String quesid);
	
	@Select("select quesno, score as fullscore from quesanswerdiv where quesid = #{qid}")
	public List<SingleAnswer> getQuesDiv(@Param("qid") String quesid);
	
	@Select("select isviewed as isMarked, mflag, paper_img as paperImg, answer_img1 as answerImg1, mark_img1 as "
			+ "markImg1, answer_img2 as answerImg2, mark_img2 as markImg2, answer_img3 as answerImg3, mark_img3 as "
			+ "markImg3, quesid, paperid as paperId from ustudy.stuanswer where quesid = #{qid} and paperid = #{pid};")
	public BlockAnswer getStuAnswer(@Param("qid") String quesid, @Param("pid") String pid);
	
	
	@Update("update ustudy.stuanswer set mflag=#{mflag}, score1=#{score1}, score1=#{score2}, score1=#{score3}, "
			+ "isviewed=true, answer_img1=#{answerImg1}, mark_img1=#{markImg1}, answer_img2=#{answerImg2}, "
			+ "mark_img2=#{markImg2}, answer_img3=#{answerImg3}, mark_img3=#{markImg3}, teacid1=#{teacid1}, "
			+ "teacid2=#{teacid2}, teacid3=#{teacid3} where id=#{quesid}")
	public int updateStuAnswer(BlockAnswer ba);
	
	@Insert("insert into ustudy.stuanswerdiv(quesno, score, answer_id) values(#{sa.quesno},#{sa.score},#{aid})")
	public int insertStuAnswerDiv(@Param("sa") SingleAnswer sa, @Param("aid") String aid);
	
	@Select("select id from ustudy.stuanswer where quesid=#{qid} and paperid=#{pid}")
	public String getStuanswerId(@Param("pid") int pid, @Param("qid") String qid);
	
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
	
	
	// meta mark task related functions
	
	@Insert("insert into ustudy.marktask(teacid, quesid, threshold, scoretype, markrole) "
			+ "values(#{teacid}, #{quesid}, #{threshold}, #{markType}, #{markrole})")
	public int populateMetaMarkTask(MetaMarkTask mmt);
	
	
	@Delete("delete from ustudy.marktask where teacid = #{teacid} and quesid = #{quesid} and "
			+ "markrole = #{markrole}")
	public int deleteMetaMarkTask(@Param("teacid") String teac, @Param("quesid") String ques, 
			@Param("markrole") String role);
	
	@Update("update ustudy.question set duration = #{tl} where id = #{qid}")
	public int setTimeLimit(@Param("tl") int timeLimit, @Param("qid") String qid);
	
}
