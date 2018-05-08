package com.ustudy.info.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.info.mapper.ExamineeMapper;
import com.ustudy.info.model.Examinee;
import com.ustudy.info.model.ExamineeSub;
import com.ustudy.info.services.ExamineeService;
import com.ustudy.info.util.InfoUtil;

@Service
public class ExamineeServiceImpl implements ExamineeService {

	private final static Logger logger = LogManager.getLogger(ExamineeService.class);

	@Autowired
	private ExamineeMapper exM;

	@Override
	@Transactional
	public int createExaminee(List<Examinee> ex) {
		int count = 0;
		List<ExamineeSub> eeSubs = new ArrayList<ExamineeSub>();
		for (Examinee ee : ex) {
			if (ee.getSchId() == null || ee.getSchId().isEmpty()) {
				if (InfoUtil.retrieveSessAttr("orgType").compareTo("学校") == 0) {
					ee.setSchId(InfoUtil.retrieveSessAttr("orgId"));
					logger.trace("createExaminee(), populated schIds " + ee.getSchId().toString());
				}
				else {
					logger.error("createExam(), schId is not specified in item->" + ee.toString());
					throw new RuntimeException("createExam(), [schId] is not specified in request parameter");
				}
			}
			int ret = exM.createExaminee(ee);
			//TODO: need to populate examinee subjects here
			if (ret < 0 || ret > 2) {
				logger.error("createExaminee(), create examinee failed with " + ret + 
						", exam_code " + ee.getStuExamId());
				throw new RuntimeException("insert into ustudy.examinee failed with " + ret);
			}
			
			if (ee.getId() <= 0) {
				logger.error("createExaminee(), invalid id after creation " + ee.getId() + 
						", exam_code " + ee.getStuExamId());
				throw new RuntimeException("invalid id after creation " + ee.getId());
			}
			
			for (long sid : ee.getSubs()) {
				eeSubs.add(new ExamineeSub(ee.getId(), sid));
			}
			count++;
		}
		
		if (eeSubs.size() > 0) {
			int ret = exM.createExamineeSubs(eeSubs);
			logger.info("createExaminee(), populate subjects for examinees, number is " + ret);
		}
		return count;

	}

	@Override
	@Transactional
	public int updateExaminee(List<Examinee> exs) {
		int count = 0, ret = -1;
		for (Examinee ee : exs) {
			if (ee.getId() < 1) {
				logger.error("updateExaminee(), id is not valid\n" + ee.toString());
				throw new RuntimeException("updateExaminee(), invalid ids");
			}
			
			if (InfoUtil.retrieveSessAttr("orgType").compareTo("学校") == 0) {
				ee.setSchId(InfoUtil.retrieveSessAttr("orgId"));
				logger.debug("updateExaminee(), populated schIds " + ee.getSchId().toString());
			}
			else {
				logger.error("updateExam(), schId is not specified in item->" + ee.toString());
				throw new RuntimeException("createExam(), [schId] is not specified in request parameter");
			}

			// check whether examId or stuExamId updated, whether unique key changed or not
			Examinee stu = exM.getExamineeById(ee.getId());
			if ((ee.getExamId() != null && ee.getExamId().compareTo(stu.getExamId()) == 0)
					&& (ee.getStuExamId() != null && ee.getStuExamId().compareTo(stu.getStuExamId()) == 0)) {
				ret = exM.createExaminee(ee);
				if (ret < 0 || ret > 2) {
					logger.error("updateExaminee(), failed to create examinee with ret->" + ret);
					throw new RuntimeException("updateExaminee(), failed to create examinee with ret->" + ret);
				}
			} else {
				// if unique key changed, need to delete first, then insert again
				ret = exM.deleteExaminee(ee.getId());
				if (ret != 1) {
					logger.error("updateExaminee(), delete failed before update with ret " + ret
							+ ",\ndetailed->" + ee.toString());
					throw new RuntimeException("updateExaminee(), delete failed before update with ret" + ret);
				}
				ret = exM.createExaminee(ee);
				if (ret != 1) {
					logger.error("updateExaminee(), failed to create examinee after delete with ret " + ret);
					throw new RuntimeException("updateExaminee(), failed to create examinee after delete with "
							+ "ret " + ret);
				}
			}
			
			count++;

		}
		return count;
	}

	@Override
	public int deleteExaminee(int id) {
		int ret = exM.deleteExaminee(id);
		if (ret != 1) {
			logger.error("deleteExaminee(). failed to delete examinee with id->" + id);
			throw new RuntimeException("deleteExaminee(). failed to delete examinee");
		}
		return ret;
	}

}
