package com.ustudy.exam.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.controller.ExamStudentController;
import com.ustudy.exam.dao.ExamStudentDao;
import com.ustudy.exam.model.ExamStudent;
import com.ustudy.exam.service.ExamStudentService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ExamStudentServiceImpl implements ExamStudentService {
    
    private static final Logger logger = LogManager.getLogger(ExamStudentController.class);

	@Resource
	private ExamStudentDao examStudentDaompl;
	
	public JSONArray getStudentInfoByExamGrade(Long examId, Long gradeId) {
		
		JSONArray array = new JSONArray();
		
		List<ExamStudent> students = examStudentDaompl.getStudentInfoByExamIDAndGradeId(examId, gradeId);
		if(null != students && students.size()>0){
			for (ExamStudent student : students) {
				JSONObject object = new JSONObject();
				object.put("id", student.getId());
				object.put("examCode", student.getExamCode());
				object.put("stuName", student.getName());
				
				array.add(object);
			}
		}
		
		return array;
	}

    public Map<String, Object> getExamStudents(Long examId, Long gradeId, Long classId, String studentName) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<Map<String, Object>> students = examStudentDaompl.getExamStudents(examId, gradeId, classId, studentName);
            result.put("students", students);
        } catch (Exception e) {
            logger.error("getExamStudents() failed->" + e.getMessage());
            throw new RuntimeException("getExamStudents() failed->" + e.getMessage());
        }
        
        return result;
    }
    
    private Set<Map<String, Object>> getClasses(List<Map<String, Object>> students){
        Set<Map<String, Object>> classes = new HashSet<>();
        
        for (Map<String, Object> student : students) {
            if(null != student.get("className") && null != student.get("classId")){
            	String className = student.get("className").toString();
            	long classId = Long.valueOf(student.get("classId").toString());
            	
            	Map<String, Object> class_ = new HashMap<>();
            	class_.put("className", className);
            	class_.put("classId", classId);
            	
            	classes.add(class_);
            }
        }
        
        return classes;
    }

    public Map<String, Object> getMissExamStudents(Long egsId, Long gradeId, Long classId, String studentName) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<Map<String, Object>> students = examStudentDaompl.getMissExamStudents(egsId, gradeId, classId, studentName);
            Set<Map<String, Object>> classes = getClasses(students);
            result.put("students", students);
            result.put("classes", classes);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }

}
