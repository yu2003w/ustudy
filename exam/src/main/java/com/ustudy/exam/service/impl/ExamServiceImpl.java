package com.ustudy.exam.service.impl;

import java.util.ArrayList;
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

    @Override
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
}
