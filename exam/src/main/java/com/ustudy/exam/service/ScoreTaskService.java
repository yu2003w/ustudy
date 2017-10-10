package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.ScoreTask;

public interface ScoreTaskService {

	public List<ScoreTask> getScoreTask(String teacid);
	
}
