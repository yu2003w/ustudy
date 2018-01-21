package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.exam.dao.ExamDao;
import com.ustudy.exam.dao.ExamSubjectDao;
import com.ustudy.exam.dao.ExameeScoreDao;
import com.ustudy.exam.dao.MultipleScoreSetDao;
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.RefAnswerDao;
import com.ustudy.exam.dao.StudentObjectAnswerDao;
import com.ustudy.exam.dao.SubscoreDao;
import com.ustudy.exam.mapper.ScoreMapper;
import com.ustudy.exam.model.ExameeScore;
import com.ustudy.exam.model.MultipleScoreSet;
import com.ustudy.exam.model.QuesAnswer;
import com.ustudy.exam.model.RefAnswer;
import com.ustudy.exam.model.StudentObjectAnswer;
import com.ustudy.exam.model.statics.ScoreClass;
import com.ustudy.exam.model.statics.ScoreSubjectCls;
import com.ustudy.exam.service.ScoreService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
	
	@Resource
    private ExamSubjectDao examSubjectDao;
	
	@Resource
    private ExamDao examDao;
    
    @Resource
    private SubscoreDao subscoreDao;
    
    @Resource
    private ExameeScoreDao exameeScoreDao;
    
    @Autowired
    private ScoreMapper scoM;

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
        
        List<RefAnswer> refAnswers = refAnswerDaoImpl.getRefAnswers(egsId);
        for (RefAnswer refAnswer : refAnswers) {
            if(!recalculateQuestionScore(egsId, refAnswer.getQuesno(), refAnswer.getAnswer())){
                return false;
            }
        }
        
        return true;
        
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

    public boolean publishExamScore(Long examId, Boolean release) throws Exception {
    	
    	if (release) {
    		// 是否已全部发布
    		long count = examSubjectDao.isExamAllSubjectPublished(examId);
    		if(count == 0){    			
    			examDao.updateExamStatus(examId, "2");
    			
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
    				
    				float score = 1000;
    				int index = 0;
    				for (ExameeScore exameeScore : exameeScores) {
    					if(exameeScore.getScore() <= score){
    						if(exameeScore.getScore() < score){
    							index = index+1;
    						}
    						score = exameeScore.getScore();
    						exameeScore.setRank(index);
    					}
    				}
    				exameeScoreDao.deleteExameeScores(examId);
    				exameeScoreDao.insertExameeScores(exameeScores);
    			}
    		}else {
    		    return false;
            }
    	} else {
    		examDao.updateExamStatus(examId, "1");
		}
        
        return true;
        
    }

    public JSONArray getStudentSubjects(Long examId, Long schId, Long gradeId, Long classId, Long subjectId, String branch, String text) throws Exception {
        
        JSONArray array = new JSONArray();
        
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
        
        List<Map<String, Object>> exameeScores = exameeScoreDao.getExameeScores(examId, schId, gradeId, classId, branch, text);
        if(null != exameeScores && exameeScores.size() > 0){
            Map<Long, JSONArray> studentScores = getStudentScores(examId, schId, gradeId, classId, subjectId, branch, text);
            for (Map<String, Object> map : exameeScores) {
                long stuid = (int)map.get("stuExamId");
                JSONArray scores = studentScores.get(stuid);
                if(null != scores){
                    map.put("scores", scores);
                    array.add(map);
                }
            }
        }

        return array;
    }
    
    private Map<Long, JSONArray> getStudentScores(Long examId, Long schId, Long gradeId, Long classId, Long subjectId, String branch, String text){
        
        Map<Long, JSONArray> result = new HashMap<>();
        
        List<Map<String, Object>> studentScores = exameeScoreDao.getStudentScores(examId, schId, gradeId, classId, subjectId, branch, text);
        for (Map<String, Object> map : studentScores) {
            long stuid = (int)map.get("stuid");
            JSONArray array = result.get(stuid);
            if(null == array){
                array = new JSONArray();
            }
            array.add(map);
            
            result.put(stuid, array);
        }
        
        return result;
        
    }

	public JSONObject getStudentScores(Long stuId, Long examId) throws Exception {
		
		JSONObject object = new JSONObject();
		
		List<Map<String, Object>> scores = subscoreDao.getStudentScores(stuId, examId);
		if(scores.size()>0){
			JSONArray array = new JSONArray();
			Map<String, Map<Long, List<Map<String, Object>>>> questions = getQuestions(stuId, examId);
			Map<Long, List<Map<String, Object>>> subjectives = questions.get("subjectives");
			Map<Long, List<Map<String, Object>>> objectives = questions.get("objectives");
			for (Map<String, Object> map : scores) {
				JSONObject subject = new JSONObject();
				long egsId = (int)map.get("egsId");
				long subId = (int)map.get("subId");
				String subName = map.get("subName").toString();
				float score = (float)map.get("score");
				float subScore = (float)map.get("subScore");
				float objScore = (float)map.get("objScore");
				subject.put("egsId", egsId);
				subject.put("subId", subId);
				subject.put("subName", subName);
				subject.put("score", score);
				subject.put("subScore", subScore);
				subject.put("objScore", objScore);
				if(null != subjectives.get(subId)){
					subject.put("subjectives", subjectives.get(subId));
				}
				if(null != objectives.get(subId)){
					subject.put("objectives", objectives.get(subId));
				}
				
				array.add(subject);
				
				if(null != map.get("stuid")){
					long stuid = (int)map.get("stuid");
					String name = map.get("name").toString();
					String stuno = map.get("stuno").toString();
					long gradeId = (int) map.get("gradeid");
					object.put("stuId", stuid);
					object.put("name", name);
					object.put("stuno", stuno);
					object.put("gradeId", gradeId);
				}
			}
			object.put("subjects", array);
		}
		
		return object;
	}
	
	private Map<String, Map<Long, List<Map<String, Object>>>> getQuestions(Long stuId, Long examId){
		
		Map<String, Map<Long, List<Map<String, Object>>>> result = new HashMap<>();
		
		List<Map<String, Object>> questions = examDao.getSubjectQuestions(examId);
		
		Map<String, Object> markModes = new HashMap<>();
		for (Map<String, Object> map : questions) {
		    long subId = (int)map.get("subId");
            int startno = (int)map.get("startno");
            String markMode = "单评";
            if(null != map.get("markMode")){
                map.get("markMode").toString();
            }
            markModes.put(subId + "-" + startno, markMode);
		}
		
		Map<Long, List<Map<String, Object>>> objectives = new HashMap<>();
		List<Map<String, Object>> objScores = subscoreDao.getStudentObjScores(stuId, examId, null);
		for (Map<String, Object> map : objScores) {
		    float score = (int)map.get("score");
		    objectives = setScores(objectives, map.get("id").toString(), score);
        }
		
		List<Map<String, Object>> subScores = subscoreDao.getStudentSubScores(stuId, examId, null);
		Map<String, List<Map<String, Object>>> subScoreMap = new HashMap<>();
		for (Map<String, Object> map : subScores) {
		    String id = map.get("id").toString();
		    List<Map<String, Object>> list = subScoreMap.get(id);
		    if(null == list){
		        list = new ArrayList<>();
		    }
		    list.add(map);
		    
		    subScoreMap.put(id, list);
		}
		
		Map<Long, List<Map<String, Object>>> subjectives = new HashMap<>();		
		for (Entry<String,List<Map<String,Object>>> entry : subScoreMap.entrySet()) {
		    String id = entry.getKey();
		    if (null != markModes.get(id) && null != subScoreMap.get(id)) {
		        String markMode = markModes.get(id).toString();
		        if (markMode.equals("单评")) {
		            Map<String, Object> map = subScoreMap.get(id).get(0);
		            subjectives = setScores(subjectives, id, (float)map.get("score"));
		        }else{
		            List<Map<String,Object>> list = subScoreMap.get(id);
		            float viewedScore = 0;
		            float finalScore = 0;
		            int count = 0;
		            for (Map<String, Object> map : list) {
		                boolean isfinal = (boolean) map.get("isfinal");
		                if (isfinal) {
		                    finalScore += (float)map.get("score");
		                } else {
		                    viewedScore += (float)map.get("score");
		                    count += 1;
                        }
                    }
		            if (finalScore > 0) {
		                subjectives = setScores(subjectives, id, finalScore);
		            } else {
		                subjectives = setScores(subjectives, id, viewedScore/count);
                    }
		        }
		    }
        }
		
		List<Map<String, Object>> stepScores = subscoreDao.getStudentStepScores(stuId, examId, null);
		for (Map<String, Object> map : stepScores) {
		    float score = (int)map.get("score");
		    subjectives = setScores(subjectives, map.get("id").toString(), score);
        }

		result.put("subjectives", subjectives);
		result.put("objectives", objectives);
		
		return result;
	}
	
	private Map<Long, List<Map<String, Object>>> setScores(Map<Long, List<Map<String, Object>>> scores,String id, float score){
	    
	    if (null != id && id.indexOf("-")>=0) {
	        String[] ids = id.split("-");
	        long subId = Long.valueOf(ids[0]);
	        int quesno = Integer.valueOf(ids[1]);
	        List<Map<String, Object>> list = scores.get(subId);
	        if(null == list){
	            list = new ArrayList<>();
	        }
	        
	        Map<String, Object> question = new HashMap<>();
            question.put("quesno", quesno);
            question.put("score", score);
	        
	        list.add(question);
	        
	        scores.put(subId, list);
	    }
	    
	    return scores;
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
		if (ret != ssCl.size()) {
			logger.error("calClsSubScore(), save subject class score failed with ret->" + ret + 
					", expected->" + ssCl.size());
			throw new RuntimeException("calClsSubScore(), save subject class score failed");
		}
		logger.info("calClsSubScore(), class subject score saved with ret->" + ret);
		
		List<ScoreClass> scL = calScoreClass(ssCl, eid);
		ret = scoM.saveScoreClass(scL);
		if (ret != scL.size()) {
			logger.error("calClsSubScore(), save class score failed with ret->" + ret + 
					", expected->" + scL.size());
			throw new RuntimeException("getClsScores(), save class score failed");
		}
		logger.info("calClsSubScore(), class score saved with ret->" + ret);
		
		return true;
	}
	
}
