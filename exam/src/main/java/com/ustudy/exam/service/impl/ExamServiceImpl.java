package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ExamDao;
import com.ustudy.exam.model.Exam;
import com.ustudy.exam.service.ExamService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ExamServiceImpl implements ExamService {
	
	private static final Logger logger = LogManager.getLogger(ExamServiceImpl.class);
	
	@Resource
	private ExamDao examDaoImpl;
	
	public List<Exam> getAllExams(){
		return examDaoImpl.getAllExams();
	}
	
	public List<Exam> getExamsByStatus(String status){
		logger.debug("getExamsByStatus -> status:" + status);
		return examDaoImpl.getExamsByStatus(status);
	}
	
	public Exam getExamsById(Long id){
		logger.debug("getExamsById -> id:" + id);
		return examDaoImpl.getExamsById(id);
	}

	@Override
	public ArrayList<Map> getSubjects() {
		return examDaoImpl.getSubjects();
	}

	@Override
	public ArrayList<Map> getGrades() {
		return examDaoImpl.getGrades();
	}

    public JSONArray getExams(Boolean finished, Long gradeId, Long subjectId, String startDate, String endDate, String name) {
        
        JSONArray result = new JSONArray();
        
        try {
            List<Exam> exams = examDaoImpl.getExams(finished, gradeId, subjectId, startDate, endDate, name);
            
            for (Exam exam : exams) {
                
                List<Map<String, Object>> grades = examDaoImpl.getExamGrades(exam.getId());
                List<Map<String, Object>> subjects = examDaoImpl.getExamSubjects(exam.getId());
                long count = examDaoImpl.getExamStudengCount(exam.getId());
                
                JSONObject object = JSONObject.fromObject(exam);
                object.put("grades", grades);
                object.put("subjects", subjects);
                object.put("studentCount", count);
                
                result.add(object);
            }
            
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        
        return result;
        
    }

	public JSONArray getExamSummary(Long examId) {
		
		List<Map<String, Object>> examSubjects = examDaoImpl.getExamSummary(examId);
		
		if(null != examSubjects && examSubjects.size() > 0){
			
			Map<Long, Long> gradeStudentCounts = getGradeStudentCounts(examId);
			Map<Long, Long> subjectPaperCounts = getSubjectPaperCounts(examId);
			Map<Long, Map<String, Long>> subjectQuestions =  getSubjectQuestions(examId);
			
			return summary(examSubjects, gradeStudentCounts, subjectPaperCounts, subjectQuestions);
		}
		
		return null;
	}
	
	private Map<Long, Long> getGradeStudentCounts(Long examId){
		
		Map<Long, Long> counts = new HashMap<>();
		
		List<Map<String, Object>> gradeStudentCounts = examDaoImpl.getGradeStudentCounts(examId);		
		for (Map<String, Object> map : gradeStudentCounts) {
			long gradeId = (long) map.get("gradeId");
			long count = (long) map.get("count");
			counts.put(gradeId, count);
		}
		
		return counts;
	}
	
	private Map<Long, Long> getSubjectPaperCounts(Long examId){
		
		Map<Long, Long> counts =  new HashMap<>();
		
		List<Map<String, Object>> subjectPaperCounts = examDaoImpl.getSubjectPaperCounts(examId);		
		for (Map<String, Object> map : subjectPaperCounts) {
			long egsId = (long) map.get("egsId");
			long count = (long) map.get("count");
			counts.put(egsId, count);
		}
		
		return counts;
	}
	
	private Map<Long, Map<String, Long>> getSubjectQuestions(Long examId){
		
		Map<Long, Map<String, Long>> counts =  new HashMap<>();
		
		List<Map<String, Object>> subjectQuestions = examDaoImpl.getSubjectQuestions(examId);
		for (Map<String, Object> map : subjectQuestions) {
			long egsId = (long) map.get("egsId");
			String type = map.get("type").toString();
			int startno = (int) map.get("startno");
			int endno = (int) map.get("endno");
			int score = (int) map.get("score");			
			if(type.equals("单选题") || type.equals("多选题") || type.equals("判断题")){
				Map<String, Long> m = counts.get(egsId);
				long objectCount = 0;
				long objectScore = 0;		
				if(null == m){
					m = new HashMap<>();
				}else {
					if(null != m.get("objectCount")){
						objectCount = m.get("objectCount");
					}
					if(null != m.get("objectScore")){
						objectScore = m.get("objectScore");
					}
				}
				int count = endno - startno + 1;
				objectCount += count;
				objectScore += count*score;
				
				m.put("objectCount", objectCount);
				m.put("objectScore", objectScore);
				
				counts.put(egsId, m);
			}else{
				Map<String, Long> m = counts.get(egsId);
				long subjectCount = 0;
				long subjectScore = 0;
				if(null == m){
					m = new HashMap<>();
				}else {
					if(null != m.get("subjectCount")){
						subjectCount = m.get("subjectCount");
					}
					if(null != m.get("subjectScore")){
						subjectScore = m.get("subjectScore");
					}
				}
				int count = endno - startno + 1;
				subjectCount += count;
				subjectScore += count*score;
				
				m.put("subjectCount", subjectCount);
				m.put("subjectScore", subjectScore);
				
				counts.put(egsId, m);
			}
		}
		
		return counts;
	}
	
	private JSONArray summary(List<Map<String, Object>> examSubjects, Map<Long, Long> gradeStudentCounts, Map<Long, Long> subjectPaperCounts, Map<Long, Map<String, Long>> subjectQuestions){
		
		for (Map<String, Object> map : examSubjects) {
			
		}
		
		return null;
	}
}
