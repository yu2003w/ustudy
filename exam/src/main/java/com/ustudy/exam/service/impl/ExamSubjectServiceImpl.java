package com.ustudy.exam.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.ExamDao;
import com.ustudy.exam.dao.ExamSubjectDao;
import com.ustudy.exam.dao.SubscoreDao;
import com.ustudy.exam.model.Exam;
import com.ustudy.exam.model.ExamSubject;
import com.ustudy.exam.model.Subscore;
import com.ustudy.exam.service.ExamSubjectService;
import com.ustudy.exam.service.impl.cache.PaperCache;
import com.ustudy.exam.service.impl.cache.ScoreCache;

@Service
public class ExamSubjectServiceImpl implements ExamSubjectService {

	private static final Logger logger = LogManager.getLogger(ExamSubjectServiceImpl.class);

	@Resource
	private ExamSubjectDao daoImpl;
	
	@Resource
	private SubscoreDao scoreDaoImpl;

	@Resource
	private ExamDao examDao;
	
	@Autowired
	private PaperCache paperC;
	
	@Autowired
    private ScoreCache scoC;

	public List<ExamSubject> getExamSubjects(Long subjectId, Long gradeId, String start, String end, String examName) {
		logger.debug("getExamSubjects");

		return daoImpl.getAllExamSubject(subjectId, gradeId, start, end, examName);
	}

	public List<ExamSubject> getExamSubjects(Long examId) {
		logger.debug("getExamSubjects -> examId:" + examId);
		return daoImpl.getAllExamSubjectByExamId(examId);
	}

	public List<ExamSubject> getExamSubjects(Long examId, Long gradeId) {
		logger.debug("getExamSubjects -> examId:" + examId + ",gradeId:" + gradeId);
		return daoImpl.getAllExamSubjectByExamIdAndGradeId(examId, gradeId);
	}

	public List<ExamSubject> getExamSubjects(Long examId, Long gradeId, Long subjectId) {
		logger.debug("getExamSubjects -> examId:" + examId + ",gradeId:" + gradeId + ",subjectId:" + subjectId);
		return daoImpl.getExamSubjectByExamIdAndGradeIdAndSubjectId(examId, gradeId, subjectId);
	}

	public ExamSubject getExamSubject(Long id) {
		logger.debug("getExamSubject -> id:" + id);
		return daoImpl.getExamSubjectById(id);
	}

	public boolean saveBlankAnswerPaper(Long id, String fileName) {
		logger.debug("saveBlankAnswerPaper -> id:" + id + ",fileName:" + fileName);
		try {
			daoImpl.saveBlankAnswerPaper(id, fileName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean saveBlankQuestionsPaper(Long id, String fileName) {
		logger.debug("saveBlankQuestionsPaper -> id:" + id + ",fileName:" + fileName);
		try {
			daoImpl.saveBlankQuestionsPaper(id, fileName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isAanswerSeted(Long id) {
		try {
			daoImpl.isAanswerSeted(id);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean isTaskDispatch(Long id) {
		try {
			daoImpl.isTaskDispatch(id);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public List<ExamSubject> getLastExamSubjects() {

		Exam exam = examDao.getLastExam();
		if (null != exam && exam.getId() > 0) {
			return getExamSubjects(exam.getId());
		} else {
			return null;
		}

	}

	public boolean updateExamSubjectStatus(Long egsId, Boolean release) {
		try {
			daoImpl.updateExamSubjectStatusById(egsId, release);

			if (release) {
			    // 分数汇总
				SummaryScore(egsId);
				// 清除缓存
				paperC.clearSubCache(String.valueOf(egsId));
				// 更新考试状态
				examDao.updateExamStatusByEgsid(egsId,"2");
			}else {
			    // 更新考试状态
			    examDao.updateExamStatusByEgsid(egsId,"1");
			    scoC.setScoreColStatus(egsId.intValue(), false);
            }

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateExamSubjectStatus(Long examId, Long gradeId, Long subjectId, Boolean release) {
		try {
			daoImpl.updateExamSubjectStatus(examId, gradeId, subjectId, release);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return false;
	}

	private void SummaryScore(Long egsId) {
		Map<Long, Float> objScores = getObjScores(egsId);
		Map<Long, Float> subjScores = getSubjScores(egsId);
		
		List<Subscore> scores = new ArrayList<>();
		for (Entry<Long, Float> objs : objScores.entrySet()) {
			Subscore subscore = new Subscore();
			subscore.setEgsId(egsId);
			long studentId = objs.getKey();
			subscore.setStuid(studentId);
			float objScore = objs.getValue();
			float subjScore = 0;
			if(null != subjScores.get(studentId)){
			    subjScore = subjScores.get(studentId);
			}
			float score = objScore + subjScore;
			subscore.setSubScore(subjScore);
			subscore.setObjScore(objScore);
			subscore.setScore(score);
			
			scores.add(subscore);
		}
		
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
			scoreDaoImpl.deleteSubscores(egsId);
			scoreDaoImpl.insertSubscores(scores);
		}
	}

	private Map<Long, Float> getObjScores(Long egsId) {

		Map<Long, Float> result = new HashMap<>();

		List<Map<String, Object>> scores = daoImpl.getExamSubjectObjScores(egsId);
		for (Map<String, Object> map : scores) {
			if (null != map.get("id") && null != map.get("objScore")) {
				long studentId = (int) map.get("id");
				BigDecimal score = (BigDecimal) map.get("objScore");
				result.put(studentId, score.floatValue());
			}
		}

		return result;

	}

	private Map<Long, Float> getSubjScores(Long egsId) {

		Map<Long, Float> result = new HashMap<>();

		Map<Long, Map<Long, List<Map<String, Object>>>> questScores = new HashMap<>();

		List<Map<String, Object>> scores = daoImpl.getExamSubjectSubjScores(egsId);
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
								sc = (float) qes_scores.get(0).get("score");
								break;
							} else {
								count = count + 1;
								sc = sc + (float) qes_scores.get(0).get("score");
							}
						}
						if(sc>0){
							score = (float) sc/count;
						}
					}
				} else {
					if (qes_scores.size() > 0) {
						score = (float) qes_scores.get(0).get("score");
					}
				}
			}

			result.put(studentId, score);
		}

		return result;

	}

	private Map<Long, String> getMarkMode(Long egsId) {

		Map<Long, String> result = new HashMap<>();

		List<Map<String, Object>> scores = daoImpl.getExamSubjectMarkMode(egsId);
		for (Map<String, Object> map : scores) {
			if (null != map.get("id") && null != map.get("markMode")) {
				long questionId = (int) map.get("id");
				String markMode = map.get("markMode").toString();
				result.put(questionId, markMode);
			}
		}

		return result;
	}

}
