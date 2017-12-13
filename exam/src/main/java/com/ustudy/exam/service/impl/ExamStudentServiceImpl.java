package com.ustudy.exam.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ExamStudentDao;
import com.ustudy.exam.model.ExamStudent;
import com.ustudy.exam.service.ExamStudentService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ExamStudentServiceImpl implements ExamStudentService {

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

    public List<Map<String, Object>> getExamStudents(Long examId) {        
        return examStudentDaompl.getExamStudents(examId);        
    }

}
