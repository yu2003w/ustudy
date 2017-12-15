package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ExamDao;
import com.ustudy.exam.model.Exam;
import com.ustudy.exam.service.ExamService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ExamServiceImpl implements ExamService {
    
    private static final Logger logger = LoggerFactory.getLogger(ExamServiceImpl.class);
    
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
        
        logger.info("getExamSummary -> examId:" + examId);
        
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
            long gradeId = (int) map.get("gradeId");
            long count = (long) map.get("count");
            counts.put(gradeId, count);
        }
        
        return counts;
    }
    
    private Map<Long, Long> getSubjectPaperCounts(Long examId){
        
        Map<Long, Long> counts =  new HashMap<>();
        
        List<Map<String, Object>> subjectPaperCounts = examDaoImpl.getSubjectPaperCounts(examId);       
        for (Map<String, Object> map : subjectPaperCounts) {
            long egsId = (int) map.get("egsId");
            long count = (long) map.get("count");
            counts.put(egsId, count);
        }
        
        return counts;
    }
    
    private Map<Long, Map<String, Long>> getSubjectQuestions(Long examId){
        
        Map<Long, Map<String, Long>> counts =  new HashMap<>();
        
        List<Map<String, Object>> subjectQuestions = examDaoImpl.getSubjectQuestions(examId);
        for (Map<String, Object> map : subjectQuestions) {
            long egsId = (int) map.get("egsId");
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
        
        JSONArray result = new JSONArray();
        JSONArray array = new JSONArray();
        
        Map<Long, List<JSONObject>> subjects = new HashMap<>();
        
        for (Map<String, Object> map : examSubjects) {
            long gradeId = (int) map.get("gradeId");
            String gradeName = String.valueOf(map.get("gradeName"));
            List<JSONObject> list = subjects.get(gradeId);          
            if(null == list){
                list = new ArrayList<>();
                JSONObject object = new JSONObject();
                object.put("gradeId", gradeId);
                object.put("gradeName", gradeName);
                
                long count = 0;
                if(null != gradeStudentCounts.get(gradeId)){
                    count = gradeStudentCounts.get(gradeId);
                }
                object.put("studentCount", count);
                
                array.add(object);
            }
            
            JSONObject subject = new JSONObject();
            subject.put("subjectName", map.get("subjectName"));
            subject.put("subjectId", map.get("subjectId"));
            subject.put("markStatus", map.get("markStatus"));
            subject.put("template", map.get("template"));
            subject.put("answerSet", map.get("answerSet"));
            subject.put("taskDispatch", map.get("taskDispatch"));
            
            long egsId = 0;
            if(null != map.get("egsId")){
                egsId = (int) map.get("egsId");
            }
            subject.put("egsId", egsId);
            
            long paperCount = 0;
            if(null != subjectPaperCounts.get(egsId)){
                paperCount = subjectPaperCounts.get(egsId);
            }
            subject.put("paperCount", paperCount);
            
            long objectCount = 0;
            long objectScore = 0;
            long subjectCount = 0;
            long subjectScore = 0;
            
            Map<String, Long> questions = subjectQuestions.get(egsId);
            if(null != questions){
                objectCount = questions.get("objectCount")==null?0:questions.get("objectCount");
                objectScore = questions.get("objectScore")==null?0:questions.get("objectScore");
                subjectCount = questions.get("subjectCount")==null?0:questions.get("subjectCount");
                subjectScore = questions.get("subjectScore")==null?0:questions.get("subjectScore");
            }
            
            subject.put("objectCount", objectCount);
            subject.put("objectScore", objectScore);
            subject.put("subjectCount", subjectCount);
            subject.put("subjectScore", subjectScore);
            
            list.add(subject);
            
            subjects.put(gradeId, list);
        }
        
        if(array.size() > 0){
            for (int i=0; i<array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                long gradeId = object.getLong("gradeId");
                
                JSONObject robject = new JSONObject();
                
                robject.put("gradeId", gradeId);
                robject.put("gradeName", object.getString("gradeName"));
                robject.put("studentCount", object.getLong("studentCount"));
                robject.put("subjects", subjects.get(gradeId));
                
                result.add(robject);
            }
        }
        
        return result;
    }
}
