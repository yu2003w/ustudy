package com.ustudy.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ustudy.exam.model.ImgRegion;
import com.ustudy.exam.model.SingleAnswer;

@Mapper
public interface AnswerMapper {

	int saveAnswerSteps(@Param("saL") List<SingleAnswer> saL, @Param("ansid") Long ansId);
	
	int saveAnswerImgs(@Param("irL") List<ImgRegion> irL, @Param("ansid") Long ansId);
	
}
