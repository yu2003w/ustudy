package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.UResp;
import com.ustudy.exam.dao.ExamDao;
import com.ustudy.exam.dao.ExamSubjectDao;
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.SubscoreDao;
import com.ustudy.exam.mapper.MarkTaskMapper;
import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.model.MarkTask;
import com.ustudy.exam.model.QuesAnswer;
import com.ustudy.exam.model.score.ChildObjScore;
import com.ustudy.exam.model.score.ChildSubScore;
import com.ustudy.exam.model.score.SubChildScore;
import com.ustudy.exam.model.score.SubScore;
import com.ustudy.exam.service.ExamSubjectService;
import com.ustudy.exam.service.impl.cache.PaperCache;
import com.ustudy.exam.service.impl.cache.ScoreCache;

@Service
public class ExamSubjectServiceImpl implements ExamSubjectService {

	private static final Logger logger = LogManager.getLogger(ExamSubjectServiceImpl.class);

	@Autowired
	private ExamSubjectDao egsDaoImpl;
	
	@Autowired
	private SubscoreDao scoreDaoImpl;

	@Resource
	private ExamDao examDao;
	
	@Autowired
	private PaperCache paperC;
	
	@Autowired
    private ScoreCache scoC;
	
	@Autowired
	private MarkTaskMapper mtM;
	
	@Autowired
	private QuesAnswerDao qaDao;

	public List<ExamSubject> getExamSubjects(Long subjectId, Long gradeId, String start, String end, String examName) {
		logger.trace("getExamSubjects(), retrieving all exam subjects.");

		List<ExamSubject> esL = egsDaoImpl.getAllExamSubject(subjectId, gradeId, start, end, examName);
		// set answerSet and taskDispatch based on calculation
		populateExamSubStatus(esL);
		return esL;
	}

	public List<ExamSubject> getExamSubjects(Long examId) {
		logger.debug("getExamSubjects -> examId:" + examId);
		List<ExamSubject> esL = egsDaoImpl.getAllExamSubjectByExamId(examId);
		// set answerSet and taskDispatch based on calculation
		populateExamSubStatus(esL);
		return esL;
	}

	public List<ExamSubject> getExamSubjects(Long examId, Long gradeId) {
		logger.debug("getExamSubjects -> examId:" + examId + ",gradeId:" + gradeId);
		
		List<ExamSubject> esL = egsDaoImpl.getAllExamSubjectByExamIdAndGradeId(examId, gradeId);
		// set answerSet and taskDispatch based on calculation
		populateExamSubStatus(esL);
		return esL;
	}

	public List<ExamSubject> getExamSubjects(Long examId, Long gradeId, Long subjectId) {
		logger.debug("getExamSubjects -> examId:" + examId + ",gradeId:" + gradeId + ",subjectId:" + subjectId);
		
		List<ExamSubject> esL = egsDaoImpl.getExamSubjectByExamIdAndGradeIdAndSubjectId(examId, gradeId, subjectId);
		// set answerSet and taskDispatch based on calculation
		populateExamSubStatus(esL);
		return esL;
	}

	public ExamSubject getExamSubject(Long id) {
		logger.debug("getExamSubject -> id:" + id);
		ExamSubject es = egsDaoImpl.getExamSubjectById(id);
		// set status for exam subject
		es.setAnswerSet(this.isAnswerSet(es.getId()).isRet());
		es.setTaskDispatch(this.isMarkTaskDispatched(es.getId()).isRet());
		return es;
	}

	public boolean saveBlankAnswerPaper(Long id, String fileName) {
		logger.debug("saveBlankAnswerPaper -> id:" + id + ",fileName:" + fileName);
		try {
			egsDaoImpl.saveBlankAnswerPaper(id, fileName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveBlankQuestionsPaper(Long id, String fileName) {
		logger.debug("saveBlankQuestionsPaper -> id:" + id + ",fileName:" + fileName);
		try {
			egsDaoImpl.saveBlankQuestionsPaper(id, fileName);
			return true;
		} catch (Exception e) {
			logger.debug("saveBlankQuestionsPaper(), failed with exception->" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean getMarkSwitchById(Long id) {
		logger.debug("getMarkSwitch -> id:" + id);
		ExamSubject es = egsDaoImpl.getMarkSwitchById(id);
		return es.getMarkSwitch();
	}

	public List<ExamSubject> getLastExamSubjects() {

		Exam exam = examDao.getLastExam();
		if (null != exam && exam.getId() > 0) {
			return getExamSubjects(exam.getId());
		} else {
			return null;
		}

	}

	public boolean updateMarkSwitch(Long egsId, Boolean release) {
		try {
			egsDaoImpl.updateMarkSwitchById(egsId, release);

			// TODO: 清除缓存
			//paperC.clearSubCache(String.valueOf(egsId));
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateMarkSwitch(Long examId, Long gradeId, Long subjectId, Boolean release) {
		try {
			egsDaoImpl.updateMarkSwitch(examId, gradeId, subjectId, release);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return false;
	}

	@Override
	public boolean updateEgsScoreStatus(Long egsId, Boolean release) {
		
		logger.debug("updateEgsScoreStatus(), egsid=" + egsId + ", release=" + release);
		try {
			egsDaoImpl.updateExamSubjectStatusById(egsId, release);
			if (release) {
			    // calculate subscore for egs
				SummaryEgsScore(egsId);
				// 清除缓存
				paperC.clearSubCache(String.valueOf(egsId));
				// 更新考试状态
				// TODO: possibly need to optimize logic and sql instructions here
				examDao.updateExamStatusByEgsid(egsId,"2");
			}else {
			    // 更新考试状态
			    examDao.updateExamStatusByEgsid(egsId,"1");
			    scoC.setScoreColStatus(egsId.intValue(), false);
            }
			return true;
		} catch (Exception e) {
			logger.error("updateEgsScoreStatus(), failed with exception->" + e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateExamSubjectStatus(Long examId, Long gradeId, Long subjectId, Boolean release) {
		try {
			egsDaoImpl.updateExamSubjectStatus(examId, gradeId, subjectId, release);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return false;
	}

	private void SummaryEgsScore(Long egsId) {
		logger.debug("SummaryEgsScore(), to retrieve object question scores for " + egsId);
		List<SubScore> scores = retrieveObjScores(egsId);
		logger.debug("SummaryEgsScore(), to retrieve subject question scores for " + egsId);
		Map<Long, SubScore> subjScores = retrieveSubScores(egsId);
		
		// calculate total score for each exmainee in this egs
		for (SubScore ss : scores) {
			SubScore subjS = subjScores.get(ss.getStuId());
			if(null != subjS){
				// combine object and subject score here
			    ss.setSubScore(subjS.getSubScore());
			    List<SubChildScore> scsOL = ss.getSubCSL();
			    List<SubChildScore> scsSL = subjS.getSubCSL();
			    for (SubChildScore scss : scsSL) {
			    	boolean found = false;
			    	for (SubChildScore scs: scsOL) {
				    	if (scs.getSubName().compareTo(scss.getSubName()) == 0) {
				    		scs.setScore(scss.getScore() + scs.getScore());
				    		found = true;
				    		break;
				    	}
				    }
			    	if (!found) {
			    		// branch only existed in subject question scores
			    		scsOL.add(scss);
			    	}
		    	}
			    
			}
			else {
				logger.warn("SummaryEgsScore(), failed to find subject scores for eid=" + ss.getStuId() + 
						", egs=" + egsId);
			}
			ss.setScore(ss.getObjScore() + ss.getSubScore());
		}
		
		logger.debug("SummaryEgsScore(), score combined, to calculate rank now.");
		if(scores.size() > 0){
			Collections.sort(scores);
			// updated by jared, need to accumulate rank when score equals
			int rank =1;
			for (int i = 0; i < scores.size(); i++) {
				if (i == 0) {
					scores.get(i).setRank(rank);
				}
				else {
					if (scores.get(i).getScore().compareTo(scores.get(i-1).getScore()) == 0) {
						scores.get(i).setRank(scores.get(i-1).getRank());
					}
					else {
						scores.get(i).setRank(rank);
					}
				}
				rank++;
			}
			// save subscores, insert on duplicate key udpate
			scoreDaoImpl.saveSubscores(scores);
			// populate sub child scores
			List<SubChildScore> childScores = new ArrayList<SubChildScore>();
			for (SubScore ss: scores) {
				List<SubChildScore> scsL = ss.getSubCSL();
				scsL.forEach(item->{
				    // TODO: populate subid here
					item.setParentId(ss.getId());
					childScores.add(item);
				});
			}
			// save sub child scores
			scoreDaoImpl.saveSubChildScores(childScores);
			logger.debug("SummaryEgsScore(), sub child scores saved for " + egsId);
		}
	}

	/**
	 * @param egsId
	 * @return List of SubScore only contains object question scores
	 */
	private List<SubScore> retrieveObjScores(Long egsId) {
		Map<Long, SubScore> scoreM = new HashMap<Long, SubScore>();
		/*
		 * returned data with format as below,
		 * score, eid, branch
		 * data is grouped by eid and branch
		 */
		List<ChildObjScore> scoL = egsDaoImpl.retrieveEgsObjScores(egsId);
		if (scoL != null && !scoL.isEmpty()) {
			logger.debug("retrieveObjScores(), retrieved " + scoL.size() + " items for " + egsId);
			for (ChildObjScore sco: scoL) {
				SubScore ss = scoreM.get(sco.getEid());
				if (ss == null) {
					ss = new SubScore(sco.getEid(), egsId);
					scoreM.put(sco.getEid(), ss);
				}
				
				// accumulate object score here
				ss.setObjScore(ss.getObjScore() + sco.getScore());
				if (sco.getBranch().compareTo("不分科") != 0)
					ss.getSubCSL().add(new SubChildScore(sco.getBranch(), sco.getScore()));
			}
		}
		logger.trace("retrieveObjScores(), obj scores retrieved for " + egsId + "->" + scoreM.values());
		return new ArrayList<SubScore>(scoreM.values());
	}
	
  /*private Map<Long, Float> getObjScores(Long egsId) {

		Map<Long, Float> result = new HashMap<>();
		
		List<Map<String, Object>> scores = egsDaoImpl.getExamSubjectObjScores(egsId);
		for (Map<String, Object> map : scores) {
			if (null != map.get("eid") && null != map.get("score")) {
				long studentId = (int) map.get("eid");
				Object score = map.get("score");
				result.put(studentId, Float.parseFloat(score.toString()));
			}
		}

		return result;

	}*/

	/**
	 * @param egsId
	 * @return subject scores for specified egs
	 */
	private Map<Long, SubScore> retrieveSubScores(Long egsId) {
		Map<Long, SubScore> scoreM = new HashMap<Long, SubScore>();
		
		List<ChildSubScore> cssL = egsDaoImpl.retrieveEgsSubScores(egsId);
		logger.trace("retrieveSubScores(), retrieved " + cssL.size() + " items of subject scores for " + egsId);
		if (cssL != null && !cssL.isEmpty()) {
			for (ChildSubScore css: cssL) {
				SubScore ss = scoreM.get(css.getEid());
				if (ss == null) {
					ss = new SubScore(css.getEid(), egsId);
					scoreM.put(css.getEid(), ss);
				}
				// accumulate subject question score for examinee
				ss.setSubScore(ss.getSubScore() + css.getRealScore());
				if (css.getBranch().compareTo("不分科") != 0) {
					List<SubChildScore> subCS = ss.getSubCSL();
					boolean found = false;
					for (SubChildScore subc : subCS) {
						if (subc.getSubName().compareTo(css.getBranch()) == 0) {
							subc.setScore(subc.getScore() + css.getRealScore());
							found = true;
							break;
						}
					}
					if (found == false) {
						SubChildScore subc = new SubChildScore(css.getBranch(), css.getRealScore());
						subCS.add(subc);
					}
				}
			}
		}
		
		logger.debug("retrieveSubScores(), detailed subject scores for " + egsId + "->" + scoreM.values());
		return scoreM;
	}
	
/*	private Map<Long, Float> getSubjScores(Long egsId) {

		Map<Long, Float> result = new HashMap<>();

		Map<Long, Map<Long, List<Map<String, Object>>>> questScores = new HashMap<>();

		List<Map<String, Object>> scores = egsDaoImpl.getExamSubjectSubjScores(egsId);
		for (Map<String, Object> map : scores) {
			if (null != map.get("id") && null != map.get("quesid")) {
				long studentId = (int) map.get("id");
				long quesid = (int) map.get("quesid");
				Map<Long, List<Map<String, Object>>> questions = questScores.get(studentId);
				if (null == questions) {
					questions = new HashMap<>();
				}
				List<Map<String, Object>> qes_scores = questions.get(quesid);
				if (null == qes_scores) {
					qes_scores = new ArrayList<>();
				}
				qes_scores.add(map);

				questions.put(quesid, qes_scores);
				questScores.put(studentId, questions);
			}
		}

		Map<Long, String> markModes = getMarkMode(egsId);

		for (Entry<Long, Map<Long, List<Map<String, Object>>>> stu : questScores.entrySet()) {
			long studentId = stu.getKey();
			float score = 0;
			for (Entry<Long, List<Map<String, Object>>> qes : stu.getValue().entrySet()) {
				long quesid = qes.getKey();
				List<Map<String, Object>> qes_scores = qes.getValue();
				String markMode = markModes.get(quesid);
				if (markMode.equals("双评")) {
					if (qes_scores.size() >= 2 && qes_scores.size() <= 3) {
						float sc = 0;
						int count = 0;
						for (Map<String, Object> map : qes_scores) {
							boolean isfinal = (boolean) map.get("isfinal");
							if(isfinal){
								count = 1;
								sc = (float) map.get("score");
								break;
							} else {
								count = count + 1;
								sc = sc + (float) map.get("score");
							}
						}
						if(sc>0){
							score += (float) sc/count;
						}
					}
				} else {
					if (qes_scores.size() > 0) {
						score += (float) qes_scores.get(0).get("score");
					}
				}
			}

			result.put(studentId, score);
		}

		return result;

	}*/

/*	private Map<Long, String> getMarkMode(Long egsId) {

		Map<Long, String> result = new HashMap<>();

		List<Map<String, Object>> scores = egsDaoImpl.getExamSubjectMarkMode(egsId);
		for (Map<String, Object> map : scores) {
			if (null != map.get("id") && null != map.get("markMode")) {
				long questionId = (int) map.get("id");
				String markMode = map.get("markMode").toString();
				result.put(questionId, markMode);
			}
		}

		return result;
	}*/

	/* (non-Javadoc)
	 * @see com.ustudy.exam.service.ExamSubjectService#isAnswerSet(java.lang.Long)
	 * 
	 * Check whether all questions' answer are set at runtime rather than determine that via
	 * fields in table of database. As settings are allowed to be changed before examination completed.
	 */
	@Override
	public UResp isAnswerSet(Long id) {
		
		UResp res = new UResp();
		// retrieve answer settings for egs firstly
		List<QuesAnswer> quesAns = qaDao.getQuesAnswerForValidation(id);
		for (QuesAnswer qa: quesAns) {
			if (!qa.isValid()) {
				logger.warn("isAnswerSet(), answer setting for question is not completed->" + qa.toString());
				res.setMessage("answer setting for question is not completed->" + qa.toString());
				return res;
			}
		}
		res.setRet(true);
		return res;
	}

	/* (non-Javadoc)
	 * @see com.ustudy.exam.service.ExamSubjectService#isMarkTaskDispatched(java.lang.Long)
	 * Check whether all questions' mark task are already dispatched
	 */
	@Override
	public UResp isMarkTaskDispatched(Long id) {
		UResp res = new UResp();
		List<MarkTask> mtL = mtM.getMarkTasksByEgs(id);
		for (MarkTask mt: mtL) {
			if (!mt.isValid()) {
				logger.warn("isMarkTaskDispatched(), mark task assignment is not completed for " + mt.getQuestionId());
				res.setMessage("mark task assignment is not completed for " + mt.getQuestionId());
				return res;
			}
		}
		
		res.setRet(true);
		return res;
	}

	private void populateExamSubStatus(List<ExamSubject> esL) {
		logger.trace("populateExamSubStatus(), populate answer set and task dispatch status for examsubjects");
		for (ExamSubject es: esL) {
			es.setAnswerSet(this.isAnswerSet(es.getId()).isRet());
			es.setTaskDispatch(this.isMarkTaskDispatched(es.getId()).isRet());
		}
	}
}
