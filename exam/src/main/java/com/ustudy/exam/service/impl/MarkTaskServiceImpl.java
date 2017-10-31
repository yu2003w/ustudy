package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.mapper.MarkTaskMapper;
import com.ustudy.exam.model.MetaScoreTask;
import com.ustudy.exam.model.QuesMarkSum;
import com.ustudy.exam.model.MarkTask;
import com.ustudy.exam.model.MarkTaskBrife;
import com.ustudy.exam.service.MarkTaskService;
import com.ustudy.exam.utility.ExamUtil;

@Service
public class MarkTaskServiceImpl implements MarkTaskService {

	private static final Logger logger = LogManager.getLogger(MarkTaskServiceImpl.class);
	@Autowired
	private MarkTaskMapper scoreTaskM;
	
	@Override
	public List<MarkTaskBrife> getMarkTaskBrife(String teacid) {
		
		List<MetaScoreTask> mstL = scoreTaskM.getMetaScoreTask(teacid);
		List<MarkTaskBrife> stL = new ArrayList<MarkTaskBrife>();
		
		for (MetaScoreTask mst: mstL) {
			stL.add(assembleTaskBrife(mst));
		}
		
		return stL;
	}
	
	/*
	 * Steps for constructing mark task brife information
	 * 1, retrieve basic mark task information
	 * 2, retrieve question summary info
	 * 
	 */
	private MarkTaskBrife assembleTaskBrife(MetaScoreTask mst) {
		
		MarkTaskBrife mt = scoreTaskM.getMarkTaskBrife(mst.getQuesid());
		mt.setTeacherId(mst.getTeacid());
		mt.setTeacherName(ExamUtil.retrieveSessAttr("uname"));
		mt.setId(mst.getId());
		mt.setScoreType(mst.getScoreType());
		logger.debug("assembleTaskBrife()," + mt.toString());
		// assemble question summary information
		String quesN = null;
		if (mt.getQuesno() == null || mt.getQuesno().isEmpty() || mt.getQuesno().compareTo("0") == 0) {
			quesN = mt.getStartno() + "-" + mt.getEndno();
		}
		else
			quesN = mt.getQuesno();
		QuesMarkSum sum = new QuesMarkSum(quesN, mt.getQuesType(), 0, 0);
		List<QuesMarkSum> sumL = new ArrayList<QuesMarkSum>();
		sumL.add(sum);
		mt.setSummary(sumL);
		logger.debug("assembleTaskBrife(), " + mt.toString());
		return mt;
	}
	
	private MarkTask assembleScoreTask(MetaScoreTask mst) {
		/*
		 *  Several steps to construct score task
		 *  1, retrieve meta score task
		 *  2, construct basic score task information
		 *  3, retrieve question paper information 
		 */
		// retrieve basic information such as exam name, grade name, subject and so on.
		MarkTask st = scoreTaskM.getScoreTask(mst.getQuesid());
		// set teacher information
		st.setTeacherId(mst.getTeacid());
		st.setTeacherName(ExamUtil.retrieveSessAttr("uname"));
		// set score task related information
		st.setScoreType(mst.getScoreType());
		
		return st;
	}

	@Override
	public List<MarkTask> getTaskPapers(String teacid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateMarkResult(List<MarkTask> papers) {
		// TODO Auto-generated method stub
		return null;
	}

}
