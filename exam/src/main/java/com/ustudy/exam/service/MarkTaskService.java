package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.MarkTaskBrife;
import com.ustudy.exam.model.QuesComb;
import com.ustudy.exam.model.QuestionPaper;

public interface MarkTaskService {

	public List<MarkTaskBrife> getMarkTaskBrife(String teacid);
	
	public MarkTaskBrife getTaskPapers(String teacid, QuesComb comb);
	
	/*
	 * each time, only one student's paper need to be updated afer mark completed
	 * 
	 */
	public boolean updateMarkResult(QuestionPaper qp);
	
}
