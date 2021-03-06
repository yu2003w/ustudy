package com.ustudy.exam.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ustudy.UResp;
import com.ustudy.exam.model.statics.ScoreClass;
import com.ustudy.exam.service.ScoreService;

@RestController
@RequestMapping(value = "/score")
public class ScoreController {

	private static final Logger logger = LogManager.getLogger(ScoreController.class);

	@Autowired
	private ScoreService scoreS;
	
	@RequestMapping(value = "/recalculate/{egsId}/{quesno}/{answer}", method = RequestMethod.POST)
    public UResp calQuestionScore(@PathVariable Long egsId, @PathVariable Integer quesno, @PathVariable String answer, HttpServletRequest request,
            HttpServletResponse response) {
        logger.debug("calQuestionScore(), egsId: " + egsId + ",quesno=" + quesno + ",answer=" + answer);

        UResp res = new UResp();

        try {
            if (scoreS.calQuestionScore(egsId, quesno, answer)) {
                res.setRet(true);
            } else {
                logger.warn("calQuestionScore(), calculate failed");
            }
        } catch (Exception e) {
            res.setMessage(e.getMessage());
            logger.error("calQuestionScore(), failed with exception->" + e.getMessage());
            e.printStackTrace();
        }

        return res;
    }

    @RequestMapping(value = "/recalculate/{egsId}", method = RequestMethod.POST)
    public UResp calObjScoreOfEgs(@PathVariable Long egsId, HttpServletResponse response) {

        logger.debug("calObjScoreOfEgs(), egsid=" + egsId);

        UResp res = new UResp();

        new Thread() {
        	public void run() {
        		if (!scoreS.calObjScoreOfEgs(egsId)) {
        			logger.error("calObjScoreOfEgs(), calulation failed for egsid=" + egsId);
        		}
        	}
        }.start();
       
        res.setRet(true);
        res.setMessage("scheduled task to calculate scores of object questions");
        logger.debug("calObjScoreOfEgs(), scheduled task for egsid=" + egsId);
        return res;
    }

    @RequestMapping(value = "/publish/{examId}/{release}", method = RequestMethod.POST)
    public UResp publishExamScore(@PathVariable Long examId, @PathVariable Boolean release, 
    		HttpServletResponse resp) {

        logger.debug("publishExamScore(examId:"+examId+",release:"+release+").");

        UResp res = new UResp();

        try {
        	if (release) {
        		if (scoreS.publishExamScore(examId, release)) {
        			res.setRet(true);
        			res.setMessage("考试成绩发布成功");
        		} else {
        			res.setMessage("考试成绩发布失败");
        		}
        	} else {
        		if (scoreS.publishExamScore(examId, release)) {
        			res.setRet(true);
        			res.setMessage("考试成绩取消发布成功");
        		} else {
        			res.setMessage("考试成绩取消发布失败");
        		}
			}
        } catch (Exception e) {
            res.setMessage(e.getMessage());
            logger.error("publishExamScore(), failed with exception->" + e.getMessage());
            resp.setStatus(500);
            e.printStackTrace();
        }

        return res;
    }
    
    /**
     * 
     * getStudentSubjects[统计考生成绩]
     * 创建人:  dulei
     * 创建时间: 2017年12月20日 下午10:48:54
     * 
     * Updated by Jared on April 01, 2018, rename the method name and refactor code
     * 
     * @Title: getStudentSubjects
     * @param examId 考试ID
     * @param schId 学校ID
     * @param gradeId 年级ID
     * @param classId 班级ID
     * @param subjectId 科目ID
     * @param branch 分科:(文科:art,理科:sci,不分科：none)
     * @param text 考生姓名或考号
     * @return
     */
    @RequestMapping(value = "/students/subjects/{examId}", method = RequestMethod.GET)
    public UResp getStudentScores(@PathVariable Long examId,
            @RequestParam(required=false,defaultValue="0") Long schId, 
            @RequestParam(required=false,defaultValue="0") Long gradeId, 
            @RequestParam(required=false,defaultValue="0") Long classId, 
            @RequestParam(required=false,defaultValue="0") Long subjectId, 
            @RequestParam(required=false,defaultValue="") String branch, 
            @RequestParam(required=false,defaultValue="") String key, HttpServletResponse resp) {
        
        logger.info("getStudentScores(), examId:" + examId + ", schId:" + schId + ",gradeId:" + gradeId +
        		",classId:" + classId + ",subjectId:" + subjectId + ",branch:" + branch + ",key:" + key);
        
        UResp res = new UResp();
        
        try {
        	res.setData(scoreS.getStudentScores(examId, schId, gradeId, classId, subjectId, branch, key));
            res.setRet(true);
        } catch (Exception e) {
            res.setMessage(e.getMessage());
            logger.error("getStudentScores(), failed with exception->" + e.getMessage());
            resp.setStatus(500);
            e.printStackTrace();
        }
        
        return res;
    }
    
    /**
     * 
     * getDetailedScores[考生的分明细]
     * retrieve detailed scored for specified examinee
     * 创建人:  dulei
     * 创建时间: 2017年12月21日 下午9:00:15
     *
     * Updated by Jared on April 02, 2018
     * 
     * @Title: getStudentScores
     * @param stuId 考生ID
     * @param examId 考试ID
     * @return
     */
    @RequestMapping(value = "/student/scores/{stuId}/{examId}", method = RequestMethod.GET)
    public UResp getDetailedScores(@PathVariable Long stuId, @PathVariable Long examId, 
    		HttpServletResponse resp) {
    	
    	logger.trace("getDetailedScores(), exameeId:" + stuId + ",examId:" + examId);
    	
    	UResp res = new UResp();
    	
    	try {
    		res.setData(scoreS.getDetailedExameeScore(stuId, examId));
    		res.setRet(true);
        } catch (Exception e) {
        	res.setMessage(e.getMessage());
            logger.error("getStudentScores(), failed with exception->" + e.getMessage());
            resp.setStatus(500);
            e.printStackTrace();
        }
    	
    	return res;
    }

    @RequestMapping(value = "/cls/{eid}/{gid}/", method = RequestMethod.GET)
    public UResp getClsScore(@PathVariable("eid") @Valid int eid, @PathVariable("gid") @Valid int gid, 
    		HttpServletResponse resp) {
    	logger.debug("getClsScore(), retrieve class scores for exam->" + eid + ", gid->" + gid);
    	UResp res = new UResp();
    	
    	// if gid <=0 indicates retrieve scores for all grades
    	if (eid < 0) {
    		logger.error("getClsScore(), parameter invalid");
    		res.setMessage("getClsScore(), parameter invalid");
    		return res;
    	}
    	
    	try {
    		List<ScoreClass> scL= scoreS.getClsScores(eid, gid);
    		res.setData(scL);
    		res.setRet(true);
    		res.setMessage("retrieve class score information succeeded.");
    	} catch (Exception e) {
    		logger.error("getClsScore(), failed with exception->" + e.getMessage());
    		resp.setStatus(500);
    		res.setMessage("retrieve class scores failed");
    	}
    	
    	return res;
    }
    
    @RequestMapping(value = "/collect/finished/{egsid}", method = RequestMethod.GET)
    public UResp isScoreCalculated(@PathVariable("egsid") int egsid, HttpServletResponse resp) {
    	UResp res = new UResp();
    	
    	try {
    		res.setData(scoreS.isScoreCalculated(egsid));
    		res.setRet(true);
    		logger.debug("isScoreCalculated(), score collected status for esgid->" + egsid 
    				+ " " + res.getData());
    	} catch (Exception e) {
    		logger.error("isScoreCalculated(), failed with->" + e.getMessage());
    		resp.setStatus(500);
    		res.setMessage("Failed with ->" + e.getMessage());
    	}
    	return res;
    }
}
