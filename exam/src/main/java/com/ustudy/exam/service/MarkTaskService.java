package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.MarkTask;

public interface MarkTaskService {

	public List<MarkTask> getScoreTask(String teacid);
	
}
