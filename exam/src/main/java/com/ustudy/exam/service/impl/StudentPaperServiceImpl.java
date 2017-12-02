package com.ustudy.exam.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ustudy.exam.dao.QuesareaDao;
import com.ustudy.exam.dao.StudentPaperDao;
import com.ustudy.exam.model.Quesarea;
import com.ustudy.exam.model.StudentPaper;
import com.ustudy.exam.service.StudentPaperService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class StudentPaperServiceImpl implements StudentPaperService {

	private static final Logger logger = LogManager.getLogger(StudentPaperServiceImpl.class);

	@Resource
	private StudentPaperDao papersDao;
	
	@Resource
	private QuesareaDao quesareaDao;

	public JSONArray getStudentPapers(Long csId) {
		logger.debug("getStudentPapers -> csId:" + csId);
		JSONArray paperArray = new JSONArray();

		Map<Integer, List<StudentPaper>> papers = getStudentPapersByEgsId(csId);
		if (null != papers && papers.size() > 0) {

			for (Integer batchNum : papers.keySet()) {
				JSONObject paperObject = new JSONObject();

				paperObject.put("CsID", csId);
				paperObject.put("BatchNum", batchNum);

				List<StudentPaper> oneBatchPaper = papers.get(batchNum);
				paperObject.put("SpCnt", oneBatchPaper.size());

				JSONArray pics = new JSONArray();
				for (StudentPaper paper : oneBatchPaper) {
					JSONObject pic = new JSONObject();
					pic.put("StuAPPath", paper.getPaperImg());
					if(null != paper.getPaperStatus() && !paper.getPaperStatus().equals("null")) pic.put("paperStatus", paper.getPaperStatus());
					if(null != paper.getErrorStatus() && !paper.getErrorStatus().equals("null")) pic.put("errorStatus", paper.getErrorStatus());
					pics.add(pic);
				}
				paperObject.put("StudentPaperPicList", pics);

				paperArray.add(paperObject);
			}
		}

		return paperArray;
	}

	private Map<Integer, List<StudentPaper>> getStudentPapersByEgsId(Long csId) {

		Map<Integer, List<StudentPaper>> resault = new HashMap<>();

		List<StudentPaper> papers = papersDao.getStudentPapers(csId);
		if (null != papers && papers.size() > 0) {
			for (StudentPaper paper : papers) {
				List<StudentPaper> oneBatchPaper = resault.get(paper.getBatchNum());
				if (null == oneBatchPaper) {
					oneBatchPaper = new ArrayList<>();
				}
				oneBatchPaper.add(paper);
				resault.put(paper.getBatchNum(), oneBatchPaper);
			}
		}

		return resault;
	}
	
	public Map<String, Object> getAnswerPapers(Long egsId, Long questionId,	Long classId, String type, String text,	Boolean viewAnswerPaper) throws Exception {
		logger.debug("egsId: " + egsId + ",questionId: " + questionId + ",classId: " + classId + ",type: " + type + ",text: " + text + ",viewAnswerPaper: " + viewAnswerPaper);
		
		Map<String, Object> resault = new HashMap<>();
		if(null == questionId && null == classId){
			throw new Exception("Parameters Exception, questionId and classId is null.");
		}else{
			
			List<StudentPaper> papers = papersDao.getAnswerPapers(egsId, questionId, classId, type, text);
			resault.put("papers", papers);
			
			if(null != questionId && questionId > 0 && !viewAnswerPaper){
				List<Quesarea> quesareas = quesareaDao.getQuesareas(questionId);
				resault.put("quesareas", quesareas);
			}
			
			return resault;			
		}
		
	}

}
