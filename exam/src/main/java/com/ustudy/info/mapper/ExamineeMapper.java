package com.ustudy.info.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ustudy.info.model.ClassInfo;
import com.ustudy.info.model.Examinee;
import com.ustudy.info.model.ExamineeSub;

@Mapper
public interface ExamineeMapper {

	int createExaminee(Examinee exs);
	
	int createExamineeSubs(List<ExamineeSub> eeSubs);
	
	int deleteExaminee(long id);
	
	Examinee getExamineeById(long id);
	
	int saveClsInfo(ClassInfo ci);
	
	List<Examinee> getExamineeByFilter(@Param("examid") long examid, @Param("gradeid") long gradeid, 
			@Param("clsid") long clsid, @Param("key") String key);
	
	List<Examinee> getAbsentListPerEgs(long egsid);
	
	List<Examinee> getExamineeByClient(@Param("examid") long examid, @Param("gradeid") long gradeid);
	
}
