package com.ustudy.exam.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.Exam;

@MapperScan
public interface ExamDao {

	List<Exam> getAllExams();
	
	List<Exam> getExams(@Param("finished")Boolean finished, @Param("orgId")String orgId, @Param("gradeId")Long gradeId, @Param("subjectId")Long subjectId, @Param("startDate")String startDate, @Param("endDate")String endDate, @Param("name")String name);
	
	List<Map<String, Object>> getExamGrades(Long examid);
	
	List<Map<String, Object>> getExamSubjects(Long examid);
	
	List<Map<String, Object>> getExamSummary(Long examid);
	
	List<Map<String, Object>> getGradeStudentCounts(Long examid);
	
	List<Map<String, Object>> getSubjectPaperCounts(Long examid);
	
	List<Map<String, Object>> getSubjectQuestions(Long examid);
	
	List<Map<String, Object>> getSubjectAnswers(Long examid);
	
	Long getExamStudengCount(Long examid);
	
	List<Exam> getExamsByStatus(@Param("status") String status, @Param("sid") String sid);
	
	Exam getExamsById(Long id);
	
	Exam getLastExam();

	ArrayList<Map> getGrades();

	ArrayList<Map> getSubjects();

	void updateExamStatus(@Param("examid")Long examid, @Param("status")String status);

}
