package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public Collection<Map<String,Object>> getErrorPapers(Long egsId) {
		
		String schId = ExamUtil.retrieveSessAttr("orgId");
		if (schId == null || schId.isEmpty()) {
			logger.error("getExcePaperSum(), failed to retrieve org id, maybe user not log in");
			throw new RuntimeException("getExcePaperSum(), failed to retrieve org id, maybe user not log in");
		}
		
		Map<String, Map<String, Object>> result = new HashMap<>();
		
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
                
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> questions = (List<Map<String, Object>>) student.get("questions");
                if(null == questions){
                    questions = new ArrayList<>();
                }
                
                long id = (int) map.get("id");
                int quesno = (int) map.get("quesno");
                String answer = map.get("answer").toString();
                
                Map<String, Object> question = new HashMap<>();
                question.put("id", id);
                question.put("quesno", quesno);
                question.put("answer", answer);
                if(null != questionsType.get(quesno)){
                    question.put("type", questionsType.get(quesno));
                }
                
                questions.add(question);
                student.put("questions", questions);
                result.put(examCode,student);
            }
        }
		
		return result.values();
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
	 

	public boolean updateErrorPaper(String papers) {
	    
	    logger.info("papers:" + papers);
	    
	    try {
	        
	        JSONObject student = JSONObject.fromObject(papers);
	        long egsId = student.getLong("egsId");
	        String examCode = student.getString("examCode");
            JSONArray answers = student.getJSONArray("answers");
            
            List<StudentObjectAnswer> objAnswers = new ArrayList<>();
            for (int i=0; i<answers.size(); i++) {
                JSONObject object = answers.getJSONObject(i);
                StudentObjectAnswer objAnswer = new StudentObjectAnswer();
                long paperid = object.getLong("paperid");
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
            soaDao.insertStudentObjectAnswers(objAnswers);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return false;
	}

}
