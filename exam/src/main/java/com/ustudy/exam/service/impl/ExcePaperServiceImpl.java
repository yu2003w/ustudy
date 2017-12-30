package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.StudentObjectAnswerDao;
import com.ustudy.exam.dao.StudentPaperDao;
import com.ustudy.exam.mapper.ExcePaperMapper;
import com.ustudy.exam.model.ExcePaperSum;
import com.ustudy.exam.model.StudentObjectAnswer;
import com.ustudy.exam.service.ExcePaperService;
import com.ustudy.exam.utility.ExamUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ExcePaperServiceImpl implements ExcePaperService {
	
	private static final Logger logger = LogManager.getLogger(ExcePaperService.class);

	@Autowired
	private ExcePaperMapper exM;
	
	@Autowired
    private StudentPaperDao spDao;
	
	@Autowired
    private QuesAnswerDao qaDao;
	
	@Autowired
    private StudentObjectAnswerDao soaDao;
	
	@Override
	public List<ExcePaperSum> getExcePaperSum(String schid) {
		
		List<ExcePaperSum> exP = exM.getPapersBySchool(2, schid);
		
		logger.debug("getExcePaperSum(), exception paper for school->" + schid + ", " + exP.toString());
		
		return exP;
		
	}

	@Override
	public Collection<Map<String,Object>> getErrorPapers(Long egsId) {
		
		String schId = ExamUtil.retrieveSessAttr("orgId");
		if (schId == null || schId.isEmpty()) {
			logger.error("getExcePaperSum(), failed to retrieve org id, maybe user not log in");
			throw new RuntimeException("getExcePaperSum(), failed to retrieve org id, maybe user not log in");
		}
		
		List<Map<String, Object>> res = new ArrayList<>();
		Map<String, Map<String, Object>> result = new HashMap<>();
		Map<String, Map<Integer,Map<String, Object>>> studentQuestions = new HashMap<>();
		
		Map<Integer, String> questionsType = getQuestionsType(egsId);
		
		List<Map<String, Object>> objAnswer = spDao.getErrorPapers(egsId, schId);
		for (Map<String, Object> map : objAnswer) {
            if (null != map.get("examCode")) {
                String examCode = map.get("examCode").toString();
                Map<String, Object> student = result.get(examCode);
                if(null == student){
                    String name = map.get("name").toString();
                    long paperid = (int) map.get("paperid");
                    String paperImg = map.get("paperImg").toString();
                    
                    student = new HashMap<>();
                    student.put("examCode", examCode);
                    student.put("name", name);
                    student.put("paperid", paperid);
                    student.put("paperImg", paperImg);
                }
                
                Map<Integer,Map<String, Object>> questions = (Map<Integer,Map<String, Object>>) studentQuestions.get(examCode);
                
                long id = 0;
                int quesno = 0;
                String answer = "";
                String status = "2";
                
                if(null == questions){
                    questions = new HashMap<>();
                    
                    for (Entry<Integer,String> entry : questionsType.entrySet()) {
                        quesno = entry.getKey();
                        
                        Map<String, Object> question = new HashMap<>();
                        question.put("id", id);
                        question.put("quesno", quesno);
                        question.put("answer", answer);
                        question.put("status", status);
                        if(null != questionsType.get(quesno)){
                            question.put("type", questionsType.get(quesno));
                        }
                        
                        questions.put(quesno, question);
                    }                    
                }
                
                if(null != map.get("id") && null != map.get("quesno") && null != map.get("answer")){
                    id = (int) map.get("id");
                    quesno = (int) map.get("quesno");
                    answer = map.get("answer").toString();
                    status = map.get("status").toString();
                    
                    Map<String, Object> question = new HashMap<>();
                    question.put("id", id);
                    question.put("quesno", quesno);
                    question.put("answer", answer);
                    question.put("status", status);
                    if(null != questionsType.get(quesno)){
                        question.put("type", questionsType.get(quesno));
                    }
                    
                    questions.put(quesno, question);
                }
                
                studentQuestions.put(examCode, questions);                
                result.put(examCode,student);
            }
        }
		
		for (Entry<String, Map<String, Object>> entry : result.entrySet()) {
		    String examCode = entry.getKey();
		    Map<String, Object> student = entry.getValue();
		    Map<Integer,Map<String, Object>> questions = studentQuestions.get(examCode);
		    student.put("questions", questions.values());
		    
		    res.add(student);
		}
		
		return res;
	}
	
	private Map<Integer, String> getQuestionsType(Long egsId){
	    
	    Map<Integer, String> result = new HashMap<>();
	    
	    List<Map<String, Object>> questions = qaDao.getObjectQuestions(egsId);
	    
	    for (Map<String, Object> map : questions) {
	        if (null != map.get("startno") && null != map.get("endno") && null != map.get("type")) {
	            int startno = (int) map.get("startno");
	            int endno = (int) map.get("endno");
	            String type = map.get("type").toString();
	            for (int i = startno; i <= endno; i++) {
	                result.put(i, type);
                }
	        }
        }
	    
	    return result;
	}
	 

	@Override
	public boolean updateErrorPaper(String papers) {
	    
	    logger.info("papers:" + papers);
	    
	    try {
	        
	        JSONObject student = JSONObject.fromObject(papers);
	        long egsId = student.getLong("egsId");
	        String examCode = student.getString("examCode");
            long paperid = student.getLong("paperid");
            JSONArray answers = student.getJSONArray("answers");
            
            List<StudentObjectAnswer> objAnswers = new ArrayList<>();
            for (int i=0; i<answers.size(); i++) {
                JSONObject object = answers.getJSONObject(i);
                StudentObjectAnswer objAnswer = new StudentObjectAnswer();
                int quesno = object.getInt("quesno");
                String answer = object.getString("answer");
                objAnswer.setPaperid(paperid);
                objAnswer.setQuesno(quesno);
                objAnswer.setAnswer(answer);
                objAnswer.setScore(0);
                objAnswer.setAnswerHas(0);
                
                objAnswers.add(objAnswer);
            }
            
            soaDao.deleteOneStudentObjectAnswers(egsId, examCode);
            spDao.updatePaperStatus(paperid, "3");
            soaDao.insertStudentObjectAnswers(objAnswers);
            
            return true;
        } catch (Exception e) {
            logger.error("updateErrorPaper(), failed ->" + e.getMessage());
        }
		
		return false;
	}

}
