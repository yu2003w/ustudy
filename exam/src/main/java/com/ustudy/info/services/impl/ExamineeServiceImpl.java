package com.ustudy.info.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustudy.info.mapper.ExamineeMapper;
import com.ustudy.info.model.ClassInfo;
import com.ustudy.info.model.Examinee;
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
		logger.trace("createExaminee(), " + ex.size() + " examinees submitted");
		// List<ExamineeSub> eeSubs = new ArrayList<ExamineeSub>();
		Map<String, Long> clsDict = populateClsInfo(ex);
		for (Examinee ee : ex) {
			if (ee.getSchId() == null || ee.getSchId().isEmpty()) {
				if (InfoUtil.retrieveSessAttr("orgType").compareTo("学校") == 0) {
					ee.setSchId(InfoUtil.retrieveSessAttr("orgId"));
					logger.trace("createExaminee(), populated schIds " + ee.getSchId().toString());
				}
				else {
					logger.error("createExaminee(), schId is not specified in item->" + ee.toString());
					throw new RuntimeException("createExam(), [schId] is not specified in request parameter");
				}
			}
			if (ee.getClassName() == null || ee.getClassName().length() == 0 || ee.getGradeId() <= 0 ||
					!clsDict.containsKey(ee.getGradeId() + ee.getClassName())) {
				logger.warn("createExainee(), invalid grade id or class name", ee);
				continue;
			}
			
			ee.setClassId(clsDict.get(ee.getGradeId() + ee.getClassName()));
			int ret = exM.createExaminee(ee);
			//TODO: need to populate examinee subjects here
			if (ret < 0 || ret > 2) {
				logger.error("createExaminee(), create examinee failed with " + ret + 
						", exam_code " + ee.getExamCode());
				throw new RuntimeException("insert into ustudy.examinee failed with " + ret);
			}
			
			/*if (ee.getId() <= 0) {
				logger.error("createExaminee(), invalid id after creation " + ee.getId() + 
						", exam_code " + ee.getExamCode());
				throw new RuntimeException("invalid id after creation " + ee.getId());
			}
			
			for (long sid : ee.getSubs()) {
				eeSubs.add(new ExamineeSub(ee.getId(), sid));
			}*/
			count++;
		}
		
	/*	if (eeSubs.size() > 0) {
			int ret = exM.createExamineeSubs(eeSubs);
			logger.info("createExaminee(), populate subjects for examinees, number is " + ret);
		}*/
		return count;

	}

	private Map<String, Long> populateClsInfo(List<Examinee> exL) {
		Map<String, Long> clsM = null;
		List<ClassInfo> clsL = new ArrayList<ClassInfo>();
		for (Examinee ex: exL) {
			if (ex.getClassName() == null || ex.getClassName().length() == 0 || ex.getGradeId() <= 0) {
				logger.warn("populateClassInfo(), invalid class info", ex);
				continue;
			}
			ClassInfo ci = new ClassInfo(ex.getClassName(), ex.getGradeId());
			if (!clsL.contains(ci)) {
				clsL.add(ci);
			}
		}
		for (ClassInfo ci: clsL) {
			int ret = exM.saveClsInfo(ci);
			if (ret < 0 || ret > 2) {
				logger.error("populateClsInfo(), failed to populate class info", ci);
				throw new RuntimeException("failed to populate class info");
			}
			logger.debug("populateClsInfo(), class info populated" + ci.toString());
			if (clsM == null)
				clsM = new HashMap<String, Long>();
			clsM.put(ci.getGradeId() + ci.getClassName(), ci.getId());
		}
		logger.debug("populateClsInfo(), class info dictionary" + clsM.toString());
		
		return clsM;
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
				logger.error("updateExaminee(), schId is not specified in item->" + ee.toString());
				throw new RuntimeException("updateExaminee(), [schId] is not specified in request parameter");
			}

			// check whether examId or stuExamId updated, whether unique key changed or not
			Examinee stu = exM.getExamineeById(ee.getId());
			if ((ee.getExamId() > 0 && ee.getExamId() == stu.getExamId())
					&& (ee.getExamCode() != null && ee.getExamCode().compareTo(stu.getExamCode()) == 0)) {
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

	@Override
	public List<Examinee> getExamineeByFilter(long examid, long gradeid, long clsid, String key) {
		List<Examinee> eeL = exM.getExamineeByFilter(examid, gradeid, clsid, key);
		logger.debug("getExamineeByFilter(), " + eeL.size() + " examinees retrieved.");
		return eeL;
	}

	@Override
	public List<Examinee> getAbsentListPerEgs(long egsid) {
		List<Examinee> eeL = exM.getAbsentListPerEgs(egsid);
		logger.debug("getAbsentListPerEgs(), " + eeL.size() + " examinees absent for egsid " + egsid);
		return eeL;
	}

	@Override
	public List<Examinee> getExamineeListByClient(long examid, long gradeid) {
		List<Examinee> eeL = exM.getExamineeByClient(examid, gradeid);
		logger.debug("getExamineeListByClient(), " + eeL.size() + " examinees retrieved by client for examid=" + examid + ", gradeid=" + gradeid);
		return eeL;
	}

}
