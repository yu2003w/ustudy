package com.ustudy.exam.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.StudentPaper;

@MapperScan
public interface StudentPaperDao {

	void insertStudentPaper(StudentPaper paper);
	
	void deleteStudentPapers(@Param("egsId")Long egsId, @Param("batchNum")Integer batchNum);
	
	List<StudentPaper> getStudentPapers(@Param("egsId")Long egsId);
	
	List<StudentPaper> getAnswerPapers(@Param("egsId")Long egsId, @Param("questionId")Long questionId, @Param("classId")Long classId, @Param("type")String type, @Param("text")String text);
}
