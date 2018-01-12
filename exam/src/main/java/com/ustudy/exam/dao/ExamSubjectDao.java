package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.ExamSubject;

@MapperScan
public interface ExamSubjectDao {

	List<ExamSubject> getAllExamSubject(@Param("subjectId") Long subjectId, @Param("gradeId") Long gradeId, @Param("start") String start, @Param("end") String end, @Param("examName") String examName);
	
	List<ExamSubject> getAllExamSubjectByExamId(Long examId);
	
	List<ExamSubject> getAllExamSubjectByExamIdAndGradeId(@Param("examId") Long examId, @Param("gradeId") Long gradeId);
	
	List<ExamSubject> getExamSubjectStatus(@Param("examId") Long examId, @Param("templateStatus") String templateStatus, @Param("gradeId") Long gradeId, @Param("markingStatus") String markingStatus);
	
	List<ExamSubject> getExamSubjectByExamIdAndGradeIdAndSubjectId(@Param("examId") Long examId, @Param("gradeId") Long gradeId, @Param("subjectId") Long subjectId);
	
	ExamSubject getExamSubjectById(Long id);
	
	void saveBlankAnswerPaper(@Param("id")Long id, @Param("fileName") String fileName);
	
	void saveBlankQuestionsPaper(@Param("id")Long id, @Param("fileName") String fileName);

	void saveOriginalData(@Param("id")Long id, @Param("answerPaper")String answerPaper, @Param("xmlServerPath")String xmlServerPath, @Param("originalData") String originalData);
	
	void isAanswerSeted(Long id);
	
	void isTaskDispatch(Long id);
    
	void updateExamSubjectStatusById(@Param("egsId")Long egsId, @Param("release")Boolean release);
    
	void updateExamSubjectStatus(@Param("examId")Long examId, @Param("gradeId")Long gradeId, @Param("subjectId")Long subjectId, @Param("release")Boolean release);
	
	List<Map<String, Object>> getExamSubjectObjScores(Long egsId);
	
	List<Map<String, Object>> getExamSubjectSubjScores(Long egsId);
	
	List<Map<String, Object>> getExamSubjectMarkMode(Long egsId);
	
	long isExamAllSubjectPublished(Long examId);
	
}
