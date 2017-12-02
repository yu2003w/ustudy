package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.StudentPaper;

@MapperScan
public interface StudentPaperDao {

	void insertStudentPaper(StudentPaper paper);
	
	void deleteStudentPapers(@Param("csId")Long csId, @Param("batchNum")Integer batchNum);
	
	List<StudentPaper> getStudentPapers(@Param("csId")Long csId);
	
	List<StudentPaper> getAnswerPapers(@Param("egsId")Long egsId, @Param("questionId")Long questionId, @Param("classId")Long classId, @Param("type")String type, @Param("text")String text);
}
