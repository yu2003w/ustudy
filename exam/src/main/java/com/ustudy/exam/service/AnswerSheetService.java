package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.answersheet.ExamGrSubMeta;

public interface AnswerSheetService {

	/**
	 * retrieve answer sheet meta information per orgid of login user
	 * @return
	 */
	List<ExamGrSubMeta> getAnswerSheetMeta();
	
}
