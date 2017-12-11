package com.ustudy.exam.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.MultipleScoreSetDao;
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.RefAnswerDao;
import com.ustudy.exam.dao.StudentObjectAnswerDao;
import com.ustudy.exam.model.MultipleScoreSet;
import com.ustudy.exam.model.QuesAnswer;
import com.ustudy.exam.model.RefAnswer;
import com.ustudy.exam.model.StudentObjectAnswer;
import com.ustudy.exam.service.ScoreService;

@Service
public class ScoreServiceImpl implements ScoreService {
	
	private static final Logger logger = LogManager.getLogger(ScoreServiceImpl.class);
	
	@Resource
	private QuesAnswerDao quesDaoImpl;
	
	@Resource
	private MultipleScoreSetDao multipleScoreSetDaoImpl;
	
	@Resource
	private RefAnswerDao refAnswerDaoImpl;
	
	@Resource
	private StudentObjectAnswerDao answerDaoImpl;

    public boolean recalculateQuestionScore(Long egsId, Integer quesno, String answer) throws Exception {
        logger.debug("egsId: " + egsId + ",quesno=" + quesno + ",answer=" + answer);
        
        if (egsId > 0 && quesno > 0 && answer.trim().length() >0) {
            answer = answer.trim().toUpperCase();
            
            List<StudentObjectAnswer> answers = answerDaoImpl.getQuestionAnswer(egsId, quesno);
            if(null != answers && answers.size() > 0){
                
                //标准答案
                RefAnswer refAnswer =refAnswerDaoImpl.getRefAnswer(egsId, quesno);
                if(null == refAnswer || null == refAnswer.getQuesid() || 0 >= refAnswer.getQuesid()){
                    return false;
                }
                
                //分数
                QuesAnswer quesAnswer = quesDaoImpl.getQuesAnswer(egsId, refAnswer.getQuesid());
                int score = quesAnswer.getScore();
                
                //多选给分
                Map<Integer, Integer> multipleScoreSets = null;
                if(answer.length() > 1){
                    multipleScoreSets = getMultipleScoreSet(egsId);
                }
                
                for (StudentObjectAnswer studentAnswer : answers) {
                    int studentScore = 0;
                    if(answer.length() == 1){
                        if(studentAnswer.getAnswer().equals(answer)){
                            studentScore = score;
                        }
                    }else{
                        if(studentAnswer.getAnswer().equals(answer)){
                            studentScore = score;
                        }else{
                            Integer correctCount = getStudentCorrectCount(studentAnswer.getAnswer(), answer);
                            if(null != multipleScoreSets.get(correctCount)){
                                studentScore = multipleScoreSets.get(correctCount);
                            }
                        }
                    }
                    
                    if(studentAnswer.getScore() != studentScore){
                        System.out.println("id=" + studentAnswer.getId() + ",score=" + studentScore);
                        answerDaoImpl.updateStudentObjectAnswer(studentAnswer.getId(), studentScore);
                    }
                }
                
            }
            
            return true;
        }else {
            return false;
        }
        
    }

    public boolean recalculateQuestionScore(Long egsId) throws Exception {
        logger.debug("egsId: " + egsId);
        // TODO Auto-generated method stub
        return false;
        
    }
    
    private Map<Integer, Integer> getMultipleScoreSet(Long egsId){
        
        Map<Integer, Integer> map = new HashMap<>();
        
        List<MultipleScoreSet> multipleScoreSets = multipleScoreSetDaoImpl.getAllMultipleScoreSets(egsId);
        
        if(null != multipleScoreSets && multipleScoreSets.size() > 0){
            for (MultipleScoreSet multipleScoreSet : multipleScoreSets) {
                map.put(multipleScoreSet.getStudentCorrectCount(), multipleScoreSet.getScore());
            }
        }
        
        return map;
    }
    
    private int getStudentCorrectCount(String stuAnswer, String correctAnswer){
        
        int studentCorrectCount = 0;
        
        String[] stuAnswers = stuAnswer.split(",");
        for (String answer : stuAnswers) {
            if(correctAnswer.contains(answer)){
                studentCorrectCount++;
            }else {
                studentCorrectCount = 0;
                break;
            }
        }
        
        return studentCorrectCount;
        
    }

}
