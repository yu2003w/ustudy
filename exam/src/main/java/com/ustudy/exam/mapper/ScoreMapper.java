package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ustudy.exam.model.statics.ScoreClass;
import com.ustudy.exam.model.statics.ScoreSubjectCls;

/**
 * @author jared
 * 
 * Real sql statement for this mapper is under directory resource in ClassScoreMapper.xml
 *
 */
@Mapper
public interface ScoreMapper {

	public List<ScoreSubjectCls> getScoreSubCls(@Param("eid") int eid, @Param("gid") int gid);
	
	/**
	 * Query average score of class group by subjects based on examination id and grade id
	 * @param eid --- examination id
	 * @param gid --- gid <=0 indicates all grades info required
	 * @return
	 */
	public List<ScoreSubjectCls> calScoreSubCls(@Param("eid") int eid, @Param("gid") int gid);
	
	public int saveScoreSubCls(List<ScoreSubjectCls> ssc);
	
	public List<ScoreClass> getScoreClass(@Param("eid") int eid, @Param("gid") int gid);
	
	public List<ScoreClass> calScoreClass(@Param("eid") int eid, @Param("gid") int gid);
	
	public int saveScoreClass(List<ScoreClass> sc);
	
}
