package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.StudentPaper;

@MapperScan
public interface StudentPaperDao {

	void insertStudentPaper(StudentPaper paper);
	
	int getStudentPaperId(@Param("egsId")Long egsId, @Param("examCode")String examCode);
	
	void updatePaperStatus(@Param("paperid")Long paperid, @Param("status")String status);
	
	void deleteStudentPapers(@Param("egsId")Long egsId, @Param("batchNum")Integer batchNum);
	
	List<StudentPaper> getStudentPapers(@Param("egsId")Long egsId);
	
	List<StudentPaper> getAnswerPapers(@Param("egsId")Long egsId, @Param("questionId")Long questionId, @Param("classId")Long classId, @Param("type")String type, @Param("text")String text);
	
	List<Map<String, Object>> getErrorPapers(@Param("egsId")Long egsId, @Param("schId")String schId);
	
}
