
package com.ustudy.exam.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ustudy.exam.dao.MultipleScoreSetDao;
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.StudentObjectAnswerDao;
import com.ustudy.exam.model.MultipleScoreSet;
import com.ustudy.exam.model.QuesAnswer;
import com.ustudy.exam.model.RefAnswer;
import com.ustudy.exam.model.StudentObjectAnswer;
import com.ustudy.exam.service.impl.ScoreServiceImpl;

public class RecalculateQuestionScoreTask implements Callable<String> {
    
    private static final Logger logger = LogManager.getLogger(ScoreServiceImpl.class);
    
    private Long egsId; 
    private RefAnswer refAnswer; 
    private QuesAnswerDao quesDaoImpl;    
    private MultipleScoreSetDao multipleScoreSetDaoImpl;    
    private StudentObjectAnswerDao answerDaoImpl;

    public RecalculateQuestionScoreTask(Long egsId,RefAnswer refAnswer,QuesAnswerDao quesDaoImpl,MultipleScoreSetDao multipleScoreSetDaoImpl,StudentObjectAnswerDao answerDaoImpl) {
        this.egsId = egsId;
        this.refAnswer = refAnswer;
        this.quesDaoImpl = quesDaoImpl;
        this.multipleScoreSetDaoImpl = multipleScoreSetDaoImpl;
        this.answerDaoImpl = answerDaoImpl;
    }
    
    @Override
    public String call() throws Exception {
        
        logger.debug("egsId: " + egsId + ",quesno=" + refAnswer.getQuesno() + ",answer=" + refAnswer.getAnswer());
        
        if (egsId > 0 && refAnswer.getQuesno() > 0 && null != refAnswer.getAnswer()) {
            
            int quesno = refAnswer.getQuesno();
            String answer = refAnswer.getAnswer().trim().toUpperCase();
            
            List<StudentObjectAnswer> answers = answerDaoImpl.getQuestionAnswer(egsId, quesno);
            List<Map<String, Object>> scores = new ArrayList<>();
            
            if(null != answers && answers.size() > 0){
                
                //分数
                QuesAnswer quesAnswer = quesDaoImpl.getQuesAnswer(egsId, refAnswer.getQuesid());
                float score = quesAnswer.getScore();
                
                //多选给分
                Map<Integer, Integer> multipleScoreSets = null;
                if(answer.length() > 1){
                    multipleScoreSets = getMultipleScoreSet(answer, egsId);
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
                            Integer correctCount = ExamUtil.getStudentCorrectCount(studentAnswer.getAnswer(), answer);
                            if(correctCount > 0){
                                if(correctCount == answer.split(",").length){
                                    studentScore = score;
                                }else if(null != multipleScoreSets.get(correctCount)){
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
            
            return "true";
        }else {
            return "false";
        }
    }
    
    private Map<Integer, Integer> getMultipleScoreSet(String answer, Long egsId){
        
        Map<Integer, Integer> map = new HashMap<>();
        
        List<MultipleScoreSet> multipleScoreSets = multipleScoreSetDaoImpl.getAllMultipleScoreSets(egsId);
        
        if(null != multipleScoreSets && multipleScoreSets.size() > 0){
            for (MultipleScoreSet multipleScoreSet : multipleScoreSets) {
                if(multipleScoreSet.getCorrectAnswerCount() == answer.trim().replaceAll(",", "").length()){
                    map.put(multipleScoreSet.getStudentCorrectCount(), multipleScoreSet.getScore());
                }
            }
        }
        
        return map;
    }
    
}

