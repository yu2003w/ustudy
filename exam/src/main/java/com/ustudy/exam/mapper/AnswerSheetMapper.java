package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ustudy.exam.model.anssheet.AnsPaper;
import com.ustudy.exam.model.anssheet.ExamGrSubMeta;

@Mapper
public interface AnswerSheetMapper {
	
	List<ExamGrSubMeta> getAnswerSheetMeta(String orgid);
	
	/**
	 * @param quesid
	 * @param type    ---   indicate best, faq or other
	 * @param clsid
	 * @param key     ---   filter, key could be eecode or eename
	 * @return
	 */
	List<AnsPaper> getAnsPapers(@Param("qid") long quesid, @Param("mflag") String type, 
			@Param("clsid") long clsid, @Param("key") String key);

}
