package com.ustudy.exam.model.score;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ustudy.UResp;
import com.ustudy.exam.dao.ExameeScoreDao;

public class ExamineeScoreCalTask implements Callable<UResp> {

	private static final Logger logger = LogManager.getLogger(ExamineeScoreCalTask.class);
	
	private long examid = 0;
	private long gradeid = 0;
	private String subs = null;

	private ExameeScoreDao eescoreDao = null;
	
	public ExamineeScoreCalTask(long examid, long gradeid, String subs, ExameeScoreDao eeScoreD) {
		super();
		this.examid = examid;
		this.gradeid = gradeid;
		this.subs = subs;
		this.eescoreDao = eeScoreD;
	}

	public ExamineeScoreCalTask(long examid, String subs) {
		super();
		this.examid = examid;
		this.subs = subs;
	}


	@Override
	public UResp call() throws Exception {
		
		UResp res = new UResp();
		String msg = "examid=" + this.examid + ", gradeid=" + this.gradeid + ", subs=" + this.subs;
		res.setMessage(msg);
		
		List<ExameeScore> eeScores = eescoreDao.calExameeScore(this.examid, this.gradeid, this.subs);
		logger.trace("ExamineeScoreCalTask:call(), " + eeScores.size() + " examinee scores retrieved for " + msg);
		
		if(eeScores.size() > 0){

			Collections.sort(eeScores);
			
			int rank = 1;
			
			// if score equals, rank should be the same and others' rank should be adjusted corresponding
			for (int i = 0; i < eeScores.size(); i++) {
				if (i == 0) {
					eeScores.get(i).setRank(rank);
				}
				else {
					if (eeScores.get(i).getScore().compareTo(eeScores.get(i-1).getScore()) == 0) {
						eeScores.get(i).setRank(eeScores.get(i-1).getRank());
					}
					else {
						eeScores.get(i).setRank(rank);
					}
				}
				rank++;
			}
			
			int ret = eescoreDao.insertExameeScores(eeScores);
			res.setRet(true);
			logger.debug("ExamineeScoreCalTask:call(), number of examinee scores saved is " + ret);
			logger.trace("ExamineeScoreCalTask:call(), examinee score calculation completed for " + msg);
			
		} else {
			res.setMessage(msg + "-> 0 examinee scores retrieved");
		}
		
		return res;
	}

}
