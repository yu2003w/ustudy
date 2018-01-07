package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ustudy.exam.model.statics.ScoreSubjectCls;

@Mapper
public interface ScoreMapper {

	@Select("select examgradesub.id as egsId, subject.name from ustudy.examgradesub join ustudy.subject on "
			+ "subject.id = examgradesub.sub_id where examid=10 and grade_id=18;")
	List<ScoreSubjectCls> getScoreSubCls(@Param("eid") int eid, @Param("gid") int gid);
	
}
