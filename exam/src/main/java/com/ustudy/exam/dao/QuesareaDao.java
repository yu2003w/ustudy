package com.ustudy.exam.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Quesarea;

@MapperScan
public interface QuesareaDao {

	void insertQuesarea(Quesarea quesarea);
	
	void insertQuesareas(List<Quesarea> quesareas);
	
	List<Quesarea> getQuesareas(Long quesid);
	
}
