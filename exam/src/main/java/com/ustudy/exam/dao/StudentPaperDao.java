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
}
