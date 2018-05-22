package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.model.score.ChildObjScore;
import com.ustudy.exam.model.score.ChildSubScore;
import com.ustudy.exam.model.MarkImage;
import com.ustudy.exam.model.score.ObjAnswer;
import com.ustudy.exam.model.score.DblAnswer;

@Mapper
public interface ExamSubjectDao {

	List<ExamSubject> getAllExamSubject(@Param("subjectId") Long subjectId, @Param("gradeId") Long gradeId, @Param("start") String start, @Param("end") String end, @Param("examName") String examName);
	
	List<ExamSubject> getAllExamSubjectByExamId(Long examId);
	
	List<ExamSubject> getAllExamSubjectByExamIdAndGradeId(@Param("examId") Long examId, @Param("gradeId") Long gradeId);
	
	List<ExamSubject> getExamSubjectStatus(@Param("examId") Long examId, @Param("templateStatus") String templateStatus, @Param("gradeId") Long gradeId, @Param("markingStatus") String markingStatus);
	
	List<ExamSubject> getExamSubjectByExamIdAndGradeIdAndSubjectId(@Param("examId") Long examId, @Param("gradeId") Long gradeId, @Param("subjectId") Long subjectId);
	
	ExamSubject getExamSubjectById(Long id);

	ExamSubject getMarkSwitchById(Long id);
	
	void saveBlankAnswerPaper(@Param("id")Long id, @Param("fileName") String fileName);
	
	void saveBlankQuestionsPaper(@Param("id")Long id, @Param("fileName") String fileName);

	void saveOriginalData(@Param("id")Long id, @Param("answerPaper")String answerPaper, @Param("xmlServerPath")String xmlServerPath, @Param("originalData") String originalData);
	
	void isAanswerSeted(Long id);

	void updateMarkSwitchById(@Param("egsId")Long egsId, @Param("release")Boolean release);

	void updateMarkSwitch(@Param("examId")Long examId, @Param("gradeId")Long gradeId, @Param("subjectId")Long subjectId, @Param("release")Boolean release);
	
	List<MarkImage> getDblMarkImgs(@Param("egsId")Long egsId);

	void updateDblMarkImgs(@Param("quesId")Long quesId, @Param("paperId")Long paperId, @Param("qareaId")Long qareaId, @Param("markImg")String markImg);

	List<MarkImage> getFinalMarkImgs(@Param("egsId")Long egsId);

	void updateMarkImg(@Param("paperId")Long paperId, @Param("markImg")String markImg);

	void isTaskDispatch(Long id);
    
	void updateExamSubjectStatusById(@Param("egsId")Long egsId, @Param("release")Boolean release);
    
	void updateExamSubjectStatus(@Param("examId")Long examId, @Param("gradeId")Long gradeId, @Param("subjectId")Long subjectId, @Param("release")Boolean release);
	
	List<ChildObjScore> retrieveEgsObjScores(Long egsId);
	
	List<ChildSubScore> retrieveEgsSubScores(Long egsId);
	
	List<Map<String, Object>> getExamSubjectSubjScores(Long egsId);
	
	List<Map<String, Object>> getExamSubjectMarkMode(Long egsId);
	
	long isExamAllSubjectPublished(Long examId);

	List<ObjAnswer> getObjAnsScore(Long paperId);

	List<DblAnswer> getDblAns(Long paperId);
	
}
