package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.anssheet.AnsPaper;
import com.ustudy.exam.model.anssheet.ExamGrSubMeta;

public interface AnswerSheetService {

	/**
	 * retrieve answer sheet meta information per orgid of login user
	 * @return
	 */
	List<ExamGrSubMeta> getAnswerSheetMeta();
	
	List<AnsPaper> getAnsPapers(long quesid, String type, long clsid, String key);
	
}
