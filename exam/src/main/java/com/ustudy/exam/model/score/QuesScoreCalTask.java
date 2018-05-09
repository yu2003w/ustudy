
package com.ustudy.exam.model.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.UResp;
import com.ustudy.exam.dao.StudentObjectAnswerDao;
import com.ustudy.exam.model.MultipleScoreSet;
import com.ustudy.exam.model.StudentObjectAnswer;

public class QuesScoreCalTask implements Callable<UResp> {
    
    private static final Logger logger = LogManager.getLogger(QuesScoreCalTask.class);
    
    private Long egsId; 
    private ScoreRule sr;    
    private StudentObjectAnswerDao answerDaoImpl;

	public QuesScoreCalTask(Long egsId, ScoreRule sr, StudentObjectAnswerDao answerDaoImpl) {
		super();
		this.egsId = egsId;
		this.sr = sr;
		this.answerDaoImpl = answerDaoImpl;
	}
	
	@Override
	@Transactional
    public UResp call() throws Exception {
        
        logger.debug("QuesScoreCalTask:call(), egsId: " + egsId + ", quesno=" + sr.getQuesno() + 
        		", answer=" + sr.getAnswer());
        
        UResp ret = new UResp();
        
        String msg = "quesid=" + String.valueOf(sr.getQuesid()) + ", quesnod=" + sr.getQuesno() + 
        		", ans=" + sr.getAnswer();
        if (egsId > 0 && sr.getQuesno() > 0 && null != sr.getAnswer()) {
            try {
            	int quesno = sr.getQuesno();
                List<StudentObjectAnswer> answers = answerDaoImpl.getQuestionAnswer(egsId, quesno);
                List<Map<String, Object>> scores = new ArrayList<>();
                
                if(null != answers && answers.size() > 0){                	
                	String answer = sr.getAnswer().trim().toUpperCase();
                    float score = sr.getScore();
                    //多选给分
                    Map<Integer, Float> multipleScoreSets = null;
                    if(answer.length() > 1){
                    	multipleScoreSets = getMultipleScoreSet(answer, sr.getMulScoreSet());
                    }
                    
                    for (StudentObjectAnswer studentAnswer : answers) {
                        float studentScore = 0;
                        //单选、判断题
                        if(answer.length() == 1){
                            if(studentAnswer.getAnswer().equals(answer)){
                                studentScore = score;
                            }
                        //多选题
                        }else if(answer.length() >= studentAnswer.getAnswer().length()){
                            if(studentAnswer.getAnswer().equals(answer)){
                                studentScore = score;
                            }else{
                                Integer correctCount = StudentObjectAnswer.getCorrectCount(studentAnswer.getAnswer(), answer);
                                if(correctCount > 0){
                                    if(correctCount == answer.length()){
                                        studentScore = score;
                                    }else if(multipleScoreSets.containsKey(correctCount)){
                                        studentScore = multipleScoreSets.get(correctCount);
                                    }
                                }
                            }
                        }
                        
                        if(studentAnswer.getScore() != studentScore && studentScore >= 0){
                            Map<String, Object> sc = new HashMap<>();
                            sc.put("id", studentAnswer.getId());
                            sc.put("score", studentScore);
                            scores.add(sc);
                        }
                    }
                    
                }
                
                if(scores.size()>0){
                    answerDaoImpl.updateBatch(scores);
                }
                ret.setRet(true);
                logger.trace("QuesScoreCalTask:call(), completed->" + msg);
            } catch (Exception e) {
            	ret.setMessage("failed with exception " + e.getMessage());
            	logger.error("QuesScoreCalTask:call(), failed->" + e.getMessage());
            }
        }else {
            ret.setMessage("invalid parameter->" + msg);
        }
        
        return ret;
    }
    
    private Map<Integer, Float> getMultipleScoreSet(String answer, List<MultipleScoreSet> mssL){
        
        Map<Integer, Float> map = new HashMap<>();
        if(null != mssL && mssL.size() > 0){
            for (MultipleScoreSet mss : mssL) {
                if(mss.getTotal() == answer.trim().replaceAll(",", "").length()){
                    map.put(mss.getSelected(), mss.getScore());
                }
            }
        }
        
        return map;
    }
    
}

