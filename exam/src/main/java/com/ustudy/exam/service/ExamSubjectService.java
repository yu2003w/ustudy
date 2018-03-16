package com.ustudy.exam.service;

import java.util.List;

import com.ustudy.exam.model.ExamSubject;

public interface ExamSubjectService {
	
	List<ExamSubject> getExamSubjects(Long subjectId, Long gradeId, String start, String end, String examName);
	
	List<ExamSubject> getExamSubjects(Long examId);
	
	List<ExamSubject> getExamSubjects(Long examId, Long gradeId);
	
	List<ExamSubject> getExamSubjects(Long examId, Long gradeId, Long subjectId);
	
	ExamSubject getExamSubject(Long id);
	
	List<ExamSubject> getLastExamSubjects();
	
	boolean updateExamSubjectStatus(Long egsId, Boolean release);
	
	boolean updateExamSubjectStatus(Long examId, Long gradeId, Long subjectId, Boolean release);

	boolean updateMarkSwitch(Long egsId, Boolean release);
	
	boolean updateMarkSwitch(Long examId, Long gradeId, Long subjectId, Boolean release);

	boolean saveBlankAnswerPaper(Long id, String fileName);
	
	boolean saveBlankQuestionsPaper(Long id, String fileName);
	
	boolean getMarkSwitchById(Long id);

	/**
	 * 
	 * check related records to find out whether answer is set or not
	 * Not a good design to determine that depends on field in table of database
	 * 
	 * @param id
	 * @return
	 */
	boolean isAnswerSet(Long id);
	
	/**
	 * check related records to find out whether mark task is dispatched or not
	 * Not a good design to determine that depends on field in table of database as 
	 * settings could be changed at runtime.
	 * 
	 * @param id
	 * @return
	 */
	boolean isMarkTaskDispatched(int id);
	
}
