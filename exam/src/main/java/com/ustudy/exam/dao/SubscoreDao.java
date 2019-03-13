package com.ustudy.exam.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ustudy.exam.model.score.ExameeSubScore;
import com.ustudy.exam.model.score.ObjQuesScore;
import com.ustudy.exam.model.score.SubChildScore;
import com.ustudy.exam.model.score.SubScore;
import com.ustudy.exam.model.score.SubjectQuesScore;
import com.ustudy.exam.model.score.PaperSubScore;

@Mapper
public interface SubscoreDao {

	void deleteSubscores(Long egsId);
	
	int saveSubscores(List<SubScore> subscores);
	
	int saveSubChildScores(List<SubChildScore> scores);
	
	List<Long> getSSIDsByEgsId(long egsid);
	
	ExameeSubScore getExameeSubScores(@Param("exameeId")Long exameeId, @Param("examId")Long examId);
	
	List<ObjQuesScore> getObjQuesScore(@Param("exameeNO") String exameeNO, @Param("egsId") long egsId);
	
	List<SubjectQuesScore> getSubQuesScore(@Param("exameeNO") String exameeNo, @Param("egsId") long egsId);
	
	List<Map<String, Object>> getStudentObjScores(@Param("stuId")Long stuId, @Param("examId")Long examId, @Param("subjectId")Long subjectId);
	
	List<Map<String, Object>> getStudentSubScores(@Param("stuId")Long stuId, @Param("examId")Long examId, @Param("subjectId")Long subjectId);
	
	List<Map<String, Object>> getStudentStepScores(@Param("stuId")Long stuId, @Param("examId")Long examId, @Param("subjectId")Long subjectId);

	PaperSubScore getPaperSubScores(@Param("paperId")Long paperId);
	
}
