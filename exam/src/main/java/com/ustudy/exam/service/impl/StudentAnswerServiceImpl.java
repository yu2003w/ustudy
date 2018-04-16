package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.exam.dao.MultipleScoreSetDao;
import com.ustudy.exam.dao.QuesAnswerDao;
import com.ustudy.exam.dao.RefAnswerDao;
import com.ustudy.exam.dao.StudentObjectAnswerDao;
import com.ustudy.exam.dao.StudentPaperDao;
import com.ustudy.exam.model.MultipleScoreSet;
import com.ustudy.exam.model.QuesAnswer;
import com.ustudy.exam.model.RefAnswer;
import com.ustudy.exam.model.StudentObjectAnswer;
import com.ustudy.exam.model.StudentPaper;
import com.ustudy.exam.service.StudentAnswerService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class StudentAnswerServiceImpl implements StudentAnswerService {

    private static final Logger logger = LogManager.getLogger(StudentAnswerServiceImpl.class);

    @Resource
    private StudentPaperDao paperDaoImpl;

    @Resource
    private StudentObjectAnswerDao answerDaoImpl;

    @Resource
    private RefAnswerDao refAnswerDaoImpl;

    @Resource
    private QuesAnswerDao quesAnswerDaoImpl;

    @Resource
    private MultipleScoreSetDao multipleScoreSetDaoImpl;

    public boolean saveStudentsAnswers(Long examId, Long egsId, JSONObject data) {

        logger.debug("saveStudentsAnswers -> examId:" + examId + ", egsId:" + egsId + ", data:" + data);

        try {
            if (null != data.get("sipisModelList")) {
                JSONArray studentPapers = data.getJSONArray("sipisModelList");
                if (studentPapers.size() > 0) {
                    for (int i = 0; i < studentPapers.size(); i++) {
                        JSONObject object = studentPapers.getJSONObject(i);
                        StudentPaper paper = new StudentPaper();
                        paper.setExamId(examId);
                        paper.setEgsId(egsId);
                        if (null != object.get("ESID")) {
                            paper.setStuExamNo(object.getString("ESID"));
                        }
                        if (null != object.get("BatchNum")) {
                            paper.setBatchNum(object.getInt("BatchNum"));
                        }
                        if (null != object.get("StuAPPath")) {
                            paper.setPaperImg(object.getString("StuAPPath"));
                        }
                        if (null != object.get("PaperStatus")) {
                            paper.setPaperStatus(object.getString("PaperStatus"));
                        }
                        if (null != object.get("ErrorStatusList")) {
                            String errorStatus = object.getJSONArray("ErrorStatusList").toString();
                            errorStatus = errorStatus.replace("[", "").replace("]", "");
                            paper.setErrorStatus(errorStatus);
                        }
                        paperDaoImpl.insertStudentPaper(paper);
                        logger.debug("saveStudentsAnswers(), paper saved->" + paper.toString());
                        
                        JSONArray studentScores = object.getJSONArray("Exam_Student_Score");
                        List<StudentObjectAnswer> answers = new ArrayList<>();

                        for (int j = 0; j < studentScores.size(); j++) {
                            JSONObject object_ = studentScores.getJSONObject(j);

                            StudentObjectAnswer answer = new StudentObjectAnswer();
                            answer.setPaperid(paper.getId());
                            if (null != object_.get("questNum")) {
                                answer.setQuesno(object_.getInt("questNum"));
                            }
                            if (null != object_.get("answerHas")) {
                                answer.setAnswerHas(object_.getInt("answerHas"));
                            }
                            if (null != object_.get("stuObjectAnswer")) {
                                answer.setAnswer(object_.getString("stuObjectAnswer"));
                            }

                            // answerDaoImpl.insertStudentObjectAnswer(answer);
                            answers.add(answer);
                        }

                        if (answers.size() > 0) {
                            answerDaoImpl.insertStudentObjectAnswers(getAnswers(egsId, answers));
                        }
                    }
                }

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("saveStudentsAnswers -> examId:" + examId + ", egsId:" + egsId + ", data:" + data);
            logger.error("saveStudentsAnswers -> errorMsg:" + e.getMessage());
            return false;
        }
    }

    /**
     * 
     * getRefAnswers[这里用一句话描述这个方法的作用]
     * 创建人:  dulei
     * 创建时间: 2017年12月4日 下午9:23:37
     *
     * @Title: getRefAnswers
     * @param egsId
     * @return
     */
    private Map<Integer, RefAnswer> getRefAnswers(Long egsId) {

        Map<Integer, RefAnswer> refAnswers = new HashMap<>();

        List<RefAnswer> list = refAnswerDaoImpl.getRefAnswers(egsId);
        if (null != list && list.size() > 0) {
            for (RefAnswer refAnswer : list) {
                refAnswers.put(refAnswer.getQuesno(), refAnswer);
            }
        }

        return refAnswers;
    }

    /**
     * 
     * getQuesAnswers[这里用一句话描述这个方法的作用]
     * 创建人:  dulei
     * 创建时间: 2017年12月4日 下午9:59:38
     *
     * @Title: getQuesAnswers
     * @param egsId
     * @return
     */
    private Map<Long, QuesAnswer> getQuesAnswers(Long egsId) {

        Map<Long, QuesAnswer> quesAnswers = new HashMap<>();

        List<QuesAnswer> list = quesAnswerDaoImpl.getQuesAnswers(egsId);
        if (null != list && list.size() > 0) {
            for (QuesAnswer quesAnswer : list) {
                quesAnswers.put(quesAnswer.getId(), quesAnswer);
            }
        }

        return quesAnswers;
    }

    /**
     * 
     * getMultipleScoreSets[这里用一句话描述这个方法的作用]
     * 创建人:  dulei
     * 创建时间: 2017年12月4日 下午10:11:01
     *
     * @Title: getMultipleScoreSets
     * @param egsId
     * @return
     */
    private Map<Integer, MultipleScoreSet> getMultipleScoreSets(Long egsId) {

        Map<Integer, MultipleScoreSet> multipleScoreSets = new HashMap<>();

        List<MultipleScoreSet> list = multipleScoreSetDaoImpl.getAllMultipleScoreSets(egsId);
        if (null != list && list.size() > 0) {
            for (MultipleScoreSet multipleScoreSet : list) {
                multipleScoreSets.put(multipleScoreSet.getStudentCorrectCount(), multipleScoreSet);
            }
        }

        return multipleScoreSets;
    }

    /**
     * 
     * getAnswers[这里用一句话描述这个方法的作用]
     * 创建人:  dulei
     * 创建时间: 2017年12月4日 下午9:49:37
     *
     * @Title: getAnswers
     * @param refAnswers
     * @param answers
     * @return
     */
    private List<StudentObjectAnswer> getAnswers(Long egsId, List<StudentObjectAnswer> answers) {

        Map<Integer, RefAnswer> refAnswers = getRefAnswers(egsId);
        Map<Long, QuesAnswer> quesAnswers = getQuesAnswers(egsId);
        Map<Integer, MultipleScoreSet> multipleScoreSets = getMultipleScoreSets(egsId);
        if (null != refAnswers && refAnswers.size() > 0 && null != quesAnswers && quesAnswers.size() > 0) {
            for (StudentObjectAnswer answer : answers) {
                RefAnswer refAnswer = refAnswers.get(answer.getQuesno());
                if (null != answer.getAnswer() && null != refAnswer && null != refAnswer.getAnswer()) {
                    String stuAnswer = answer.getAnswer();
                    String correctAnswer = refAnswer.getAnswer();
                    if (stuAnswer.equals(correctAnswer)) {
                        QuesAnswer quesAnswer = quesAnswers.get(refAnswer.getQuesid());
                        answer.setScore(quesAnswer.getScore());
                    } else if (stuAnswer.length() > 0 && correctAnswer.length() >= stuAnswer.length()) {
                        int studentCorrectCount = StudentObjectAnswer.getCorrectCount(stuAnswer.toUpperCase(), correctAnswer.toUpperCase());
                        if (studentCorrectCount > 0) {
                            answer.setScore(multipleScoreSets.get(studentCorrectCount).getScore());
                        }
                    }
                }
            }
        }

        return answers;
    }

    @Transactional
    public boolean deletePapers(Long egsId, Integer batchNum) {
        answerDaoImpl.deleteStudentObjectAnswers(egsId, batchNum);
        paperDaoImpl.deleteStudentPapers(egsId, batchNum);
        return true;
    }

}
