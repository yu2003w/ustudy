package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.UResp;
import com.ustudy.exam.dao.ExamDao;
import com.ustudy.exam.dao.ExamSubjectDao;
import com.ustudy.exam.dao.ExameeScoreDao;
import com.ustudy.exam.dao.MultipleScoreSetDao;
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.RefAnswerDao;
import com.ustudy.exam.dao.StudentObjectAnswerDao;
import com.ustudy.exam.dao.SubscoreDao;
import com.ustudy.exam.mapper.ScoreMapper;
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.model.ExameeScore;
import com.ustudy.exam.model.MultipleScoreSet;
import com.ustudy.exam.model.QuesAnswer;
import com.ustudy.exam.model.RefAnswer;
import com.ustudy.exam.model.StudentObjectAnswer;
import com.ustudy.exam.model.score.DetailedSubScore;
import com.ustudy.exam.model.score.ExameeSubScore;
import com.ustudy.exam.model.score.QuesScoreCalTask;
import com.ustudy.exam.model.score.ScoreRule;
import com.ustudy.exam.model.score.StudentScore;
import com.ustudy.exam.model.statics.ScoreClass;
import com.ustudy.exam.model.statics.ScoreSubjectCls;
import com.ustudy.exam.service.ScoreService;
import com.ustudy.exam.service.impl.cache.ScoreCache;

@Service
public class ScoreServiceImpl implements ScoreService {
	
	private static final Logger logger = LogManager.getLogger(ScoreServiceImpl.class);
	
	@Resource
	private QuesAnswerDao quesDaoImpl;
	
	@Resource
	private MultipleScoreSetDao multipleScoreSetDaoImpl;
	
	@Autowired
	private RefAnswerDao refAnswerDaoImpl;
	
	@Resource
	private StudentObjectAnswerDao answerDaoImpl;
	
	@Resource
    private ExamSubjectDao examSubjectDao;
	
	@Resource
    private ExamDao examDao;
    
    @Autowired
    private SubscoreDao subscoreDao;
    
    @Autowired
    private ExameeScoreDao exameeScoreDao;
    
    @Autowired
    private ScoreMapper scoM;
    
    @Autowired
    private ScoreCache scoC;

    public boolean calQuestionScore(Long egsId, Integer quesno, String answer) throws Exception {
        logger.debug("calQuestionScore(), egsId: " + egsId + ",quesno=" + quesno + ",answer=" + answer);
        
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
                float score = quesAnswer.getScore();
                
                //多选给分
                Map<Integer, Float> multipleScoreSets = null;
                if(answer.length() > 1){
                    multipleScoreSets = getMultipleScoreSet(answer,egsId);
                }
                
                for (StudentObjectAnswer studentAnswer : answers) {
                    float studentScore = 0;
                    if(answer.length() == 1){
                        if(studentAnswer.getAnswer().equals(answer)){
                            studentScore = score;
                        }
                    }else if(answer.length() >= studentAnswer.getAnswer().length()){
                        if(studentAnswer.getAnswer().equals(answer)){
                            studentScore = score;
                        }else{
                            Integer correctCount = StudentObjectAnswer.getCorrectCount(studentAnswer.getAnswer(), answer);
                            if(correctCount == answer.length()){
                                studentScore = score;
                            }else if(null != multipleScoreSets.get(correctCount)){
                                studentScore = multipleScoreSets.get(correctCount);
                            }
                        }
                    }
                    
                    if(studentAnswer.getScore() != studentScore && studentScore >= 0){
                        answerDaoImpl.updateStudentObjectAnswer(studentAnswer.getId(), studentScore);
                    }
                }
                
            }
            
            return true;
        }else {
            return false;
        }
        
    }

    @Override
    public boolean calObjScoreOfEgs(Long egsId) {
        logger.debug("calEgsScore(), calculate egs score for " + egsId);
        
        /**
         * 创建线程池，并发量最大为5
         * LinkedBlockingDeque，表示执行任务或者放入队列
         */
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(5, 100, 0,
                TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        
        //存储线程的返回值
        List<Future<UResp>> results = new LinkedList<Future<UResp>>();
        
        //List<RefAnswer> refAnswers = refAnswerDaoImpl.getRefAnswers(egsId);
        List<ScoreRule> scoreRules = refAnswerDaoImpl.getEgsScoreRules(egsId);
        
        for (ScoreRule sr : scoreRules) {
            QuesScoreCalTask task = new QuesScoreCalTask(egsId, sr, answerDaoImpl);
            Future<UResp> result = tpe.submit(task);
            results.add(result);
        }
        
        //此函数表示不再接收新任务，
        //如果不调用，awaitTermination将一直阻塞
        tpe.shutdown();
        //1天，模拟永远等待
        try {
            tpe.awaitTermination(1, TimeUnit.DAYS);
            for (Future<UResp> res : results) {
            	logger.trace("calObjScoreOfEgs(), result->" + res.get());
            	// check Future to get status of calculation
            	if (!res.get().isRet()) {
            		logger.error("calObjScoreOfEgs(), " + res.get().getMessage());
            		return false;
            	}
            }
        } catch (Exception e) {
        	logger.error("calObjScoreOfEgs(), exception->" + e.getMessage());
        	return false;
        }
        
        ExamSubject examSubject = examSubjectDao.getExamSubjectById(egsId);
        if(null != examSubject && null != examSubject.getExamid()){
            calClsSubScore(examSubject.getExamid().intValue(), -1);
        }
        
        scoC.setScoreColStatus(egsId.intValue(), true);
        return true;
    }
    
    private Map<Integer, Float> getMultipleScoreSet(String answer, Long egsId){
        
        Map<Integer, Float> map = new HashMap<>();
        
        List<MultipleScoreSet> multipleScoreSets = multipleScoreSetDaoImpl.getAllMultipleScoreSets(egsId);
        
        if(null != multipleScoreSets && multipleScoreSets.size() > 0){
            for (MultipleScoreSet multipleScoreSet : multipleScoreSets) {
                if(multipleScoreSet.getTotal() == answer.trim().replaceAll(",", "").length()){
                    map.put(multipleScoreSet.getSelected(), multipleScoreSet.getScore());
                }
            }
        }
        
        return map;
    }
    
    @Transactional
    public boolean publishExamScore(Long examId, Boolean release) throws Exception {
    	
    	if (release) {
    		// 是否已全部发布
    		long count = examSubjectDao.isExamAllSubjectPublished(examId);
    		if(count == 0){    			
    			examDao.updateExamStatus(examId, "2");
                // update the status of all the sub-exams accordingly.
                examDao.updateEgsStatus(examId, "2");
    			
    			List<Map<String, Object>> scores = subscoreDao.getExamScores(examId);
    			if(scores.size() > 0){
    				List<ExameeScore> exameeScores = new ArrayList<>();
    				for (Map<String, Object> map : scores) {
    					ExameeScore exameeScore = new ExameeScore();
    					exameeScore.setExamId(examId);
    					long stuid = (int) map.get("stuid");
    					exameeScore.setStuid(stuid);
    					double score = (double) map.get("score");
    					exameeScore.setScore(Float.valueOf(String.valueOf(score)));
    					
    					exameeScores.add(exameeScore);
    				}
    				Collections.sort(exameeScores);
    				
    				int rank = 1;
    				// modified by jared, alreay sorted examinee scores
    				// if score equals, rank should be the same, however, 
    				// others' rank should be adjusted corresponding
    				for (int i = 0; i < exameeScores.size(); i++) {
    					if (i == 0) {
    						exameeScores.get(i).setRank(rank);
    					}
    					else {
    						if (exameeScores.get(i).getScore().compareTo(exameeScores.get(i-1).getScore()) == 0) {
    							exameeScores.get(i).setRank(exameeScores.get(i-1).getRank());
    						}
    						else {
    							exameeScores.get(i).setRank(rank);
    						}
    					}
    					rank++;
    				}
    				
    				// exameeScoreDao.deleteExameeScores(examId);
    				int ret = exameeScoreDao.insertExameeScores(exameeScores);
    				logger.debug("publishExamScore(), number of examee scores saved is " + ret);
    			}
    		}else {
    		    return false;
            }
    	} else {
    		examDao.updateExamStatus(examId, "1");
            // update the status of all the sub-exams accordingly.
            examDao.updateEgsStatus(examId, "1");
		}
        
        return true;
        
    }

    /* 
     * Retrieve student scores based on different filter conditions，including examinee scores 
     * and subject scores
     * (non-Javadoc)
     * @see com.ustudy.exam.service.ScoreService#getStudentScores(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String)
     */
    public List<StudentScore> getStudentScores(Long examId, Long schId, Long gradeId, Long classId, 
    		Long subjectId, String branch, String text) throws Exception {
        
        if (null != branch && branch.trim().length() > 0) {
            branch = branch.trim();
            if (branch.equals("文科")) {
                branch = "art";
            } else if (branch.equals("理科")) {
                branch = "sci";
            } else {
                branch = "";
            }
        }

        List<StudentScore> exameeScores = exameeScoreDao.getExameeScores(examId, schId, gradeId, 
        		classId, branch, text);

        logger.debug("getStudentScores(), number of items retrieved is " + exameeScores.size());
        return exameeScores;
    }

	/* 
	 * Assemble detailed scores for specified examinee
	 * (non-Javadoc)
	 * @see com.ustudy.exam.service.ScoreService#getDetailedExameeScore(java.lang.Long, java.lang.Long)
	 */
    @Override
	public ExameeSubScore getDetailedExameeScore(Long exameeId, Long examId) throws Exception {
		
		ExameeSubScore examSubS = subscoreDao.getExameeSubScores(exameeId, examId);
		logger.debug("getDetailedExameeScore(), retrieved metainfo->" + examSubS.toString());
		
		//populate object/subject question scores for each subject
		for (DetailedSubScore dss: examSubS.getSubScores()) {
			if (dss.getScore() != -1) {
				dss.setObjQuesL(subscoreDao.getObjQuesScore(examSubS.getExameeNO(), dss.getEgsId()));
				logger.trace("getDetailedExameeScore(), obj ques score->" + dss.getObjQuesL());
				dss.setSubQuesL(subscoreDao.getSubQuesScore(examSubS.getExameeNO(), dss.getEgsId()));
				logger.trace("getDetailedExameeScore(), subject ques score->" + dss.getSubQuesL());
			}
		}
		
		return examSubS;
	}

	@Override
	public List<ScoreClass> getClsScores(int eid, int gid) {
		
		List<ScoreSubjectCls> ssCl = scoM.getScoreSubCls(eid, gid);
		List<ScoreClass> scL = scoM.getScoreClass(eid, gid);
		
		// each time score for one grade calculated
		if ((ssCl == null || ssCl.isEmpty() || scL == null || scL.isEmpty())) {
			if (!calClsSubScore(eid, gid))
				return new ArrayList<ScoreClass>();
		}
		
		ssCl = scoM.getScoreSubCls(eid, gid);
		logger.debug("getClsScores(), class subject score->" + ssCl.toString());
		scL = scoM.getScoreClass(eid, gid);
		logger.debug("getClsScores(), class score->" + scL.toString());
		
		// need to assemble class score data
		for (ScoreClass sc : scL) {
			List<ScoreSubjectCls> ss = new ArrayList<ScoreSubjectCls>();
			for (ScoreSubjectCls ssc: ssCl) {
				if (ssc.getClsId() == sc.getClsId())
					ss.add(ssc);
			}
			sc.setSubScore(ss);
		}
		
		return scL;
	}
	
	private List<ScoreClass> calScoreClass(List<ScoreSubjectCls> ssc, int eid) {
		
		HashMap<Integer, ScoreClass> scM = new HashMap<Integer, ScoreClass>();
		
		logger.debug("calScoreClass(), calculate score class from " + ssc);
		for (ScoreSubjectCls ss: ssc) {
			if (scM.containsKey(ss.getClsId())) {
				ScoreClass sc = scM.get(ss.getClsId());
				sc.setAveScore(sc.getAveScore() + ss.getAveScore());
			}
			else {
				ScoreClass sc = new ScoreClass(ss.getClsId(), eid);
				sc.setAveScore(ss.getAveScore());
				scM.put(ss.getClsId(), sc);
			}
		}
		
		// sort based on ave score via list
		List<ScoreClass> scL = new ArrayList<ScoreClass>(scM.values());
		logger.debug("calScoreClass(), before sort->" + scL.toString());
		scL.sort(new Comparator<ScoreClass> () {
			public int compare(ScoreClass a, ScoreClass b) {
				if (a.getAveScore() < b.getAveScore()) {
					return 1;
				}
				else if (a.getAveScore() == b.getAveScore()) {
					return 0;
				}
				else 
					return -1;				
			}
		});
		
		// set rank here
		for (int i = 1; i <= scL.size(); i++) {
			if (i == 1)
				scL.get(i-1).setRank(i);
			else {
				if (scL.get(i-1).getAveScore() == scL.get(i-2).getAveScore())
					scL.get(i-1).setRank(scL.get(i-2).getRank());
				else
					scL.get(i-1).setRank(i);
			}
		}
		logger.debug("calScoreClass(), after sort->" + scL.toString());
		return scL;
	}

	@Transactional
	private boolean calClsSubScore(int eid, int gid) {
		
		logger.info("calClsSubScore(), to calculate class subject score for eid->" + eid + ", gid->" + gid);
		
		List<ScoreSubjectCls> ssCl = scoM.calScoreSubCls(eid, gid);
		if (ssCl == null || ssCl.isEmpty()) {
			logger.warn("calClsSubScore(), failed to calculate class subject score for eid->" + eid + 
					", gid->" + gid);
			return false;
		}
		
		logger.debug("calClsSubScore(), before set rank for class subject->" + ssCl.toString());
		// need to set rank for retrieved class subject score per egs
		int egsid = ssCl.get(0).getEgsId();
		int rank = 1;
		for (int i = 0; i < ssCl.size(); i++) {
			if (ssCl.get(i).getEgsId() == egsid) {
				if (rank == 1) {
					ssCl.get(i).setRank(rank++);
				}
				else {
					if (ssCl.get(i).getAveScore() == ssCl.get(i-1).getAveScore()) {
						ssCl.get(i).setRank(ssCl.get(i-1).getRank());
						rank++;
					}
					else {
						ssCl.get(i).setRank(rank++);
					}
				}
			}
			else {
				egsid = ssCl.get(i).getEgsId();
				rank = 1;
				ssCl.get(i).setRank(rank++);
			}
		}
		logger.debug("calClsSubScore(), after set rank for class subject->" + ssCl.toString());
		
		// save class subject score
		int ret = scoM.saveScoreSubCls(ssCl);
		if (ret < 0) {
			logger.error("calClsSubScore(), save subject class score failed with ret->" + ret + 
					", expected->" + ssCl.size());
			throw new RuntimeException("calClsSubScore(), save subject class score failed");
		}
		logger.info("calClsSubScore(), class subject score saved with ret->" + ret);
		
		List<ScoreClass> scL = calScoreClass(ssCl, eid);
		ret = scoM.saveScoreClass(scL);
		if (ret < 0) {
			logger.error("calClsSubScore(), save class score failed with ret->" + ret + 
					", expected->" + scL.size());
			throw new RuntimeException("getClsScores(), save class score failed");
		}
		logger.info("calClsSubScore(), class score saved with ret->" + ret);
		
		return true;
	}

	@Override
	public boolean isScoreCalculated(int egsid) {
		return scoC.getScoreColStatus(egsid);
	}
	
}
