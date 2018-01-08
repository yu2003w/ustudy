package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ustudy.exam.model.statics.ScoreClass;
import com.ustudy.exam.model.statics.ScoreSubjectCls;

@Mapper
public interface ScoreMapper {

	/*
	@Select("select ave_score as aveScore, rank, cls_id, subject.name as subjectName from scoreclssubject "
			+ "join examgradesub on examgradesub.id = scoreclssubject.egs_id join subject on "
			+ "subject.id = examgradesub.sub_id where examgradesub.examid = #{eid} and "
			+ "examgradesub.grade_id = #{gid}")
			*/
	public List<ScoreSubjectCls> getScoreSubCls(@Param("eid") int eid, @Param("gid") int gid);
	
	/**
	 * Query average score of class group by subjects based on examination id and grade id
	 * @param eid --- examination id
	 * @param gid --- grade id
	 * @return
	 */
	/*
	 @Select("select avg(score) as aveScore, class.id as clsId, examgradesub.id as egsId FROM ustudy.examinee "
			+ "join ustudy.subscore on examinee.id = subscore.stuid join ustudy.class on "
			+ "class.id = examinee.class_id join examgradesub on examgradesub.id = subscore.exam_grade_sub_id "
			+ "where examgradesub.examid = #{eid} and examgradesub.grade_id = #{gid} group by examinee.class_id, "
			+ "examgradesub.sub_id order by aveScore desc")
			*/
	public List<ScoreSubjectCls> calScoreSubCls(@Param("eid") int eid, @Param("gid") int gid);
	
	public int saveScoreSubCls(ScoreSubjectCls ssc);
	
	/*
	@Select("SELECT ave_score as aveScore, rank, class.cls_name as clsName, class.id as clsId FROM "
			+ "ustudy.scoreclass join class on class.id = scoreclass.cls_id where scoreclass.exam_id = #{eid}")
			*/
	public List<ScoreClass> getScoreClass(@Param("eid") int eid, @Param("gid") int gid);
	
	public List<ScoreClass> calScoreClass(@Param("eid") int eid, @Param("gid") int gid);
	
	
}
