package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.MarkProgressDao;
import com.ustudy.exam.mapper.MarkProgressMapper;
import com.ustudy.exam.model.statics.EgsMarkProgress;
import com.ustudy.exam.model.statics.EgsMeta;
import com.ustudy.exam.model.statics.ExamMarkProgress;
import com.ustudy.exam.model.statics.QuesMarkMetrics;
import com.ustudy.exam.service.MarkProgService;

@Service
public class MarkProgServiceImpl implements MarkProgService {

	private static final Logger logger = LogManager.getLogger(MarkProgServiceImpl.class);
	
	@Autowired
	private MarkProgressMapper mpM;
	
	@Autowired
    private MarkProgressDao dao;
	
	@Override
	public List<ExamMarkProgress> getExamMarkProg(String sid) {
		
		List<EgsMeta> exMetaL = mpM.getExamMetaInfo(sid);
		
		List<ExamMarkProgress> expL = null;
		// need to convert EgsMeta to ExamMarkProgress
		if (exMetaL != null && !exMetaL.isEmpty()) {
			expL = getExMarkProg(exMetaL);
		}
		else {
			expL = new ArrayList<ExamMarkProgress>();
		}
		
		return expL;
	}

	@Override
	public List<QuesMarkMetrics> getEgsMarkProg(int eid, int egsid) {
		
		List<QuesMarkMetrics> qmt = mpM.getQuesMarkMetricsByEgsId(eid, egsid);
		
		logger.debug("getEgsMarkProg(), QuesMarkMetrics for egsid->" + egsid + qmt.toString());
		return qmt;
	}

	private List<ExamMarkProgress> getExMarkProg(List<EgsMeta> emL) {
		
		HashMap<Integer, ExamMarkProgress> exmM = new HashMap<Integer, ExamMarkProgress>();
		
		for (EgsMeta em: emL) {
			
			EgsMarkProgress egs = new EgsMarkProgress(em.getEgsId(), em.getGradeId(), em.getGradeName(), 
					em.getSubId(), em.getSubName());
			List<QuesMarkMetrics> qmm = mpM.getQuesMarkMetricsByEgsId(em.getExamId(), em.getEgsId());
			egs.setMetrics(qmm);
			
			if (exmM.containsKey(Integer.valueOf(em.getExamId()))) {
				ExamMarkProgress emp = exmM.get(em.getExamId());
				
				emp.getEgs().add(egs);
			}
			else {
				ExamMarkProgress emp = new ExamMarkProgress(em.getExamId(), em.getExamName(), 
						em.getSchoolId(), em.getSchoolName());
				List<EgsMarkProgress> egmL = new ArrayList<EgsMarkProgress>();
				egmL.add(egs);
				emp.setEgs(egmL);
				exmM.put(Integer.valueOf(em.getExamId()), emp);
			}
			
		}
		
		logger.debug("getExMarkProg(), " + exmM.values());
		
		return new ArrayList<ExamMarkProgress>(exmM.values());
	}

	
	/**
	 * 
	 * getTeacherMarkProgress[教师阅卷进度]
	 * 创建人:  dulei
	 * 创建时间: 2018年1月11日 下午9:40:08
	 *
	 * @Title: getTeacherMarkProgress
	 * @param sid 学校ID
	 * @param egsId 考试科目ID
	 * @return
	 */
    public Collection<Map<String, Object>> getTeacherMarkProgress(String orgId, int egsId) {
        orgId = "001";
        List<Map<String, Object>> teachers = dao.getEgsTeachers(orgId, egsId);
        Map<String, Map<String, Object>> progress = getEgsTeacherMarkProgress(orgId, egsId);
        long studentsCount = dao.getEgsStudentsCount(orgId, egsId);
        Map<String, Integer> quesTeachersCount = getQuesTeachersCount(teachers);
        Map<Integer, Integer> finalMarks = getEgsTeacherFinalMarks(orgId, egsId);
        
        Map<String, Map<String, Object>> map = new HashMap<>();
        for (Map<String, Object> teacher : teachers) {
            String teacId = teacher.get("teacId").toString();
            Map<String, Object> object = map.get(teacId);
            if(null == object){
                object = new HashMap<>();
                object.put("teacId", teacId);
                object.put("teacName", teacher.get("teacName"));
                object.put("gradeId", teacher.get("gradeId"));
                object.put("gradeName", teacher.get("gradeName"));
                
                List<Map<String, Object>> quesProgress = new ArrayList<>();
                Map<String, Object> progres = new HashMap<>();
                int quesId = (int) teacher.get("quesId");
                progres.put("quesId", quesId);
                int startNo = (int) teacher.get("startNo");
                int endNo = (int) teacher.get("endNo");
                if(startNo == endNo){
                    progres.put("quesName", String.valueOf(startNo));
                }else {
                    progres.put("quesName", startNo + "-" + endNo);
                }
                
                String markType = teacher.get("markType").toString();
                int teacherCount = quesTeachersCount.get(quesId + "-" + markType);
                if(markType.equals("终评")){
                    studentsCount = finalMarks.get(quesId);
                }
                long count = studentsCount%teacherCount==0?studentsCount/teacherCount:studentsCount/teacherCount+1;
                
                Map<String, Object> p = progress.get(teacId + "-" + quesId);
                if(null != p){
                    long processedCount = (long) p.get("count");
                    float progress_ = (float)processedCount/count*100;
                    progres.put("progress", progress_);
                }else {
                    progres.put("progress", 0);
                }
                
                quesProgress.add(progres);
                object.put("questions", quesProgress);
            }else {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> quesProgress = (List<Map<String, Object>>) object.get("questions");
                Map<String, Object> progres = new HashMap<>();
                int quesId = (int) teacher.get("quesId");
                progres.put("quesId", quesId);
                int startNo = (int) teacher.get("startNo");
                int endNo = (int) teacher.get("endNo");
                if(startNo == endNo){
                    progres.put("quesName", String.valueOf(startNo));
                }else {
                    progres.put("quesName", startNo + "-" + endNo);
                }
                
                String markType = teacher.get("markType").toString();
                int teacherCount = quesTeachersCount.get(quesId + "-" + markType);
                if(markType.equals("终评") && null != finalMarks.get(quesId)){
                    studentsCount = finalMarks.get(quesId);
                }
                long count = studentsCount%teacherCount==0?studentsCount/teacherCount:studentsCount/teacherCount+1;
                
                Map<String, Object> p = progress.get(teacId + "-" + quesId);
                if(null != p){
                    long processedCount = (long) p.get("count");
                    float progress_ = (float)processedCount/count*100;
                    progres.put("progress", progress_);
                }else {
                    progres.put("progress", 0);
                }
                
                quesProgress.add(progres);
                object.put("questions", quesProgress);
            }
            
            map.put(teacId, object);
        }
        
        return map.values();
    }
    
    private Map<String, Map<String, Object>> getEgsTeacherMarkProgress(String orgId, int egsId){
        List<Map<String, Object>> progress = dao.getEgsTeacherMarkProgress(orgId, egsId);
        Map<String, Map<String, Object>> map = new HashMap<>();
        for (Map<String, Object> progres : progress) {
            String teacId = progres.get("teacId").toString();
            int quesId = (int) progres.get("quesId");
            map.put(teacId + "-" + quesId, progres);
        }
        
        return map;
    }
    
    private Map<String, Integer> getQuesTeachersCount(List<Map<String, Object>> teachers){        
        Map<String, Integer> quesTeachersCount = new HashMap<>();
        for (Map<String, Object> map : teachers) {
            int quesId = (int) map.get("quesId");
            String markType = map.get("markType").toString();
            String key = quesId + "-" + markType;
            if(null == quesTeachersCount.get(key)){
                quesTeachersCount.put(key, 1);
            }else{
                int count = quesTeachersCount.get(key);
                quesTeachersCount.put(key, count + 1);
            }            
        }
        
        return quesTeachersCount;
    }
    
    private Map<Integer, Integer> getEgsTeacherFinalMarks(String orgId, int egsId){
        List<Map<String, Object>> finalMarks = dao.getEgsTeacherFinalMarks(orgId, egsId);
        
        Map<String, List<Float>> map = new HashMap<>();
        for (Map<String, Object> finalMark : finalMarks) {
            String paperQuesId = finalMark.get("paperQuesId").toString();
            float score = (float) finalMark.get("score");
            List<Float> scores = map.get(paperQuesId);
            if(null == scores){
                scores = new ArrayList<>();
            }
            scores.add(score);
            
            map.put(paperQuesId, scores);
        }
        
        Map<Integer, Integer> finalMarksCount = new HashMap<>();
        for (Entry<String, List<Float>> entry: map.entrySet()) {
            List<Float> scores = entry.getValue();
            if (null != scores && scores.size() == 2) {
                if (Math.abs(scores.get(0)-scores.get(1)) >= 5) {
                    String[] paperQuesId = entry.getKey().split("-");
                    int quesId = Integer.valueOf(paperQuesId[1]);
                    if(null == finalMarksCount.get(quesId)){
                        finalMarksCount.put(quesId, 1);
                    }else {
                        finalMarksCount.put(quesId, finalMarksCount.get(quesId) + 1);
                    }
                }
            }
        }
        
        return finalMarksCount;
    }
	
}
