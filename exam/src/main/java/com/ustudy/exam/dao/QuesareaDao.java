package com.ustudy.exam.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Quesarea;

@MapperScan
public interface QuesareaDao {

	void insertQuesarea(Quesarea quesarea);
	
}
