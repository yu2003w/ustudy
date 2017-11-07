package com.ustudy.exam.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.ustudy.exam.model.StudentPaper;

@MapperScan
public interface StudentPaperDao {

	void insertStudentPaper(StudentPaper paper);
	
	void deleteStudentPapers(@Param("csId")Integer csId, @Param("batchNum")Integer batchNum);
}
