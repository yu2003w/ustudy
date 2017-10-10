package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ustudy.exam.model.ExamResult;

@Mapper
public interface ClientMapper {

	@Select("select id, stuname, stuno, score from examresult where id > 0 limit 10000")
	public List<ExamResult> saveTemplates();
}
