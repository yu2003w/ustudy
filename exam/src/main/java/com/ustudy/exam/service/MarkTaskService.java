package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.MarkTask;
import com.ustudy.exam.model.MarkTaskBrife;
import com.ustudy.exam.model.QuesComb;

public interface MarkTaskService {

	public List<MarkTaskBrife> getMarkTaskBrife(String teacid);
	
	public MarkTaskBrife getTaskPapers(String teacid, QuesComb comb);
	
	public String updateMarkResult(List<MarkTask> papers);
}
