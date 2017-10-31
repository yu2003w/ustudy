package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.MarkTask;
import com.ustudy.exam.model.MarkTaskBrife;

public interface MarkTaskService {

	public List<MarkTaskBrife> getMarkTaskBrife(String teacid);
	
	public List<MarkTask> getTaskPapers(String teacid);
	
	public String updateMarkResult(List<MarkTask> papers);
}
