package com.ustudy.exam.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Exam;

@MapperScan
public interface ExamDao {

	List<Exam> getAllExams(String orgid);
	
	List<Exam> getExams(@Param("finished")Boolean finished, @Param("orgId")String orgId, @Param("gradeId")Long gradeId, @Param("subjectId")Long subjectId, @Param("startDate")String startDate, @Param("endDate")String endDate, @Param("name")String name);
	
	List<Map<String, Object>> getExamGrades(Long examid);
	
	List<Map<String, Object>> getExamSubjects(Long examid);
	
	List<Map<String, Object>> getExamSummary(Long examid);
	
	List<Map<String, Object>> getGradeStudentCounts(Long examid);

	List<Map<String, Object>> getEgsStudentCounts(@Param("examid")long examid, @Param("artMathCount")long artMathCount);	

	Long getArtMathCount(Long examid);	

	Long getBranchCount(Long examid);	
	
	List<Map<String, Object>> getSubjectPaperCounts(Long examid);
	
	List<Map<String, Object>> getSubjectQuestions(Long examid);
	
	List<Map<String, Object>> getSubjectAnswers(Long examid);
	
	List<Map<String, Object>> getTeacherExams(String orgId);
	
	List<Map<String, Object>> getSubjectQuestionPapers(@Param("egsId")long egsId, @Param("quesId")long quesId);
	
	List<Map<String, Object>> getSubjectQuestionMarkImgs(@Param("egsId")long egsId, @Param("quesId")long quesId);
	
	Long getExamStudengCount(Long examid);
	
	void updateExamStatusByEgsid(@Param("egsId")Long egsId,@Param("status") String status);
	
	List<Exam> getExamsByStatus(@Param("status") String status, @Param("sid") String sid);
	
	Exam getExamsById(Long id);
	
	Exam getLastExam();

	ArrayList<Map<String, Object>> getGrades();

	ArrayList<Map<String, Object>> getSubjects();

	void updateExamStatus(@Param("examid")Long examid, @Param("status")String status);

	void updateEgsStatus(@Param("examid")Long examid, @Param("status")String status);

}
