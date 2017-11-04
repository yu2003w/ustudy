package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.MultipleScoreSetDao;
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.QuesAnswerDivDao;
import com.ustudy.exam.dao.RefAnswerDao;
import com.ustudy.exam.model.MultipleScoreSet;
import com.ustudy.exam.model.QuesAnswer;
import com.ustudy.exam.model.QuesAnswerDiv;
import com.ustudy.exam.model.RefAnswer;
import com.ustudy.exam.service.SetAnswersService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class SetAnswersServiceImpl implements SetAnswersService {
	
	private static final Logger logger = LogManager.getLogger(SetAnswersServiceImpl.class);
	
	@Resource
	private QuesAnswerDao quesAnswerDaoImpl;
	
	@Resource
	private QuesAnswerDivDao quesAnswerDivDaoImpl;
	
	@Resource
	private RefAnswerDao refAnswerDaoImpl;
	
	@Resource
	private MultipleScoreSetDao multipleScoreSetDaoImpl;

	public Map<String, Object> getQuesAnswer(int egsId) throws Exception {
		
		logger.info("getQuesAnswer -> egsId=" + egsId);
		
		try {
			Map<String, Object> result = new HashMap<>();
			
			result.put("refAnswers", refAnswerDaoImpl.getRefAnswers(egsId));
			
			List<QuesAnswer> quesAnswers = quesAnswerDaoImpl.getQuesAnswers(egsId);
			List<QuesAnswerDiv> quesAnswerDivs = quesAnswerDivDaoImpl.getAllQuesAnswerDivs(egsId);
			Map<Integer, List<QuesAnswerDiv>> map = new HashMap<>();
			for (QuesAnswerDiv quesAnswerDiv : quesAnswerDivs) {
				List<QuesAnswerDiv> divs = map.get(quesAnswerDiv.getQuesid());
				if(null == divs){
					divs = new ArrayList<>();
				}
				divs.add(quesAnswerDiv);
				map.put(quesAnswerDiv.getQuesid(), divs);
			}
			for (QuesAnswer quesAnswer : quesAnswers) {
				quesAnswer.setChild(map.get(quesAnswer.getId()));
			}
			
			result.put("quesAnswers", quesAnswers);
			result.put("checkBoxScores", multipleScoreSetDaoImpl.getAllMultipleScoreSets(egsId));
			
			return result;
		} catch (Exception e) {
			logger.error("getQuesAnswer select error. -> msg = " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
	}

	public boolean deleteQuesAnswers(int egsId) throws Exception {
		
		logger.info("deleteQuesAnswers -> egsId=" + egsId);
		
		try {
			quesAnswerDivDaoImpl.deleteQuesAnswerDivs(egsId);
			quesAnswerDaoImpl.deleteQuesAnswers(egsId);
			refAnswerDaoImpl.deleteRefAnswers(egsId);
			multipleScoreSetDaoImpl.deleteMultipleScoreSets(egsId);
			
			return true;
		} catch (Exception e) {
			logger.error("deleteQuesAnswers delete error. -> msg = " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
	}

	public boolean saveQuesAnswers(int egsId, JSONObject ques) throws Exception {
		
		try {
			deleteQuesAnswers(egsId);
			
			List<QuesAnswer> quesAnswers = new ArrayList<>();
			JSONArray objectives = ques.getJSONArray("objectives");
			for(int i=0;i<objectives.size();i++){
				JSONObject objective = objectives.getJSONObject(i);
				
				QuesAnswer quesAnswer = new QuesAnswer();
				if(null != objective.get("quesno")) quesAnswer.setQuesno(objective.getInt("quesno"));
				if(null != objective.get("startno")) quesAnswer.setStartno(objective.getInt("startno"));
				if(null != objective.get("endno")) quesAnswer.setEndno(objective.getInt("endno"));
				if(null != objective.get("type")) quesAnswer.setType(objective.getString("type"));
				if(null != objective.get("branch")) quesAnswer.setBranch(objective.getString("branch"));
				if(null != objective.get("choiceNum")) quesAnswer.setChoiceNum(objective.getInt("choiceNum"));
				if(null != objective.get("score")) quesAnswer.setScore(objective.getInt("score"));
				if(null != objective.get("assignMode")) quesAnswer.setAssignMode(objective.getString("assignMode"));
				if(null != objective.get("scoreMode")) quesAnswer.setScoreMode(objective.getString("scoreMode"));				
				if(null != objective.get("teacOwner")) quesAnswer.setTeacOwner(objective.getString("teacOwner"));
				quesAnswer.setExamGradeSubId(egsId);
				
				quesAnswers.add(quesAnswer);
			}
			
			JSONArray subjectives = ques.getJSONArray("subjectives");
			for(int i=0;i<subjectives.size();i++){
				JSONObject subjective = subjectives.getJSONObject(i);
				
				QuesAnswer quesAnswer = new QuesAnswer();
				if(null != subjective.get("quesno")) quesAnswer.setQuesno(subjective.getInt("quesno"));
				if(null != subjective.get("startno")) quesAnswer.setStartno(subjective.getInt("startno"));
				if(null != subjective.get("endno")) quesAnswer.setEndno(subjective.getInt("endno"));
				if(null != subjective.get("type")) quesAnswer.setType(subjective.getString("type"));
				if(null != subjective.get("branch")) quesAnswer.setBranch(subjective.getString("branch"));
				if(null != subjective.get("choiceNum")) quesAnswer.setChoiceNum(subjective.getInt("choiceNum"));
				if(null != subjective.get("score")) quesAnswer.setScore(subjective.getInt("score"));
				if(null != subjective.get("assignMode")) quesAnswer.setAssignMode(subjective.getString("assignMode"));
				if(null != subjective.get("scoreMode")) quesAnswer.setScoreMode(subjective.getString("scoreMode"));				
				if(null != subjective.get("teacOwner")) quesAnswer.setTeacOwner(subjective.getString("teacOwner"));
				quesAnswer.setExamGradeSubId(egsId);
				
				quesAnswers.add(quesAnswer);
			}
			if(!quesAnswers.isEmpty()){
				quesAnswerDaoImpl.insertQuesAnswers(quesAnswers);
			}
			
			Map<Integer, Integer> quesids = new HashMap<>();
			for (QuesAnswer quesAnswer : quesAnswers) {
				for (int i = quesAnswer.getStartno(); i <= quesAnswer.getEndno(); i++) {
					quesids.put(i, quesAnswer.getId());
				}
			}
			
			List<RefAnswer> refAnswers = new ArrayList<>();
			JSONArray objectiveAnswers = ques.getJSONArray("objectiveAnswers");
			for(int i=0;i<objectiveAnswers.size();i++){
				JSONObject objectiveAnswer = objectiveAnswers.getJSONObject(i);
				
				RefAnswer refAnswer = new RefAnswer();
				if(null != objectiveAnswer.get("quesno")) {
					refAnswer.setQuesid(quesids.get(objectiveAnswer.getInt("quesno")));
					refAnswer.setQuesno(objectiveAnswer.getInt("quesno"));
				}
				if(null != objectiveAnswer.get("quesno")) {
					String answer = objectiveAnswer.getString("answer");
					if(answer.startsWith(",")){
						answer = answer.substring(1);
					}
					refAnswer.setAnswer(answer);
				}
				refAnswer.setEgsId(egsId);
				
				refAnswers.add(refAnswer);
			}
			if(!refAnswers.isEmpty()){
				refAnswerDaoImpl.insertRefAnswers(refAnswers);
			}

			List<QuesAnswerDiv> quesAnswerDivs = new ArrayList<>();
			for(int i=0;i<subjectives.size();i++){
				JSONObject subjective = subjectives.getJSONObject(i);
				
				if(null != subjective.get("child") && !subjective.get("child").equals(null)){
					JSONArray childs = subjective.getJSONArray("child");
					for(int j=0; j<childs.size(); j++){
						JSONObject child = childs.getJSONObject(j);
						QuesAnswerDiv quesAnswerDiv = new QuesAnswerDiv();
						
						if(null != child.get("quesno")) quesAnswerDiv.setQuesno(child.getString("quesno"));
						if(null != child.get("branch")) quesAnswerDiv.setBranch(child.getString("branch"));
						if(null != child.get("score")) quesAnswerDiv.setScore(child.getInt("score"));
						if(null != subjective.get("quesno") && null != quesids.get(subjective.getInt("quesno"))) {
							quesAnswerDiv.setQuesid(quesids.get(subjective.getInt("quesno")));
						}
						quesAnswerDiv.setEgsId(egsId);
						
						quesAnswerDivs.add(quesAnswerDiv);
					}
				}
			}
			if(!quesAnswerDivs.isEmpty()){
				quesAnswerDivDaoImpl.insertQuesAnswerDivs(quesAnswerDivs);
			}
			
			List<MultipleScoreSet> multipleScoreSets = new ArrayList<>();
			JSONArray checkBoxScores = ques.getJSONArray("checkBoxScores");
			for(int i=0;i<checkBoxScores.size();i++){
				JSONObject checkBoxScore = checkBoxScores.getJSONObject(i);
				if(null != checkBoxScore.get("size") && null != checkBoxScore.get("scores") && !checkBoxScore.get("scores").equals(null)){
					int correctAnswerCount = checkBoxScore.getInt("size");
					JSONArray scores = checkBoxScore.getJSONArray("scores");
					for(int j=0; j<scores.size(); j++){
						JSONObject score = scores.getJSONObject(j);
						if(null != score.get("count") && null != score.get("score")){
							int studentCorrectCount = score.getInt("count");
							int score_ = score.getInt("score");
							MultipleScoreSet multipleScoreSet = new MultipleScoreSet();
							multipleScoreSet.setExamGradeSubId(egsId);
							multipleScoreSet.setCorrectAnswerCount(correctAnswerCount);
							multipleScoreSet.setStudentCorrectCount(studentCorrectCount);
							multipleScoreSet.setScore(score_);
							
							multipleScoreSets.add(multipleScoreSet);
						}
					}
				}
			}
			if(!multipleScoreSets.isEmpty()){
				multipleScoreSetDaoImpl.insertMultipleScoreSets(multipleScoreSets);
			}
			
			return true;
		} catch (Exception e) {
			logger.error("saveQuesAnswers save error. -> msg = " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
	}

}
