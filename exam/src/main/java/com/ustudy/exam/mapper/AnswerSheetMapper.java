package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ustudy.exam.model.answersheet.ExamGrSubMeta;

@Mapper
public interface AnswerSheetMapper {
	
	List<ExamGrSubMeta> getAnswerSheetMeta(String orgid);

}
